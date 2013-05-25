/*
 * Copyright 2013 Petr Kunc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.lessappcache.parser;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.importer.ImportedFile;
import cz.muni.fi.lessappcache.importer.Importer;
import cz.muni.fi.lessappcache.parser.modules.Module;
import cz.muni.fi.lessappcache.parser.modules.ModuleControl;
import cz.muni.fi.lessappcache.parser.modules.ModuleException;
import cz.muni.fi.lessappcache.parser.modules.ModuleLoader;
import cz.muni.fi.lessappcache.parser.modules.ModuleOutput;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * The entry point of library. Manifest parser provides the main functionality to parse the lesscache
 * file, analyze its lines and execute appropriate modules on each line.
 *
 * @author Petr Kunc
 */
public class ManifestParser {

    private final static String VERSION = "#version-control";
    private final static Logger logger = Logger.getLogger(ManifestParser.class.getName());
    private List<Module> modules;
    private String mode = "CACHE:";
    private Path filePath;
    private final Map<String, String> loadedResources = new HashMap<>();
    private String absolute;

    /**
     * Constructor specifying fileName to be analyzed and parsed
     *
     * @param fileName
     */
    public ManifestParser(Path fileName) {
        filePath = fileName;
        modules = ModuleLoader.load();
    }

    /**
     * Shorthand for path constructor
     *
     * @param fileName
     */
    public ManifestParser(String fileName) {
        this(Paths.get(fileName));
    }

    private String getAbsolute() {
        return absolute;
    }

    /**
     * Setter for absolute. Absolute attribute is important when developer wants to check
     * absolute paths of processed resources. This path should be set to the path describing the root
     * of server.
     *
     * @param absolute path of the server root
     */
    public void setAbsolute(String absolute) {
        this.absolute = absolute;
    }

    /**
     * Getter of filePath
     *
     * @return filePath
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Getter of current mode
     *
     * @return mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Setter of current mode
     *
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * getter of loaded resources
     *
     * @return loaded resources
     */
    public Map<String, String> getLoadedResources() {
        return loadedResources;
    }

    /**
     * This method serves for executing the parser function. It adds
     * correct headers and version comment based on last moified file
     *
     * @return list of lines of completely parsed lesscache manifest
     * @throws IOException when accessing of any file or resource stated in the lesscache file failed and application was not able to continue parsing
     */
    public List<String> execute() throws IOException {
        logger.info("Executing LessAppcache parser for file: " + filePath);
        List<String> result = new ArrayList<>();
        result.addAll(createHeaders());
        result.addAll(processFile());

        Path p = getLastModifiedFile();
        String version = "Version: " + (p == null ? new Date() : formatter(p));
        result.set(result.indexOf(VERSION), "# " + version);
        return result;
    }

    /**
     * Processes lesscache file.
     *
     * @param context
     * @return lines of processed manifest file in given context
     * @throws IOException when accessing of any file or resource stated in the lesscache file failed and application was not able to continue parsing
     */
    public List<String> processFile() throws IOException {
        List<String> processed = new ArrayList<>();
        //returned Imported File has loaded lines and normalized path saved
        ImportedFile imported = Importer.importFile(filePath);
        Path parent = PathUtils.getParent(filePath);
        processed.add("# Imported file: " + filePath);
        //make sure that imported file starts with mode set to CACHE: 
        String oldMode = mode;
        if (!"CACHE:".equals(mode)) {
            processed.add("CACHE:");
            mode = "CACHE:";
        }
        int lineNumber = 0;
        for (String line : imported.getLines()) {
            lineNumber++;
            try {
                processed.addAll(processLine(line, parent, lineNumber));
            } catch (ModuleException ex) {
                logger.error("Error while processing " + imported.getFilePath() + " on line " + lineNumber + ": " + ex.getMessage());
            }
        }
        processed.add("# End of imported file: " + filePath);
        //make sure to get back to mode we got while importing
        if (!mode.equals(oldMode)) {
            mode = oldMode;
            processed.add(oldMode);
        }
        return processed;
    }

    /**
     * Processes line of the manifest file by executing module parsers.
     *
     * @param line to be processed
     * @param context of file (relative path against its importer)
     * @param lineNumber number of processed line
     * @return list of created lines based on the line
     * @throws ModuleException when module encountered an unrecoverable error
     */
    public List<String> processLine(String line, Path context, int lineNumber) throws ModuleException {
        List<String> output = new ArrayList<>();
        line = line.trim();
        // TODO: ensure that imports and filters can use "" and ''
        for (Module m : modules) {
            ModuleOutput mo = m.parse(line, new ParsingContext(loadedResources, mode, context));

            for (Map.Entry<String, String> entry : mo.getLoadedResources().entrySet()) {
                getLoadedResources().put(entry.getKey(), filePath.toString() + ", line: " + lineNumber + ", info: " + entry.getValue());
            }

            if (mo.getMode() != null) {
                setMode(mo.getMode());
            }

            if (mo.getControl() == ModuleControl.STOP) {
                output.addAll(mo.getOutput());
                break;
            } else if (mo.getControl() == ModuleControl.REPARSE) {
                for (String l : mo.getOutput()) {
                    output.addAll(processLine(l, context, lineNumber));
                }
                break;
            } else {
                // DEFINE: to add or not to add?
                //output.addAll(mo.getOutput());
            }
        }
        return output;
    }

    private Path getLastModifiedFile() {
        Path newest = null;
        FileTime ftn = null;
        for (Map.Entry<String, String> entry : getLoadedResources().entrySet()) {
            try {
                Path temp = getFile(entry.getKey());
                FileTime ftt = null;
                if (temp != null) {
                    ftt = Files.getLastModifiedTime(temp, LinkOption.NOFOLLOW_LINKS);
                }
                if (ftt != null && (ftn == null || ftt.toMillis() > ftn.toMillis())) {
                    newest = temp;
                    ftn = ftt;
                }
            } catch (FileNotFoundException | IOException ex) {
                logger.warn(entry.getKey() + " is not a file. If this resource is not virtual, check for typos. Additional info: " + entry.getValue());
            }
        }
        return newest;
    }

    private Path getFile(String res) throws FileNotFoundException {
        if (PathUtils.isRemote(res)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(res);
        if (PathUtils.isAbsolute(res)) {
            if (getAbsolute() == null) {
                // we cannot check absolute URL paths without given context of where
                // absolute paths starts in the local filesystem
                return null;
            }
            sb.insert(0, getAbsolute());
        }

        Path resource = Paths.get(sb.toString());
        if (!Files.exists(resource, LinkOption.NOFOLLOW_LINKS)) {
            throw new FileNotFoundException();
        } else {
            return resource;
        }
    }

    private String formatter(Path p) throws IOException {
        return "Last modified file - " + p.toString() + ", Date: "
                + new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(
                new Date(Files.getLastModifiedTime(p, LinkOption.NOFOLLOW_LINKS).toMillis()));
    }

    private List<String> createHeaders() {
        logger.info("Creating headers");
        List<String> header = new ArrayList<>();
        header.add("CACHE MANIFEST");
        header.add(VERSION);
        return header;
    }
}
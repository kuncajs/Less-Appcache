/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.importer.ImportedFile;
import cz.muni.fi.lessappcache.importer.Importer;
import cz.muni.fi.lessappcache.importer.ImporterImpl;
import cz.muni.fi.lessappcache.parser.modules.Module;
import cz.muni.fi.lessappcache.parser.modules.ModuleControl;
import cz.muni.fi.lessappcache.parser.modules.ModuleException;
import cz.muni.fi.lessappcache.parser.modules.ModuleLoader;
import cz.muni.fi.lessappcache.parser.modules.ModuleOutput;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class ManifestParser {

    private final static String VERSION = "#version-control";
    private final static Logger logger = Logger.getLogger(ManifestParser.class.getName());
    private final Importer importer = new ImporterImpl();
    private Set<Module> modules = new TreeSet<>();
    private String mode = "CACHE:";
    private Path filePath;
    private final Map<String, String> loadedResources = new HashMap<>();
    private String absolute;

    public ManifestParser(Path fileName) {
        filePath = fileName;
        modules = ModuleLoader.load();
    }

    public ManifestParser(String fileName) {
        this(Paths.get(fileName));
    }

    private String getAbsolute() {
        return absolute;
    }

    public void setAbsolute(String absolute) {
        this.absolute = absolute;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Map<String, String> getLoadedResources() {
        return loadedResources;
    }

    public List<String> execute() throws IOException, ModuleException {
        logger.info("Executing LessAppcache parser for file: " + filePath);
        List<String> result = new ArrayList<>();
        result.addAll(createHeaders());
        result.addAll(processFile());

        File f = getLastModifiedFile();
        String version = "Verison: " + (f == null ? new Date() : formatter(f));
        result.set(result.indexOf(VERSION), "# "+version);
        return result;
    }

    public List<String> processFileInContextOf(Path context) throws IOException, ModuleException {
        List<String> processed = new ArrayList<>();
        //returned Imported File has loaded lines and normalized path saved
        ImportedFile imported = importer.importFile(filePath);
        Path relative = PathUtils.relativizeFolders(context, imported.getFilePath());
        Path pathToImport = relative.resolve(imported.getFilePath().getFileName());
        processed.add("# Imported file: " + pathToImport);
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
                processed.addAll(processLine(line, relative, lineNumber));
            } catch (ModuleException ex) {
                //TODO: throw exception even higher? to allow strict or normal execution -> probably yes;
                logger.error("Error while processing " + imported.getFilePath() + " on line " + lineNumber + ": " + ex.getMessage());
                throw ex;
            }
        }
        processed.add("# End of imported file: " + pathToImport);
        //make sure to get back to mode we got while importing
        if (!mode.equals(oldMode) && !filePath.equals(context)) {
            mode = oldMode;
            processed.add(oldMode);
        }
        return processed;
    }

    public List<String> processFile() throws IOException, ModuleException {
        return processFileInContextOf(filePath);
    }

    public List<String> processLine(String line, Path context, int lineNumber) throws ModuleException {
        List<String> output = new ArrayList<>();
        line = line.trim();
        for (Module m : modules) {
            //TODO: one line continue?
            //TODO: work with output! mode etc.            
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
                //TODO: add?
                //output.addAll(mo.getOutput());
            }
        }
        return output;
    }

    private File getLastModifiedFile() {
        File newest = null;
        for (Map.Entry<String, String> entry : getLoadedResources().entrySet()) {
            try {
                File temp = getFile(entry.getKey());
                if (temp != null && (newest == null || temp.lastModified() > newest.lastModified())) {
                    newest = temp;
                }
            } catch (FileNotFoundException ex) {
                logger.warn(entry.getKey()+" is not a file. If this resource is not virtual, check for typos. Additional info: "+entry.getValue());
            }
        }
        return newest;
    }

    private File getFile(String res) throws FileNotFoundException {
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
            return resource.toFile();
        }
    }
    
    private String formatter(File f) {
        return "Last modified file - " + f.toString()+", Date: " + new SimpleDateFormat("MM/dd/yyyy HH-mm-ss").format(new Date(f.lastModified()));
    }


    private List<String> createHeaders() {
        logger.info("Creating headers");
        List<String> header = new ArrayList<>();
        header.add("CACHE MANIFEST");
        header.add(VERSION);
        return header;
    }
}
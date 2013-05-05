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
import cz.muni.fi.lessappcache.parser.modules.ModuleException;
import cz.muni.fi.lessappcache.parser.modules.ModuleOutput;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class Parser {

    private Parser() {
    }

    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class ParserHolder {

        public static final Parser INSTANCE = new Parser();
    }

    public static Parser getInstance() {
        return ParserHolder.INSTANCE;
    }
    
    private final static Logger logger = Logger.getLogger(Parser.class.getName());
    private final Importer importer = new ImporterImpl();
    private final Set<Module> modules = new TreeSet<>();
    private final Set<String> loadedResources = new HashSet<>();
    private String mode = "CACHE:";
    private Path context;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Path getContext() {
        return context;
    }

    public Set<String> getLoadedResources() {
        return loadedResources;
    }
    
    private void initialize() {
        if (modules.isEmpty()) {
            ServiceLoader<Module> loadedModules = ServiceLoader.load(Module.class);
            for (Module module : loadedModules) {
                modules.add(module);
            }
        }
    }

    public List<String> execute(String fileName) throws IOException, ModuleException {
        logger.info("Executing LessAppcache parser for file: " + fileName);
        initialize();
        context = Paths.get(fileName);
        List<String> result = new ArrayList<>();
        result.addAll(createHeaders());
        result.addAll(processFile(fileName));
        return result;
    }

    public List<String> processFile(String fileName) throws IOException, ModuleException {
        List<String> processed = new ArrayList<>();
        //returned Imported File has loaded lines and normalized path saved
        ImportedFile imported = importer.importFile(fileName);
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
                processed.addAll(processLine(line, relative));
            } catch (ModuleException ex) {
                //TODO: throw exception even higher? to allow strict or normal execution -> probably yes;
                logger.error("Error while processing " + imported.getFilePath() + " on line " + lineNumber + ": " + ex.getMessage());
                throw ex;
            }
        }
        processed.add("# End of imported file: " + pathToImport);
        //make sure to get back to mode we got while importing
        if (!mode.equals(oldMode)) {
            mode = oldMode;
            processed.add(oldMode);
        }
        return processed;
    }

    public List<String> processLine(String line, Path context) throws ModuleException {
        List<String> output = new ArrayList<>();
        line = line.trim();
        for (Module m : modules) {
            //TODO: one line continue?
            ModuleOutput mo = m.parse(line, context);
            output.addAll(mo.getOutput());
            if (mo.isStop()) {
                break;
            }
        }
        return output;
    }

    private List<String> createHeaders() {
        logger.info("Creating headers");
        List<String> header = new ArrayList<>();
        header.add("CACHE MANIFEST");
        //TODO: check the change of files?? modified date??
        header.add("# VERSION " + new Date());
        return header;
    }
}
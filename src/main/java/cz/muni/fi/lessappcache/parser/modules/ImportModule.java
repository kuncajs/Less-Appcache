/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.ManifestParser;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class ImportModule extends AbstractModule implements Module {
    
    private final static Logger logger = Logger.getLogger(ImportModule.class.getName());

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (line.startsWith("@import")) {
            output.setControl(ModuleControl.STOP);
            String url = line.replaceAll("(?i)^@import\\s+(.*)$", "$1");
            String file = (PathUtils.isAbsoluteOrRemote(url) ? "" : pc.getContext()) + url;
            ManifestParser mp = new ManifestParser(file);
            try {
                mp.getLoadedResources().putAll(pc.getLoadedResources());
                mp.setMode(pc.getMode());
                output.getOutput().addAll(mp.processFileInContextOf(pc.getContext()));
                output.setLoadedResources(mp.getLoadedResources());
                output.setMode(mp.getMode());
            } catch (IOException ex) {
                logger.error("File " + file + " not found.");
                throw new ModuleException(ex);
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 5.5;
    }
    
}

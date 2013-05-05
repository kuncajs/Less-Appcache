/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.Parser;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.log4j.Logger;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;

/**
 *
 * @author Petr
 */
public class ImportModule extends AbstractModule implements Module {
    
    private final static Logger logger = Logger.getLogger(ImportModule.class.getName());

    @Override
    public ModuleOutput parse(String line, Path context) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (line.startsWith("@import")) {
            output.setStop(STOP);
            String url = line.replaceAll("(?i)^@import \"(.*)\"", "$1");
            try {
                //url does not have to be relative
                output.getOutput().addAll(Parser.getInstance().processFile((PathUtils.isAbsoluteOrRemote(url) ? "" : context) + url));
            } catch (IOException ex) {
                logger.error("File " + (PathUtils.isAbsoluteOrRemote(url) ? "" : context) + url + " not found.");
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.FileNotFoundException;
import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.Parser;
import java.nio.file.Path;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class ExplicitModule extends AbstractModule implements Module {

    private final static Logger logger = Logger.getLogger(ExplicitModule.class.getName());

    @Override
    public ModuleOutput parse(String line, Path context) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (Parser.getInstance().getMode().equals("CACHE:")) {
            output.setStop(STOP);
            try {
                String resource = PathUtils.processResource(line, context, true);
                if (!Parser.getInstance().getLoadedResources().contains(resource)) {
                    Parser.getInstance().getLoadedResources().add(resource);
                    output.getOutput().add(resource);
                } else {
                    logger.info("Resource " + resource + " already in cache");
                }
            } catch (FileNotFoundException ex) {
                throw new ModuleException(ex);
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.3;
    }
}

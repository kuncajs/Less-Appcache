/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class ExplicitModule extends AbstractModule implements Module {

    private final static Logger logger = Logger.getLogger(ExplicitModule.class.getName());

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("CACHE:")) {
            output.setControl(ModuleControl.STOP);

            String resource = PathUtils.processResource(line, pc.getContext());
            if (!pc.getLoadedResources().keySet().contains(resource)) {
                output.getLoadedResources().put(resource, "Section: Explicit");
                output.getOutput().add(resource);
            } else {
                logger.info("Resource " + resource + " already in cache");
            }

        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.3;
    }
}

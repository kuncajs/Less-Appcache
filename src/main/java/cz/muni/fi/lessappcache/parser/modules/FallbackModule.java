/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class FallbackModule extends AbstractModule implements Module {

    private final static Logger logger = Logger.getLogger(FallbackModule.class.getName());
    private Set<String> namespaces = new HashSet<>();

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("FALLBACK:")) {
            output.setControl(ModuleControl.STOP);
            String[] fallback = line.split("\\s+");
            if (fallback.length != 2) {
                throw new ModuleException("Fallback line does not contain exactly two resources!");
            }
            if (namespaces.contains(fallback[0])) {
                throw new ModuleException("Fallback namespace was already defined in the manifest!");
            }
            namespaces.add(fallback[0]);
            String resource = PathUtils.processResource(fallback[1], pc.getContext());
            if (!pc.getLoadedResources().keySet().contains(resource)) {
                output.getLoadedResources().put(resource, "Section: Fallback");
            }
            output.getOutput().add(fallback[0] + " " + resource);
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.1;
    }
}

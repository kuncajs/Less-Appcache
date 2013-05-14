/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.ParsingContext;

/**
 *
 * @author Petr
 */
public class NetworkModule extends AbstractModule implements Module {

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("NETWORK:")) {
            output.setControl(ModuleControl.STOP);
            if (!line.equals("*")) {
                output.getOutput().add(PathUtils.processResource(line, pc.getContext()));
            } else {
                output.getOutput().add(line);
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.2;
    }
}

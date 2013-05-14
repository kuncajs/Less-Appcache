/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.ManifestParser;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;

/**
 *
 * @author Petr
 */
public class HeaderModule extends AbstractModule implements Module {

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        switch (line.toUpperCase()) {
            case "CACHE MANIFEST":
                output.setControl(ModuleControl.STOP);
                return output;
            case "CACHE:":
            case "FALLBACK:":
            case "NETWORK:":
            case "SETTINGS:":
                if (!line.toUpperCase().equals(pc.getMode())) {
                    output.getOutput().add(line.toUpperCase());
                    output.setMode(line.toUpperCase());
                }
                output.setControl(ModuleControl.STOP);
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 10.0;
    }
}

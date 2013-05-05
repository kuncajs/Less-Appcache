/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.Parser;
import java.nio.file.Path;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;

/**
 *
 * @author Petr
 */
public class HeaderModule extends AbstractModule implements Module {

    @Override
    public ModuleOutput parse(String line, Path context) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        switch (line.toUpperCase()) {
            case "CACHE MANIFEST":
                output.setStop(STOP);
                return output;
            case "CACHE:":
            case "FALLBACK:":
            case "NETWORK:":
                if (!line.toUpperCase().equals(Parser.getInstance().getMode())) {
                    output.getOutput().add(line.toUpperCase());
                    Parser.getInstance().setMode(line.toUpperCase());
                }
                output.setStop(STOP);
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 10.0;
    }
}

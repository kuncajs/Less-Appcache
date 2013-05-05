/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.Parser;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;

/**
 *
 * @author Petr
 */
public class SettingsModule extends AbstractModule implements Module {
    
    private Set<String> settings = new HashSet<>();
    
    public SettingsModule() {
        //only one setting is available now by spec
        settings.add("prefer-online");
    }

    @Override
    public ModuleOutput parse(String line, Path context) {
        ModuleOutput output = new ModuleOutput();
        if (Parser.getInstance().getMode().equals("SETTINGS:")) {
            output.setStop(STOP);
            for (String s : settings) {
                if (s.equals(line)) {
                    output.getOutput().add(s);
                    break;
                }
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.0;
    }
    
}

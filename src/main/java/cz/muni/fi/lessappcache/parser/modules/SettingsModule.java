/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.ParsingContext;
import java.util.HashSet;
import java.util.Set;

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
    public ModuleOutput parse(String line, ParsingContext pc) {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("SETTINGS:")) {
            output.setControl(ModuleControl.STOP);
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

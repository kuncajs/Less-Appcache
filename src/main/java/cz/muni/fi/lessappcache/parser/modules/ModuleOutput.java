/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Petr
 */
public class ModuleOutput {
    
    private final List<String> output = new ArrayList<>();
    private Map<String,String> loadedResources = new HashMap<>();
    private ModuleControl control = ModuleControl.CONTINUE;
    private String mode;

    public List<String> getOutput() {
        return output;
    }

    public ModuleControl getControl() {
        return control;
    }

    public void setControl(ModuleControl control) {
        this.control = control;
    }
    
    public Map<String,String> getLoadedResources() {
        return loadedResources;
    }

    public void setLoadedResources(Map<String,String> loadedResources) {
        this.loadedResources = loadedResources;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}

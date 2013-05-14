/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Petr
 */
public class ModuleLoader {
    private static final Set<Module> modules = new TreeSet<>();
    
    public static Set<Module> load() {
        if (modules.isEmpty()) {
            ServiceLoader<Module> loadedModules = ServiceLoader.load(Module.class);
            for (Module module : loadedModules) {
                modules.add(module);
            }
        }
        return modules;
    }
}

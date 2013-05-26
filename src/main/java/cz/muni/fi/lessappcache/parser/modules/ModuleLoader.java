/*
 * Copyright 2013 Petr Kunc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.lessappcache.parser.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

/**
 * ModuleLoader is responsible for managing modules in the application.
 * 
 * @author Petr Kunc
 */
public class ModuleLoader {

    private static final List<Module> modules = new ArrayList<>();

    /**
     * Loads all modules using ServiceLoader available on the classpath and sorts them
     * in correct order to enable parsing each line by the modules
     *
     * @return available modules
     */
    public static List<Module> load() {
        if (modules.isEmpty()) {
            ServiceLoader<Module> loadedModules = ServiceLoader.load(Module.class);
            for (Module module : loadedModules) {
                modules.add(module);
            }
            
            Collections.sort(modules, new Comparator<Module>(){
                @Override
                public int compare(Module a, Module b){
                     return (a.getPriority() > b.getPriority() ? -1 : (a.getPriority() == b.getPriority() ? 0 : 1));
                }
            });
        }
        return modules;
    }
}

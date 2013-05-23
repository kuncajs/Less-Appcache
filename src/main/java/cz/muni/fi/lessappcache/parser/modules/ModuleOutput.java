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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing the output of modules. Contains generated lines, newly loaded resources and mode the parser should switch to.
 * It also defines how the manifest parser should process these produced lines.
 *
 * @author Petr Kunc
 */
public class ModuleOutput {
    
    private final List<String> output = new ArrayList<>();
    private Map<String,String> loadedResources = new HashMap<>();
    private ModuleControl control = ModuleControl.CONTINUE;
    private String mode;

    /**
     * List of produced lines
     *
     * @return output
     */
    public List<String> getOutput() {
        return output;
    }

    /**
     * Defines further processing of output lines, more info at {@link ModuleOutput ModuleOutput}
     * 
     * @return
     */
    public ModuleControl getControl() {
        return control;
    }

    /**
     * Setter of module control
     *
     * @param control
     */
    public void setControl(ModuleControl control) {
        this.control = control;
    }
    
    /**
     * Getter of newly loaded resources
     *
     * @return loaded resources
     */
    public Map<String,String> getLoadedResources() {
        return loadedResources;
    }

    /**
     * Setter for newly loaded resources
     *
     * @param loadedResources
     */
    public void setLoadedResources(Map<String,String> loadedResources) {
        this.loadedResources = loadedResources;
    }

    /**
     * Getter of parser mode
     *
     * @return mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Setter for parser mode
     *
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }
}
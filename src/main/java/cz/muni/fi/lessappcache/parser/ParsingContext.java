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
package cz.muni.fi.lessappcache.parser;

import java.nio.file.Path;
import java.util.Map;

/**
 * Instances of this class are sent to filters as they need to know in which mode (explicit, network,...) and context (relative path
 * to the first loaded file) it currently is. the output is a single file but resources can be imported from other files so relative
 * path has to be changed according to directory imported file is in.
 * Also map of loaded resources is passed.
 *
 * @author Petr Kunc
 */
public class ParsingContext {

    private Map<String, String> loadedResources;
    private String mode;
    private Path context;

    /**
     * Constructor of parsing context
     *
     * @param loadedResources map of loaded resources, key is the resource, value is additional info about resource useful when debugging
     * @param mode mode the parser is in ("CACHE:", "NETWORK:", "FALLBACK:" or "SETTINGS:")
     * @param context in which parsed file is.
     */
    public ParsingContext(Map<String, String> loadedResources, String mode, Path context) {
        this.loadedResources = loadedResources;
        this.mode = mode;
        this.context = context;
    }

    /**
     * getter of mode
     *
     * @return mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * Setter for mode
     *
     * @param mode
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    /**
     * Getter of context
     *
     * @return context
     */
    public Path getContext() {
        return context;
    }

    /**
     * Setter for context
     *
     * @param context
     */
    public void setContext(Path context) {
        this.context = context;
    }

    /**
     * Getter for loadedResources
     *
     * @return already loaded resources
     */
    public Map<String, String> getLoadedResources() {
        return loadedResources;
    }

    /**
     * Setter of loaded resources
     *
     * @param loadedResources
     */
    public void setLoadedResources(Map<String, String> loadedResources) {
        this.loadedResources = loadedResources;
    }
}
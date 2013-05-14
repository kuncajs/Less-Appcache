/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Petr
 */
public class ParsingContext {

    private Map<String, String> loadedResources;
    private String mode;
    private Path context;

    public ParsingContext(Map<String, String> loadedResources, String mode, Path context) {
        this.loadedResources = loadedResources;
        this.mode = mode;
        this.context = context;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Path getContext() {
        return context;
    }

    public void setContext(Path context) {
        this.context = context;
    }

    public Map<String, String> getLoadedResources() {
        return loadedResources;
    }

    public void setLoadedResources(Map<String, String> loadedResources) {
        this.loadedResources = loadedResources;
    }
}
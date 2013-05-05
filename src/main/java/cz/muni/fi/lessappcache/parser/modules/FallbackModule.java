/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.FileNotFoundException;
import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.Parser;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Petr
 */
public class FallbackModule extends AbstractModule implements Module {
    
    private Set<String> namespaces = new HashSet<>();

    @Override
    public ModuleOutput parse(String line, Path context) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (Parser.getInstance().getMode().equals("FALLBACK:")) {
            String[] fallback = line.split("\\s+");
            if (fallback.length != 2) {
                throw new ModuleException("Fallback line does not contain exactly two resources!");
            }
            if (namespaces.contains(fallback[0])) {
                throw new ModuleException("Fallback namespace was already defined in the manifest!");
            }
            namespaces.add(fallback[0]);
            try {
                String resource = PathUtils.processResource(fallback[1], context, true);
                Parser.getInstance().getLoadedResources().add(resource);
                output.getOutput().add(fallback[0]+" "+resource);
                
            } catch (FileNotFoundException ex) {
                throw new ModuleException(ex);
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.1;
    }
}

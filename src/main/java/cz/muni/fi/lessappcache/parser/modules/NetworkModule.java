/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.FileNotFoundException;
import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.Parser;
import java.nio.file.Path;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;

/**
 *
 * @author Petr
 */
public class NetworkModule extends AbstractModule implements Module {

    @Override
    public ModuleOutput parse(String line, Path context) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (Parser.getInstance().getMode().equals("NETWORK:"))  {
            output.setStop(STOP);
            if (!line.equals("*")) {
                try {
                    output.getOutput().add(PathUtils.processResource(line, context, false));
                } catch (FileNotFoundException ex) {
                    throw new ModuleException(ex);
                }
            } else {
                output.getOutput().add(line);
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 1.2;
    }
}

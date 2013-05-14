/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.ParsingContext;
import static cz.muni.fi.lessappcache.parser.modules.ModuleOutput.*;
/**
 *
 * @author Petr
 */
public class CommentModule extends AbstractModule implements Module {

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) {
        ModuleOutput output = new ModuleOutput();
        //do not add comments in the output but prepare module if needed in future
        if (line.startsWith("#") || line.equals("")) {
            output.setControl(ModuleControl.STOP);
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 8.0;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filters.Filter;
import cz.muni.fi.lessappcache.filters.FilterException;
import cz.muni.fi.lessappcache.filters.FilterExecutionException;
import cz.muni.fi.lessappcache.filters.FilterFactory;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class FilterModule extends AbstractModule implements Module {
    
    private final static Logger logger = Logger.getLogger(FilterModule.class.getName());

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (line.startsWith("@")) {
            output.setControl(ModuleControl.REPARSE);
            try {
                for (String s : loadFilter(line, pc.getContext())) {
                    output.getOutput().add(s);
                }
            } catch (FilterException ex) {
                logger.error(ex.getMessage());
                output.getOutput().add("# Filter on next line could not be load! Check the error log!");
                output.getOutput().add("# " + line);
                throw new ModuleException(ex);
            } catch (FilterExecutionException ex) {
                logger.error(ex.getMessage());
                throw new ModuleException(ex);
            }
        }
        return output;
    }

    @Override
    public double getPriority() {
        return 5.0;
    }
    
    private List<String> loadFilter(String line, Path context) throws FilterException, FilterExecutionException {
        List<String> output = new ArrayList<>();
        String[] split = line.split(" ");
        //singleton FilterFactory to ensure that each filter has only one instance to support variables and so on in the future
        Filter filterInstance = FilterFactory.getFilterInstance(split[0]);
        output.addAll(filterInstance.execute(split, context));
        return output;
    }
    
}

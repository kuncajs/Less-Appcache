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
 * Loads given filter defined by {@literal @}filter and processes its parameters. If {@link FilterException} or {@link FilterExecutionException}
 * is thrown, rethrows {@link ModuleException}.
 *
 * @author Petr Kunc
 */
public class FilterModule extends AbstractModule implements Module {
    
    private final static Logger logger = Logger.getLogger(FilterModule.class.getName());

    /**
     * Constructs module and sets priority
     */
    public FilterModule() {
        setPriority(4.0);
    }

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
                throw new ModuleException("Filter could not be loaded! "+ line, ex);
            } catch (FilterExecutionException ex) {
                throw new ModuleException("Error during filter execution.", ex);
            }
        }
        return output;
    }

    private List<String> loadFilter(String line, Path context) throws FilterException, FilterExecutionException {
        List<String> output = new ArrayList<>();
        String[] split = line.split("\\s+");
        //singleton FilterFactory to ensure that each filter has only one instance to support variables and so on in the future
        Filter filterInstance = FilterFactory.getFilterInstance(split[0]);
        output.addAll(filterInstance.execute(split, context));
        return output;
    }
    
}

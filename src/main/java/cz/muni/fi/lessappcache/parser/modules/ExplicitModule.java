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

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import org.apache.log4j.Logger;

/**
 * Module parsing resources in explicit section. Auto detects whether resource is already stated in lesscache file.
 *
 * @author Petr Kunc
 */
public class ExplicitModule extends AbstractModule implements Module {

    private final static Logger logger = Logger.getLogger(ExplicitModule.class.getName());

    /**
     * Constructs module and sets priority
     */
    public ExplicitModule() {
        setPriority(1.3);
    }
    
    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("CACHE:")) {
            output.setControl(ModuleControl.STOP);

            String resource = PathUtils.processResource(line, pc.getContext());
            if (!pc.getLoadedResources().keySet().contains(resource)) {
                output.getLoadedResources().put(resource, "Section: Explicit");
                output.getOutput().add(resource);
            } else {
                logger.info("Resource " + resource + " already in cache");
            }

        }
        return output;
    }
}

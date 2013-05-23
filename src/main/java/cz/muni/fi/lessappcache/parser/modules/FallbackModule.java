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
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Module parsing resources in fallback section. Auto detects whether given namespace of resource is already declared and
 * throws exception in case it is. Also checks whether namespace and resource are stated correctly.
 *
 * @author Petr Kunc
 */
public class FallbackModule extends AbstractModule implements Module {

    private final static Logger logger = Logger.getLogger(FallbackModule.class.getName());
    private Set<String> namespaces = new HashSet<>();

    /**
     * Constructs module and sets priority
     */
    public FallbackModule() {
        setPriority(1.1);
    }

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("FALLBACK:")) {
            output.setControl(ModuleControl.STOP);
            String[] fallback = line.split("\\s+");
            if (fallback.length != 2) {
                throw new ModuleException("Fallback line does not contain exactly two resources!");
            }
            if (namespaces.contains(fallback[0])) {
                throw new ModuleException("Fallback namespace was already defined in the manifest!");
            }
            namespaces.add(fallback[0]);
            String resource = PathUtils.processResource(fallback[1], pc.getContext());
            if (!pc.getLoadedResources().keySet().contains(resource)) {
                output.getLoadedResources().put(resource, "Section: Fallback");
            }
            output.getOutput().add(fallback[0] + " " + resource);
        }
        return output;
    }
}

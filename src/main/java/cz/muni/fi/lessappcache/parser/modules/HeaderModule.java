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

import cz.muni.fi.lessappcache.parser.ParsingContext;

/**
 * Parses declarations of headers.
 *
 * @author Petr Kunc
 */
public class HeaderModule extends AbstractModule implements Module {

    /**
     * Constructs module and sets priority
     */
    public HeaderModule() {
        setPriority(10.0);
    }
    
    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        switch (line.toUpperCase()) {
            case "CACHE MANIFEST":
                output.setControl(ModuleControl.STOP);
                return output;
            case "CACHE:":
            case "FALLBACK:":
            case "NETWORK:":
            case "SETTINGS:":
                if (!line.toUpperCase().equals(pc.getMode())) {
                    output.getOutput().add(line.toUpperCase());
                    output.setMode(line.toUpperCase());
                }
                output.setControl(ModuleControl.STOP);
        }
        return output;
    }
}

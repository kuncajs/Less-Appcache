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
import java.util.HashSet;
import java.util.Set;

/**
 * Module for parsing resources in SETTINGS: section. It ensures that only settings defined by standard will be processed
 *
 * @author Petr Kunc
 */
public class SettingsModule extends AbstractModule implements Module {
    
    private Set<String> settings = new HashSet<>();
    
    /**
     * Constructs module, sets priority and define possible settings
     */
    public SettingsModule() {
        setPriority(1.0);
        //only one setting is available now by spec
        settings.add("prefer-online");
    }

    @Override
    public ModuleOutput parse(String line, ParsingContext pc) {
        ModuleOutput output = new ModuleOutput();
        if (pc.getMode().equals("SETTINGS:")) {
            output.setControl(ModuleControl.STOP);
            for (String s : settings) {
                if (s.equals(line)) {
                    output.getOutput().add(s);
                    break;
                }
            }
        }
        return output;
    }
}
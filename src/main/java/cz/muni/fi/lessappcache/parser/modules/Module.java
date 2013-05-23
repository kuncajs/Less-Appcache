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
 * Module interface describes modules in the application that are used to parse lines of lesscache files
 *
 * @author Petr Kunc
 */
public interface Module {
    /**
     * Parses the line (if it meets its declared pattern).
     *
     * @param line to be parsed
     * @param pc {@link ParsingContext ParsingContext} defining actual state of the parser
     * @return output defining another behavior of parser
     * @throws ModuleException if module was not able to end correctly
     */
    ModuleOutput parse(String line, ParsingContext pc) throws ModuleException;
    /**
     * Gets priority in which the modules should be executed. {@link ModulePhases ModulePhases} contains predefined phases of processing.
     *
     * @return priority
     */
    double getPriority();
    /**
     * Sets priority in which the modules should be executed. {@link ModulePhases ModulePhases} contains predefined phases of processing.
     * 
     * @param priority
     */
    void setPriority(double priority);
}
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

/**
 * Defines behavior of parser when module execution has ended
 *
 * @author Petr Kunc
 */
public enum ModuleControl {
    /**
     * CONTINUE tells the parser that module did not parse the line or the parser
     * should reprocess the same line with next module
     */
    CONTINUE,
    /**
     * STOP tells the parser that modules parsed the line and no further
     * processing should be done
     */
    STOP,
    /**
     * REPARSE tells the parser that modules parsed the line and processing of this line
     * should be stopped but lines produced in output should be reparsed
     */
    REPARSE
}

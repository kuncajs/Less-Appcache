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
package cz.muni.fi.lessappcache.filters;

import java.nio.file.Path;
import java.util.List;

/**
 * Filter interface. Filter is parsing the line. It inspects the name of the filter and given arguments separated 
 * by whitespaces.
 *
 * @author Petr Kunc
 */
public interface Filter {
    /**
     * Executes the filter.
     *
     * @param args variable number of arguments. First one is always name of the filter
     * @param context of imported file against the main processed file
     * @return list of lines to be added in the output
     * @throws FilterExecutionException if anything goes wrong in the filter and filter cannot continue
     */
    List<String> execute (String[] args, Path context) throws FilterExecutionException;
}
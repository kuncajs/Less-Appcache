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

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for filters. This class is responsible for loading needed filters when requested
 * and ensures that they are loaded as singletons.
 * 
 * @author Petr Kunc
 */
public class FilterFactory {
    private static final Map<String, Filter> filters = new HashMap<>();
    
    private static final FilterClassLoader loader = new FilterClassLoader();

    /**
     * Loads given filter by name. Filters accessed by this method are loaded as singletons.
     *
     * @param filterName name of filter
     * @return Filter loaded by name
     * @throws FilterException when given filter is not found or cannot be instantiated or accessed
     */
    public static Filter getFilterInstance(String filterName) throws FilterException {
        if (!filters.containsKey(filterName)) {
            try {
                Class filter = loader.loadClass(filterName);
                filters.put(filterName, (Filter) filter.newInstance());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                throw new FilterException("There was an error when calling filter " + filterName, ex);
            }
        }
        return filters.get(filterName);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filters;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Petr
 */
public class FilterFactory {
    private static final Map<String, Filter> filters = new HashMap<>();

    public static Filter getFilterInstance(String filterName) throws FilterException {
        if (!filters.containsKey(filterName)) {
            try {
                Class filter = new FilterClassLoader().loadClass(filterName);
                filters.put(filterName, (Filter) filter.newInstance());
            } catch (ClassNotFoundException ex) {
                throw new FilterException("Filter " + filterName + " not found!", ex);
            } catch (InstantiationException ex) {
                throw new FilterException("Could not instantiate " + filterName, ex);
            } catch (IllegalAccessException ex) {
                throw new FilterException("Illegal access to filter " + filterName, ex);
            }
        }
        return filters.get(filterName);
    }
}

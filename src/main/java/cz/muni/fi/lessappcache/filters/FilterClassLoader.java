/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filters;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class FilterClassLoader extends ClassLoader {
    
    private static final Logger logger = Logger.getLogger(FilterClassLoader.class.getName());
    
    public FilterClassLoader() {  
        super(FilterClassLoader.class.getClassLoader()); //calls the parent class loader's constructor  
    }  
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(resolveName(name));
    }
    
    private String resolveName(String name) {
        char[] delimiter = {'-'};
        return "cz.muni.fi.lessappcache.filters."+StringUtils.remove(WordUtils.capitalizeFully(name.substring(1), delimiter),"-")+"Filter";
    }
}
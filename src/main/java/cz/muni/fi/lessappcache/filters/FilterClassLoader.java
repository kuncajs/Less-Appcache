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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;

/**
 * Class loader for filters. It seeks given filter name in package cz.muni.fi.lesscache.filters
 * Given filename is in format prefix-name-etc and is translated to PrefixNameEtc class name
 *
 * @author Petr Kunc
 */
public class FilterClassLoader extends ClassLoader {
    
    private static final Logger logger = Logger.getLogger(FilterClassLoader.class.getName());
    
    /**
     *  Constructor of class loader
     */
    public FilterClassLoader() {  
        super(FilterClassLoader.class.getClassLoader()); //calls the parent class loader's constructor  
    }  
    
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(resolveName(name));
    }
    
    private String resolveName(String name) {
        char[] delimiter = {'-'};
        return "cz.muni.fi.lessappcache.filters."+StringUtils.remove(WordUtils.capitalizeFully(name.substring(1), delimiter ),"-")+"Filter";
    }
}
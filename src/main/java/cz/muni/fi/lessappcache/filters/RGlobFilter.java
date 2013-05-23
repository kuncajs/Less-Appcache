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
import org.apache.log4j.Logger;

/**
 * Filter for listing contents of directory tree matching given glob filter
 * called as {@literal @}r-glob path pattern
 *
 * @author Petr Kunc
 */
public class RGlobFilter extends AbstractWalkTree implements Filter {

    private final static Logger logger = Logger.getLogger(RGlobFilter.class.getName());

    /**
     * Executor of filter, walks given directory and its subdirectories and finds all files matching given glob pattern
     *
     * @param args must contain three arguments, (0 - name of filter), 1 - path to directory to be walked, 2 - glob pattern
     * @param context of imported file against the main processed file
     * @return list of files in the directory and subdirectories, (subdirecotires themselves and symbolic links are ommited)
     * @throws FilterExecutionException if there was a problem accessing the directory
     */
    @Override
    public List<String> execute(String[] args, Path context) throws FilterExecutionException {
        if (args.length != 3) {
            throw new FilterExecutionException("Filter expects two arguments, 1st: path to directory, 2nd: glob expression");
        }
        args[2] = "glob:" + args[2];
        return super.execute(args, context);
    }
}

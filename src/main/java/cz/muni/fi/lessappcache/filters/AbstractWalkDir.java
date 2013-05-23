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

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Abstract class for listing contents of one directory
 *
 * @author Petr Kunc
 */
public abstract class AbstractWalkDir implements Filter {

    private final static Logger logger = Logger.getLogger(AbstractWalkDir.class.getName());

    /**
     * Executor of filter, walks given directory and finds all files matching pattern
     *
     * @param args must contain three arguments, 0 - name of filter, 1 - path to directory to be walked, 2 - (regex | glob):pattern
     * @param context of imported file against the main processed file
     * @return list of files in the directory (subdirectories and symbolic links are ommited)
     * @throws FilterExecutionException if there was a problem accessing the directory
     */
    @Override
    public List<String> execute(final String[] args, Path context) throws FilterExecutionException {
        List<String> result = new ArrayList<>();
        String pathName = args[1];
        pathName = PathUtils.processResource(pathName, context);

        final Path path = Paths.get(pathName);
        Path pathRelative = Paths.get(args[1]);

        //filter out directories!!
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) {
                FileSystem fs = path.getFileSystem();
                final PathMatcher matcher = fs.getPathMatcher(args[2]);
                return matcher.matches(entry.getFileName());
            }
        })) {
            for (Path entry : stream) {
                if (!Files.isDirectory(entry, LinkOption.NOFOLLOW_LINKS)) {
                    result.add(pathRelative.resolve(entry.getFileName()).toString());
                }
            }
        } catch (IOException ex) {
            throw new FilterExecutionException(ex);
        }
        return result;
    }
}

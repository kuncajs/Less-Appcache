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
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import static java.nio.file.FileVisitResult.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 * Abstract class for listing contents of directory tree
 *
 * @author Petr Kunc
 */
public abstract class AbstractWalkTree implements Filter {

    private final static Logger logger = Logger.getLogger(AbstractWalkTree.class.getName());

    /**
     * Executor of filter, walks given directory and its subdirectories and finds all files matching pattern
     *
     * @param args must contain three arguments, (0 - name of filter), 1 - path to directory to be walked, 2 - type of matcher (regex or glob)
     * @param context of imported file against the main processed file
     * @return list of files in the directory and subdirectories, (subdirecotires themselves and symbolic links are ommited)
     * @throws FilterExecutionException if there was a problem accessing the directory
     */
    @Override
    public List<String> execute(String[] args, Path context) throws FilterExecutionException {
        List<String> result = new ArrayList<>();
        // pattern in args[2]
        Finder f = new Finder(args[2]);
        // directory path in args[1]
        String pathName = args[1];
        pathName = PathUtils.processResource(pathName, context);

        Path path = Paths.get(pathName);
        Path pathRelative = Paths.get(args[1]);

        try {
            Files.walkFileTree(path, f);
        } catch (IOException ex) {
            throw new FilterExecutionException("Given path was not found: "+path, ex);
        }

        for (Path p : f.result()) {
            Path resultRelative = path.relativize(p);
            result.add(pathRelative.resolve(resultRelative).toString());
        }
        return result;
    }

    static class Finder extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private final List<Path> results = new ArrayList<>();

        Finder(String pattern) {
            matcher = FileSystems.getDefault().getPathMatcher(pattern);
        }

        public List<Path> result() {
            return results;
        }

        // Compares the pattern against
        // the file name.
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                results.add(file);
            }
        }

        // Invoke the pattern matching method on each file.
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        // Ignores directory names
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            System.err.println(exc);
            return CONTINUE;
        }
    }
}

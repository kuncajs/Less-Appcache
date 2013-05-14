/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Petr
 */
public abstract class AbstractWalkTree implements Filter {

    private final static Logger logger = Logger.getLogger(AbstractWalkTree.class.getName());

    @Override
    public List<String> execute(String[] args, Path context) throws FilterExecutionException {
        List<String> result = new ArrayList<>();
        Finder f = new Finder(args[2]);
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

        // Compares the glob pattern against
        // the file name.
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                results.add(file);
            }
        }

        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }

        // Invoke the pattern matching
        // method on each directory.
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

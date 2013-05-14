/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filesystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class PathUtils {

    private final static Logger logger = Logger.getLogger(PathUtils.class.getName());

    public static Path relativizeFolders(Path basePath, Path otherPath) {
        Path difference = basePath.relativize(otherPath).normalize();
        difference = difference.getParent();
        if (difference == null || difference.getNameCount() <= 1) {
            difference = Paths.get("");
        } else {
            difference = difference.subpath(1, difference.getNameCount());
        }
        return difference;
    }

    public static String processResource(String resource, Path context) {
        if (isAbsoluteOrRemote(resource)) {
            return resource;
        }

        Path path = Paths.get(resource);
        return context.resolve(path).normalize().toString();
    }

    public static boolean isAbsoluteOrRemote(String resource) {
        return (isAbsolute(resource) || isRemote(resource));
    }

    public static boolean isAbsolute(String resource) {
        return resource.startsWith("/");
    }

    public static boolean isRemote(String resource) {
        return resource.contains("://");
    }
}
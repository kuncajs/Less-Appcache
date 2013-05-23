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
package cz.muni.fi.lessappcache.filesystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.log4j.Logger;

/**
 * Utils for processing the paths or string representations of paths as needed
 * in the modules.
 *
 * @author Petr Kunc
 */
public class PathUtils {

    private final static Logger logger = Logger.getLogger(PathUtils.class.getName());

    /**
     * Constructs a relative path between two folders. The relativize method
     * in the path class does not work properly as if given file "a.txt" and "b.txt"
     * in the same folder it relativizes as "../"
     * In future this method could be replaced by absolutization and the relativizing.
     *
     * @param basePath the path to be relativized against
     * @param otherPath the path to be relativized
     * @return Path describing the relative path from one folder to other one.
     */
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

    /**
     * Relativize the process against given context
     *
     * @param resource file name to be processed
     * @param context relative context
     * @return relativized resource
     */
    public static String processResource(String resource, Path context) {
        if (isAbsoluteOrRemote(resource)) {
            return resource;
        }

        Path path = Paths.get(resource);
        return context.resolve(path).normalize().toString();
    }

    /**
     * Helper method to discover whether given resource
     * is absolute or remote. See {@link #isAbsolute(String resource) isAbsolute}
     * and {@link #isRemote(String resource) isRemote} methods
     * 
     * @param resource to be analyzed
     * @return true if the resource is remote or absolute, false otherwise
     */
    public static boolean isAbsoluteOrRemote(String resource) {
        return (isAbsolute(resource) || isRemote(resource));
    }

    /**
     * Checks whether the resource is absolute (whether starts with "/")
     *
     * @param resource to be analyzed
     * @return true if is absolute, false otherwise
     */
    public static boolean isAbsolute(String resource) {
        return resource.startsWith("/");
    }

    /**
     * Checks whether the resource is remote (whether contains with "://")
     *
     * @param resource to be analyzed
     * @return true if is remote, false otherwise
     */
    public static boolean isRemote(String resource) {
        return resource.contains("://");
    }
}
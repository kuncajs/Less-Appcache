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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Util file for reading and writing files
 *
 * @author Petr Kunt
 */
public class FileUtils {
    
    private final static Logger logger = Logger.getLogger(FileUtils.class.getName());

    /**
     * Encoding of loaded files
     */
    public final static Charset ENCODING = StandardCharsets.UTF_8;

    /**
     * Reads file on given path.
     *
     * @param filePath path to the file to be read
     * @return read list of lines
     * @throws IOException if there was a problem during reading the file
     */
    public static List<String> readFile(Path filePath) throws IOException {
        logger.info("Reading file: "+filePath);
        return Files.readAllLines(filePath, ENCODING);
    }

    /**
     * Writes lines to file  on given path
     *
     * @param lines list of lines to be written in the file
     * @param filePath path to the file to be written
     * @throws IOException if there was a problem during accesing the file
     */
    public static void writeFile(List<String> lines, Path filePath) throws IOException {
        logger.info("Writing file: "+filePath);
        Files.write(filePath, lines, ENCODING);
    }
}
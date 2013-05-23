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

package cz.muni.fi.lessappcache.importer;

import cz.muni.fi.lessappcache.filesystem.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 * Class for loading files to import in the manifest parser.
 *
 * @author Petr Kunc
 */
public class Importer {

    private final static Logger logger = Logger.getLogger(Importer.class.getName());
    private final static Set<Path> importedFiles = new HashSet<>();

    /**
     * Method serves to detect whether given file is already imported
     *
     * @param filePath path of file to be checked whether is already imported in the parser
     * @return true if file is already imported, false otherwise
     */
    public static boolean isImported(Path filePath) {
        return importedFiles.contains(filePath.normalize());
    }
    
    /**
     * Method serves to detect whether given file is already imported
     * shorthand for isImported(Paths.get(fileName))
     *
     * @param fileName path of file to be checked whether is already imported in the parser
     * @return true if file is already imported, false otherwise
     */
    public static boolean isImported(String fileName) {
        return isImported(Paths.get(fileName));
    }

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static ImportedFile importFile(String fileName) throws IOException {
        Path path = Paths.get(fileName).normalize();
        return importFile(path);        
    }
    
    /**
     * Imports given file in the application. It does NOT automatically check whether file is already imported.
     *
     * @param path file to be imported
     * @return ImportedFile with correctly loaded lines and path
     * @throws IOException when problem occured during importing file
     */
    public static ImportedFile importFile(Path path) throws IOException {
        logger.info("Importing file: "+ path);
        ImportedFile imported = new ImportedFile();
        imported.setFilePath(path);
        imported.setLines(FileUtils.readFile(path));
        logger.info("Importing complete");
        importedFiles.add(imported.getFilePath());
        return imported;
    }
}
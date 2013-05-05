/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Petr
 */
public class FileUtils {
    
    private final static Logger logger = Logger.getLogger(FileUtils.class.getName());

    public final static Charset ENCODING = StandardCharsets.UTF_8;

    public static List<String> readFile(Path filePath) throws IOException {
        logger.info("Reading file: "+filePath);
        return Files.readAllLines(filePath, ENCODING);
    }

    public static void writeFile(List<String> lines, Path filePath) throws IOException {
        logger.info("Writing file: "+filePath);
        Files.write(filePath, lines, ENCODING);
    }
}
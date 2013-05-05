/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filters;

import cz.muni.fi.lessappcache.filesystem.FileNotFoundException;
import cz.muni.fi.lessappcache.filesystem.PathUtils;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class GlobFilter implements Filter {
    private final static Logger logger = Logger.getLogger(GlobFilter.class.getName());

    @Override
    public List<String> execute(String[] args, Path context) throws FilterExecutionException {

        List<String> result = new ArrayList<>();

        String pathName = args[1];
        if (!PathUtils.isAbsoluteOrRemote(args[1])) {
            try {
                pathName = PathUtils.processResource(pathName, context, false);
            } catch (FileNotFoundException ex) {
                throw new FilterExecutionException("This should never happen", ex);
            }
        }
        
        Path path = Paths.get(pathName);
        Path pathRelative = Paths.get(args[1]);
        
        //filter out directories!!
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, args[2])) {
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

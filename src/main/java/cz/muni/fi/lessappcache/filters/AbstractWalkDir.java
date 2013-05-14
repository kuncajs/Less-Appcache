/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Petr
 */
public class AbstractWalkDir implements Filter {

    private final static Logger logger = Logger.getLogger(AbstractWalkDir.class.getName());

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

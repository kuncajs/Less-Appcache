package cz.muni.fi.lessappcache.importer;

import cz.muni.fi.lessappcache.filesystem.FileUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class ImporterImpl implements Importer {

    private final static Logger logger = Logger.getLogger(ImporterImpl.class.getName());
    private final List<Path> importedFiles = new ArrayList<>();

    private boolean alreadyImported(Path filePath) {
        return importedFiles.contains(filePath);
    }

    @Override
    public ImportedFile importFile(String fileName) throws IOException {
        Path path = Paths.get(fileName).normalize();
        return importFile(path);        
    }
    
    @Override
    public ImportedFile importFile(Path path) throws IOException {
        logger.info("Importing file: "+ path);
        if (alreadyImported(path)) {
            logger.info("File already imported. Skipping...");
            return null;
        }
        importedFiles.add(path);
        ImportedFile imported = new ImportedFile();
        imported.setFilePath(path);
        imported.setLines(FileUtils.readFile(path));
        logger.info("Importing complete");
        return imported;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.importer;

import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author Petr
 */
public interface Importer {
    ImportedFile importFile(String fileName) throws IOException;
    ImportedFile importFile(Path path) throws IOException;
}

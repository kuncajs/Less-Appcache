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

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * This class represents file loaded in the manifest parser containing its lines and path.
 *
 * @author Petr Kunc
 */
public class ImportedFile {
    private List<String> lines;
    private Path filePath;

    /**
     * Getter for lines
     *
     * @return list of imported lines
     */
    public List<String> getLines() {
        return lines;
    }

    /**
     * Setter for lines
     *
     * @param lines contains lines to be set
     */
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    /**
     * Getter for filePath
     *
     * @return path of imported file
     */
    public Path getFilePath() {
        return filePath;
    }

    /**
     * Setter for filePath
     *
     * @param filePath contains path of imported file
     */
    public void setFilePath(Path filePath) {
        this.filePath = filePath.normalize();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 69 * hash + Objects.hashCode(this.filePath);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImportedFile other = (ImportedFile) obj;
        if (!Objects.equals(this.filePath, other.filePath)) {
            return false;
        }
        return true;
    }
}

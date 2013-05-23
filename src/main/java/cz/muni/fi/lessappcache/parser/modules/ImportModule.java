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
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.filesystem.PathUtils;
import cz.muni.fi.lessappcache.importer.Importer;
import cz.muni.fi.lessappcache.parser.ManifestParser;
import cz.muni.fi.lessappcache.parser.ParsingContext;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * Handles import of other lesscache manifest files. If file is not found, throws {@link ModuleException}
 *
 * @author Petr Kunc
 */
public class ImportModule extends AbstractModule implements Module {
    
    private final static Logger logger = Logger.getLogger(ImportModule.class.getName());

    /**
     * Constructs module and sets priority.
     */
    public ImportModule() {
        setPriority(6.0);
    }


    @Override
    public ModuleOutput parse(String line, ParsingContext pc) throws ModuleException {
        ModuleOutput output = new ModuleOutput();
        if (line.startsWith("@import")) {
            output.setControl(ModuleControl.STOP);
            String url = line.replaceAll("(?i)^@import\\s+(.*)$", "$1");
            String file = (PathUtils.isAbsoluteOrRemote(url) ? "" : pc.getContext()) + url;
            if (Importer.isImported(file)) {
                logger.warn("File "+file+" already imported. Skipping...");
                return output;
            }
            ManifestParser mp = new ManifestParser(file);
            try {
                mp.getLoadedResources().putAll(pc.getLoadedResources());
                mp.setMode(pc.getMode());
                output.getOutput().addAll(mp.processFileInContextOf(pc.getContext()));
                output.setLoadedResources(mp.getLoadedResources());
                output.setMode(mp.getMode());
            } catch (IOException ex) {
                logger.error("File " + file + " not found.");
                throw new ModuleException(ex);
            }
        }
        return output;
    }
}

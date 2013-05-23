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

package cz.muni.fi.lessappcache;

import cz.muni.fi.lessappcache.parser.ManifestParser;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;


/**
 * Main static class defined as executable in maven when JAR package is created
 * 
 * @author Petr Kunc
 */
public class Main {

    private final static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Main method must have at least one argument:
     * lesscache file - it is parsed against actual directory!
     * Second is mandatory - absolute path
     *
     * @param args array of parameters
     */
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 2) {
            printError();
            return;
        }

        ManifestParser mp = new ManifestParser(args[0]);
        if (args.length == 2) {
            mp.setAbsolute(args[1]);
        }

        try {
            List<String> executed = mp.execute();
            for (String s : executed) {
                System.out.println(s);
            }
        } catch (IOException ex) {
        }
    }

    private static void printError() {
        System.err.println("Invalid number of parameters.");
        System.err.println("This executable can be run with these 2 arguments:");
        System.err.println("Argument 1 (mandatory) - name of lesscache file to be parsed. Plese note that all resources"
                + "are parsed in context of current directory.");
        System.err.println("Argument 2 (compulsory) - path to the directory which is supposed to be the root on the server to"
                + "allow checking existence of absolute paths in the lesscache file. Do not end the path with /");
        System.err.println();
        System.err.println("Examples:");
        System.err.println("java -jar lesscache.jar offline.lesscache /var/www/myweb");
        System.err.println("java -jar lesscache.jar offline.lesscache");
    }
}
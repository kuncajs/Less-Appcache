package cz.muni.fi.lessappcache;

import cz.muni.fi.lessappcache.parser.ManifestParser;
import cz.muni.fi.lessappcache.parser.modules.ModuleException;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) throws IOException, ModuleException {
        if (args.length == 0 || args.length > 2) {
            printError();
            return;
        }
        
        ManifestParser mp = new ManifestParser(args[0]);
        if (args.length == 2) {
            mp.setAbsolute(args[1]);
        }
        
        List<String> executed = mp.execute();
        for (String s : executed) {
            System.out.println(s);
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
package cz.muni.fi.lessappcache;

import cz.muni.fi.lessappcache.parser.Parser;
import cz.muni.fi.lessappcache.parser.modules.ModuleException;
import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main {

    public static void main(String[] args) throws IOException, ModuleException {
        //System.out.println(Files.exists(Paths.get("b.txt")));
        
        List<String> executed = Parser.getInstance().execute("main.lesscache");
        for (String s : executed) {
            System.out.println(s);
        }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filters;

import java.nio.file.Path;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Petr
 */
public class RRegex extends AbstractWalkTree implements Filter {

    private final static Logger logger = Logger.getLogger(RRegex.class.getName());

    @Override
    public List<String> execute(String[] args, Path context) throws FilterExecutionException {
        if (args.length != 3) {
            throw new FilterExecutionException("Filter expects two arguments, 1st: path to directory, 2nd: regex expression");
        }
        args[2] = "regex:"+args[2];
        return super.execute(args, context);
    }
}

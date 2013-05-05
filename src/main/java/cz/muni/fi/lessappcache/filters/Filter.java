/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filters;

import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Petr
 */
public interface Filter {
    List<String> execute (String[] args, Path context) throws FilterExecutionException;
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import java.nio.file.Path;

/**
 *
 * @author Petr
 */
public interface Module extends Comparable<Module> {
    ModuleOutput parse(String line, Path context) throws ModuleException;
    double getPriority();
}
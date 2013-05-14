/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.ParsingContext;

/**
 *
 * @author Petr
 */
public interface Module extends Comparable<Module> {
    ModuleOutput parse(String line, ParsingContext pc) throws ModuleException;
    double getPriority();
}
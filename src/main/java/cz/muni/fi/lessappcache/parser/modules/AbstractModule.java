/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

/**
 *
 * @author Petr
 */
public abstract class AbstractModule implements Module {
    
    @Override
    public int compareTo(Module o) {
        return (getPriority() > o.getPriority() ? -1 : (getPriority() == o.getPriority() ? 0 : 1));
    }
    
}

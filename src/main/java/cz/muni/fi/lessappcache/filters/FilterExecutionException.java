/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.filters;

/**
 *
 * @author Petr
 */
public class FilterExecutionException extends Exception {

    public FilterExecutionException() {
        super();
    }

    public FilterExecutionException(String message) {
        super(message);
    }

    public FilterExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilterExecutionException(Throwable cause) {
        super(cause);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.lessappcache.parser.modules;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Petr
 */
public class ModuleOutput {
    public static final boolean STOP = true;
    public static final boolean CONTINUE = false;
    
    private final List<String> output = new ArrayList<>();
    private boolean stop = CONTINUE;

    public List<String> getOutput() {
        return output;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    
}

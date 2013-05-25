/*
 * Copyright 2013 Petr.
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
package cz.muni.fi.lessappcache.parser.modules;

import cz.muni.fi.lessappcache.parser.ParsingContext;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class NetworkModuleTest extends TestCase {
    
    public NetworkModuleTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of parse method, of class NetworkModule.
     */
    public void testParse() throws Exception {
        System.out.println("parse network");
        
        NetworkModule instance = new NetworkModule();
        
        ModuleOutput result = instance.parse("whatever line", new ParsingContext(null, "CACHE:", null));
        ModuleOutput expResult = new ModuleOutput();
        expResult.setControl(ModuleControl.CONTINUE);
        assertEquals(expResult, result);
        
        result = instance.parse("whatever line", new ParsingContext(null, "FALLBACK:", null));
        assertEquals(expResult, result);
        
        result = instance.parse("*", new ParsingContext((Map) new HashMap<>(), "NETWORK:", null));
        expResult.setControl(ModuleControl.STOP);
        expResult.getOutput().add("*");
        assertEquals(expResult, result);
        
        result = instance.parse("file", new ParsingContext((Map) new HashMap<>(), "NETWORK:", Paths.get("")));
        expResult.getOutput().clear();
        expResult.getOutput().add("file");
        assertEquals(expResult, result);
        
        result = instance.parse("file", new ParsingContext((Map) new HashMap<>(), "NETWORK:", Paths.get("context")));
        expResult.getOutput().clear();
        expResult.getOutput().add(Paths.get("context/file").toString());
        assertEquals(expResult, result);
    }
}

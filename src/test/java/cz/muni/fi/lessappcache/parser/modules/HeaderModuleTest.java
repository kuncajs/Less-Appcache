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
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class HeaderModuleTest extends TestCase {
    
    public HeaderModuleTest(String testName) {
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
     * Test of parse method, of class HeaderModule.
     */
    public void testParse() throws Exception {
        System.out.println("parse");
        HeaderModule instance = new HeaderModule();
        
        ModuleOutput result = instance.parse("CACHE:", new ParsingContext(null, "CACHE:", null));
        ModuleOutput exp = new ModuleOutput();
        exp.setControl(ModuleControl.STOP);
        assertEquals(exp, result);
        
        assertEquals(exp, instance.parse("CACHE MANIFEST", new ParsingContext(null, null, null)));

        result = instance.parse("NETWORK:", new ParsingContext(null, "FALLBACK:", null));
        exp.setControl(ModuleControl.STOP);
        exp.getOutput().add("NETWORK:");
        exp.setMode("NETWORK:");
        assertEquals(exp, result);
        
        result = instance.parse("some line", new ParsingContext(null, "FALLBACK:", null));
        exp.setControl(ModuleControl.CONTINUE);
        exp.getOutput().clear();
        exp.setMode(null);
        assertEquals(exp, result);
    }
}

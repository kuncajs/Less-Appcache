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
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class FilterModuleTest extends TestCase {
    
    public FilterModuleTest(String testName) {
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
     * Test of parse method, of class FilterModule.
     */
    public void testParse() throws Exception {
        System.out.println("parse filter");
        FilterModule instance = new FilterModule();
        ModuleOutput expResult = new ModuleOutput();
        expResult.setControl(ModuleControl.CONTINUE);
        ModuleOutput result = instance.parse("data", new ParsingContext(null, null, null));
        assertEquals(expResult, result);
        
        try {
            instance.parse("@whatever", new ParsingContext(null, null, null));
            fail("exc should be thrown");
        } catch (ModuleException ex) {            
        }
    }
}

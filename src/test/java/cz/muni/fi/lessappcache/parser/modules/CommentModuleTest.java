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
public class CommentModuleTest extends TestCase {
    
    public CommentModuleTest(String testName) {
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
     * Test of parse method, of class CommentModule.
     */
    public void testParse() {
        System.out.println("parse comment");

        CommentModule instance = new CommentModule();
        
        ModuleOutput expResult = new ModuleOutput();
        ModuleOutput result = instance.parse("line", new ParsingContext(null, null, null));
        expResult.setControl(ModuleControl.CONTINUE);
        assertEquals(expResult, result);
        
        result = instance.parse("", new ParsingContext(null, null, null));
        expResult.setControl(ModuleControl.STOP);
        assertEquals(expResult, result);
        
        result = instance.parse("# whatever", new ParsingContext(null, null, null));
        expResult.setControl(ModuleControl.STOP);
        assertEquals(expResult, result);
    }
}

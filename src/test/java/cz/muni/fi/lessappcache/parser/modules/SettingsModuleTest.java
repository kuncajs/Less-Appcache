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
import java.util.HashMap;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class SettingsModuleTest extends TestCase {
    
    public SettingsModuleTest(String testName) {
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
     * Test of parse method, of class SettingsModule.
     */
    public void testParse() {
        System.out.println("parse settings");
        
        ParsingContext pc = new ParsingContext((Map) new HashMap<>(), "SETTINGS:", null);
        SettingsModule instance = new SettingsModule();
        
        ModuleOutput expResult = new ModuleOutput();
        expResult.setControl(ModuleControl.STOP);
        expResult.getOutput().add("prefer-online");
        ModuleOutput result = instance.parse("prefer-online", pc);
        assertEquals(expResult, result);
        
        pc.setMode("CACHE:");
        ModuleOutput exp2 = new ModuleOutput();
        exp2.setControl(ModuleControl.CONTINUE);
        result = instance.parse("anything", pc);
        assertEquals(exp2, result);
        
        pc.setMode("SETTINGS:");
        ModuleOutput exp3 = new ModuleOutput();
        exp3.setControl(ModuleControl.STOP);
        result = instance.parse("not-existing-setting", pc);
        assertEquals(exp3, result);
    }
}

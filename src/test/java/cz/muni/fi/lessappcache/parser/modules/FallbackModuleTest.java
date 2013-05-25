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
import static junit.framework.Assert.fail;
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class FallbackModuleTest extends TestCase {

    public FallbackModuleTest(String testName) {
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
     * Test of parse method, of class FallbackModule.
     */
    public void testParse() throws Exception {
        System.out.println("parse fallback");

        FallbackModule instance = new FallbackModule();
        Map<String, String> loaded = new HashMap<>();

        ModuleOutput result = instance.parse("res", new ParsingContext(null, "NETWORK:", null));
        ModuleOutput expResult = new ModuleOutput();
        expResult.setControl(ModuleControl.CONTINUE);
        assertEquals(expResult, result);

        result = instance.parse("res", new ParsingContext(null, "CACHE:", null));
        assertEquals(expResult, result);

        try {
            instance.parse("data", new ParsingContext(loaded, "FALLBACK:", Paths.get("")));
            fail("exception not thrown");
        } catch (ModuleException ex) {
        }

        try {
            instance.parse("data a q", new ParsingContext(loaded, "FALLBACK:", Paths.get("")));
            fail("exception not thrown");
        } catch (ModuleException ex) {
        }

        result = instance.parse("/ res", new ParsingContext(loaded, "FALLBACK:", Paths.get("")));
        expResult.setControl(ModuleControl.STOP);
        expResult.getOutput().add("/ res");
        expResult.getLoadedResources().put("res", null);
        assertEquals(expResult, result);

        try {
            instance.parse("file", new ParsingContext(loaded, "FALLBACK:", Paths.get("path")));
            fail("exc not thrown");
        } catch (ModuleException ex) {
        }

        result = instance.parse("/more file", new ParsingContext(loaded, "FALLBACK:", Paths.get("path")));
        expResult.getOutput().clear();
        expResult.getLoadedResources().clear();
        expResult.getOutput().add("/more "+Paths.get("path/file").toString());
        expResult.getLoadedResources().put(Paths.get("path/file").toString(), null);
        assertEquals(expResult, result);
    }
}

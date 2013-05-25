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
package cz.muni.fi.lessappcache.filters;

import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class FilterClassLoaderTest extends TestCase {

    public FilterClassLoaderTest(String testName) {
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
     * Test of loadClass method, of class FilterClassLoader.
     */
    public void testLoadClass() throws Exception {
        System.out.println("loadClass");
        FilterClassLoader instance = new FilterClassLoader();
        Class result = instance.loadClass("@glob");
        Class expResult = GlobFilter.class;
        assertEquals(expResult, result);
        result = instance.loadClass("@regex");
        expResult = RegexFilter.class;
        assertEquals(expResult, result);

        assertEquals(expResult, result);
        expResult = RGlobFilter.class;
        result = instance.loadClass("@r-glob");
        assertEquals(expResult, result);
        try {
            instance.loadClass("@non-existing");
            fail("Should throw exception");
        } catch (ClassNotFoundException ex) {
        }
    }
}

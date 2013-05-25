/*
 * Copyright 2013 Petr Kunc
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
package cz.muni.fi.lessappcache.filesystem;

import java.nio.file.Path;
import java.nio.file.Paths;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;

/**
 *
 * @author Petr
 */
public class PathUtilsTest extends TestCase {

    public PathUtilsTest(String testName) {
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
     * Test of relativizeFolders method, of class PathUtils.
     */
    public void testGetParent() {
        System.out.println("testGetParent");
        Path result = PathUtils.getParent(Paths.get(""));
        assertEquals(Paths.get(""), result);
        result = PathUtils.getParent(Paths.get("import.lesscache"));
        assertEquals(Paths.get(""), result);
        result = PathUtils.getParent(Paths.get("path/import.lesscache"));
        assertEquals(Paths.get("path"), result);
        result = PathUtils.getParent(Paths.get("base/path/import.lesscache"));
        assertEquals(Paths.get("base/path"), result);
        result = PathUtils.getParent(Paths.get("../file.lesscache"));
        assertEquals(Paths.get(".."), result);
        result = PathUtils.getParent(Paths.get("../../file.lesscache"));
        assertEquals(Paths.get("../.."), result);
        result = PathUtils.getParent(Paths.get("../other/file.lesscache"));
        assertEquals(Paths.get("../other"), result);
    }

    /**
     * Test of processResource method, of class PathUtils.
     */
    public void testProcessResource() {
        System.out.println("processResource");
        // Tests of absolute and remotes
        assertEquals("http://www.seznam.cz", PathUtils.processResource("http://www.seznam.cz", Paths.get("")));
        assertEquals("https://a.b/q/w/e", PathUtils.processResource("https://a.b/q/w/e", Paths.get("some/context/file.lesscache")));
        assertEquals("/file", PathUtils.processResource("/file", Paths.get("")));
        assertEquals("/abs/path/file", PathUtils.processResource("/abs/path/file", Paths.get("some/context/file.lesscache")));
        // Tests relative
        assertEquals(Paths.get("some/context/file.txt").toString(), PathUtils.processResource("file.txt", Paths.get("some/context")));
        assertEquals(Paths.get("some/context/file.txt").toString(), PathUtils.processResource("file.txt", Paths.get("some/context/")));
        assertEquals(Paths.get("some/file.txt").toString(), PathUtils.processResource("../file.txt", Paths.get("some/context")));
        assertEquals(Paths.get("../../file.txt").toString(), PathUtils.processResource("../file.txt", Paths.get("../")));
        assertEquals(Paths.get("../file.txt").toString(), PathUtils.processResource("file.txt", Paths.get("../")));
    }

    /**
     * Test of isAbsoluteOrRemote method, of class PathUtils.
     */
    public void testIsAbsoluteOrRemote() {
        System.out.println("isAbsoluteOrRemote");
        assertTrue(PathUtils.isAbsoluteOrRemote("/absolute"));
        assertTrue(PathUtils.isAbsoluteOrRemote("/any/absolute/paths"));
        assertTrue(PathUtils.isAbsoluteOrRemote("http://www.seznam.cz"));
        assertTrue(PathUtils.isAbsoluteOrRemote("https://is.muni.cz/auth/more/data"));
        assertTrue(PathUtils.isAbsoluteOrRemote("ftp://server"));
        assertTrue(PathUtils.isAbsoluteOrRemote("http://www.seznam.cz"));
        assertFalse(PathUtils.isAbsoluteOrRemote("relative path"));
        assertFalse(PathUtils.isAbsoluteOrRemote("../../data"));
        assertFalse(PathUtils.isAbsoluteOrRemote("./../../data.txt"));
        assertFalse(PathUtils.isAbsoluteOrRemote("relative/path"));
        assertFalse(PathUtils.isAbsoluteOrRemote("relative"));
    }

    /**
     * Test of isAbsolute method, of class PathUtils.
     */
    public void testIsAbsolute() {
        System.out.println("isAbsolute");
        assertTrue(PathUtils.isAbsolute("/absolute"));
        assertTrue(PathUtils.isAbsolute("/any/absolute/paths"));
        assertFalse(PathUtils.isAbsolute("relative path"));
        assertFalse(PathUtils.isAbsolute("../../data"));
        assertFalse(PathUtils.isAbsolute("http://www.seznam.cz"));
    }

    /**
     * Test of isRemote method, of class PathUtils.
     */
    public void testIsRemote() {
        System.out.println("isRemote");
        assertTrue(PathUtils.isRemote("http://www.seznam.cz"));
        assertTrue(PathUtils.isRemote("https://is.muni.cz/auth/more/data"));
        assertTrue(PathUtils.isRemote("ftp://server"));
        assertFalse(PathUtils.isRemote("relative/path"));
        assertFalse(PathUtils.isRemote("relative"));
        assertFalse(PathUtils.isRemote("/absolute/path"));
        assertFalse(PathUtils.isRemote("./../../data.txt"));
    }
}

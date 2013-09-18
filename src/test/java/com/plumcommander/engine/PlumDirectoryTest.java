package com.plumcommander.engine;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class PlumDirectoryTest {

    public static final String PATH_NAME = "test_basic_scenario";
    public static final String PATH_NAME_2 = "test_deeper_scenario";

    private static DirectoryService directoryService;

    @BeforeClass
    public static void testSetUp() throws Exception {
        new File(PATH_NAME).mkdir();

        Paths.get(PATH_NAME, "text1.txt").toFile().createNewFile();
        Paths.get(PATH_NAME, "text2.txt").toFile().createNewFile();

        Paths.get(PATH_NAME, PATH_NAME_2).toFile().mkdir();
        Paths.get(PATH_NAME, PATH_NAME_2, "textA.txt").toFile().createNewFile();
    }

    @AfterClass
    public static void testTearDown() throws Exception {
        FileUtils.deleteDirectory(new File(PATH_NAME));
    }

    @Before
    public void before() throws Exception {
        directoryService = new PlumDirectory();
        directoryService.changeDirectory(PATH_NAME);
    }

    @Test
    public void testGetFiles() throws Exception {
        List<File> files = directoryService.getFiles();

        Assert.assertEquals(files.size(), 3);
    }

    @Test
    public void testGetCurrentPath() throws Exception {
        String currentPath = directoryService.getCurrentPath();

        Assert.assertTrue(currentPath.endsWith(PATH_NAME));
    }

    @Test
    public void testChangeDirectory() throws Exception {
        directoryService.changeDirectory(PATH_NAME_2);

        String currentPath = directoryService.getCurrentPath();
        Assert.assertTrue(currentPath.endsWith(PATH_NAME_2));

        List<File> files = directoryService.getFiles();
        Assert.assertEquals(files.size(), 1);
    }
}

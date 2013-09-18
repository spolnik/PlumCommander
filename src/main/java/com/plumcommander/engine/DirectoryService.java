package com.plumcommander.engine;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DirectoryService {
    String getCurrentPath();

    void changeDirectory(String directoryPath) throws IOException;

    List<File> getFiles();
}

package com.plumcommander.engine;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.google.common.base.Preconditions.*;

final class PlumDirectory implements DirectoryService {

    private Path currentPath;

    PlumDirectory() throws IOException {
        currentPath = Paths.get(".").toRealPath();
    }

    public static void main(String[] args) throws IOException {

        String command = null;
        DirectoryService directoryService = new PlumDirectory();

        do {

            System.out.print("Command (LS, CD, PWD, EXIT): ");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String path = ".";

            try {
                String commandLine = br.readLine();
                String[] commandArgs = commandLine.split(" ");

                command = commandArgs[0].toUpperCase();

                if (commandArgs.length > 1)
                    path = commandArgs[1];

                switch (command) {
                    case "CD":
                        directoryService.changeDirectory(path);
                        break;
                    case "PWD":
                        System.out.println(directoryService.getCurrentPath());
                        break;
                    case "LS":
                        for (File file : directoryService.getFiles())
                            System.out.println(file.getName());
                        break;
                    case "EXIT":
                        System.out.println("Existing from program.");
                        break;
                    default:
                        System.err.println("Wrong command: " + commandArgs[0]);
                        break;
                }

            } catch (IOException ioe) {
                System.err.println(ioe.getMessage());
            }

        } while (!Objects.equals(command, "EXIT"));
    }

    @Override
    public String getCurrentPath() {
        return currentPath.toString();
    }

    @Override
    public void changeDirectory(String directoryPath) throws IOException {
        checkArgument(!Strings.isNullOrEmpty(directoryPath), "Directory path cannot be null nor empty");

        Path path = getRealPath(directoryPath);

        if (!Files.exists(path))
            throw new FileNotFoundException("File doesn't exist: " + path.toString());

        if (Files.isDirectory(path))
            currentPath = path;
        else
            throw new IOException("File is not directory!");

    }

    @Override
    public List<File> getFiles() {
        File[] files = currentPath.toFile().listFiles();

        if (files == null)
            return Lists.newArrayList();

        Arrays.sort(files, getFilesComparator());
        return Arrays.asList(files);
    }

    private Path getRealPath(String directoryPath) throws IOException {
        boolean isAbsolute = new File(directoryPath).isAbsolute();

        return (isAbsolute
                ? Paths.get(directoryPath)
                : Paths.get(currentPath.toString(), directoryPath)
        ).toRealPath();
    }

    private Comparator<File> getFilesComparator() {
        return new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                if (f1.isDirectory() && !f2.isDirectory()) {
                    // Directory before non-directory
                    return -1;
                } else if (!f1.isDirectory() && f2.isDirectory()) {
                    // Non-directory after directory
                    return 1;
                } else {
                    // Alphabetic order otherwise
                    return f1.compareTo(f2);
                }
            }
        };
    }
}

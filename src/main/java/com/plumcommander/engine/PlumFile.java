package com.plumcommander.engine;

import com.google.common.base.Objects;
import com.google.common.io.Files;

import java.io.*;
import java.nio.charset.Charset;

final class PlumFile implements FileService {
    private final String filePath;

    public PlumFile(String filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) throws IOException {
        System.out.print("File name: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String filePath = br.readLine();

        PlumFile file = new PlumFile(filePath);
        String text = file.getContentAsText();

        System.out.println(text);

    }

    @Override
    public String getContentAsText() throws IOException {
        return Files.toString(new File(filePath), Charset.defaultCharset());
    }

    @Override
    public int hashCode() {
        throw new AssertionError(); // Method is never called
    }

    @Override
    public boolean equals(Object obj) {
        throw new AssertionError(); // Method is newer called
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("filePath", filePath)
                .toString();
    }
}

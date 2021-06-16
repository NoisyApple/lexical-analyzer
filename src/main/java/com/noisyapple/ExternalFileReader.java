package com.noisyapple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// Class for managing file reading.
public class ExternalFileReader {

    // Reads the file found in the given path and returns a String with the content.
    public static String fileToString(String path) {
        String contentCopy = "";

        try {
            BufferedReader bR = new BufferedReader(new FileReader(new File(path)));

            int c = 0;

            while ((c = bR.read()) != -1) {
                contentCopy += (char) c;
            }

            bR.close();

        } catch (Exception e) {
            System.out.println("File not found at specified path.");
        }

        return contentCopy;
    }

}

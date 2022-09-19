package com.noisyapple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

// Class for containing multiple static utility methods.
public class Utils {

    // Reads the file found in the given path and returns a String with the content.
    public static String fileToString(String path) {
        String contentCopy = "";

        try {
            BufferedReader bR = new BufferedReader(new FileReader(new File(path)));

            String line;

            while ((line = bR.readLine()) != null) {
                contentCopy += line + "\n";
            }

            bR.close();

        } catch (Exception e) {
            System.out.println("File not found at specified path.");
        }

        return contentCopy;
    }

    // Returns the line of a file based on the given index of a pointer.
    public static int getLineBasedOnIndex(String file, int index) {

        int pointer = 0;
        int lineNumber = 1;

        if (index > file.length()) {
            return -1;
        }

        while (pointer <= index) {
            char currentCharacter = file.charAt(pointer);

            if (currentCharacter == '\n')
                lineNumber++;

            pointer++;
        }

        return lineNumber;
    }

}

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

    // Returns the line number based on the given index.
    public static int getLineNumber(String file, int index) {

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

    // Returns the contents of a line based on the given line number.
    public static String getLineContent(String file, int lineNumber) {
        int pointer = 0;
        int currentLineNumber = 1;
        String lineContent = "";

        while (pointer <= file.length() && currentLineNumber != lineNumber) {
            char currentCharacter = file.charAt(pointer);

            if (currentCharacter == '\n')
                currentLineNumber++;

            pointer++;
        }

        while (pointer <= file.length() && currentLineNumber == lineNumber) {
            char currentCharacter = file.charAt(pointer);

            if (currentCharacter == '\n')
                currentLineNumber++;

            lineContent += (currentCharacter != '\n') ? currentCharacter : "";

            pointer++;
        }

        return lineContent;
    }

}

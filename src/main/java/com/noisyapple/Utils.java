package com.noisyapple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

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

    // Takes a grammar's data and returns its parts in an array with multiple arrays.
    public static String[][] getGrammarParts(String grammarFile) {

        String[] productionRules = grammarFile.split("\n");

        ArrayList<String> nonTerminalSymbols = new ArrayList<String>();
        ArrayList<String> terminalSymbols = new ArrayList<String>();
        ArrayList<String> rulesRightSides = new ArrayList<String>();

        Arrays.stream(productionRules).forEach(pR -> {
            String[] ruleSides = pR.split(" -> ");

            if (ruleSides.length != 2) {
                throw new Error("Inconsistent production rule found.");
            }

            if (!nonTerminalSymbols.contains(ruleSides[0])) {
                nonTerminalSymbols.add(ruleSides[0]);
            }

            rulesRightSides.add(ruleSides[1]);
        });

        rulesRightSides.forEach(rS -> {
            String[] symbols = rS.split(" ");

            for (String s : symbols) {
                if (!terminalSymbols.contains(s) && !nonTerminalSymbols.contains(s)) {
                    terminalSymbols.add(s);
                }
            }
        });

        return new String[][] {nonTerminalSymbols.toArray(new String[0]),
                terminalSymbols.toArray(new String[0]), rulesRightSides.toArray(new String[0])};

    }

}

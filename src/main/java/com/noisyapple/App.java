package com.noisyapple;

public class App {
    public static void main(String[] args) {

        if (args.length > 0) {

            // Takes the path of the file from args.
            String fileData = ExternalFileReader.fileToString(args[0]);

            LexicalAnalyzer lA = new LexicalAnalyzer(fileData);

            lA.startAnalysis();
            System.out.println(lA.toString());

        } else {
            throw new Error("No path specified.");
        }
    }
}

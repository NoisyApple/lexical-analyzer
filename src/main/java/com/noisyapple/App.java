package com.noisyapple;

public class App {
    public static void main(String[] args) {

        // (;|=|\+|-|/|\(|\)|,)
        Token t = new Token(",", (int) ',');

        System.out.println(t);

        // if (args.length > 0) {

        // String fileData = ExternalFileReader.fileToString(args[0]);

        // LexicalAnalyzer lA = new LexicalAnalyzer(fileData);

        // lA.startAnalysis();

        // } else {
        // throw new Error("No path specified.");
        // }
    }
}

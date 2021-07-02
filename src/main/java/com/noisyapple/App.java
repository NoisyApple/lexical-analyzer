package com.noisyapple;

public class App {
    public static void main(String[] args) {

        if (args.length == 2) {

            // Takes the paths of the files from args.
            String codeFile = Utils.fileToString(args[0]);
            String grammarFile = Utils.fileToString(args[1]);

            LexicalAnalyzer lA = new LexicalAnalyzer(codeFile);

            lA.startAnalysis();
            System.out.println(lA.toString());

            Grammar g = new Grammar(grammarFile);

            System.out.println(g.toString());

        } else {
            throw new Error("No input files paths specified.");
        }
    }
}

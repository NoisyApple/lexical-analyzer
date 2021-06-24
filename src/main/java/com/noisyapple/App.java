package com.noisyapple;

import java.util.Arrays;

public class App {
    public static void main(String[] args) {

        if (args.length == 2) {

            // Takes the paths of the files from args.
            String codeFile = Utils.fileToString(args[0]);
            String grammarFile = Utils.fileToString(args[1]);

            LexicalAnalyzer lA = new LexicalAnalyzer(codeFile);

            lA.startAnalysis();
            System.out.println(lA.toString());

            String[][] grammarParts = Utils.getGrammarParts(grammarFile);

            String[] nonTerminalSymbols = grammarParts[0];
            String[] terminalSymbols = grammarParts[1];
            String[] rulesRightSides = grammarParts[2];

            String grammarData = "";

            grammarData += "--------------[NON TERMINALS]--------------\n";
            grammarData += Arrays.toString(nonTerminalSymbols) + "\n\n";

            grammarData += "----------------[TERMINALS]----------------\n";
            grammarData += Arrays.toString(terminalSymbols) + "\n\n";

            grammarData += "---------------[RIGHT SIDES]---------------\n";
            grammarData += Arrays.toString(rulesRightSides);

            System.out.println(grammarData);

        } else {
            throw new Error("No input files paths specified.");
        }
    }
}

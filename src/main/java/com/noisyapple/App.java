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

            grammarData += "------------------[FILE]-------------------\n";
            grammarData += grammarFile + "\n\n";

            grammarData += "--------------[NON TERMINALS]--------------\n";
            for (String s : nonTerminalSymbols)
                grammarData += s + "\n";

            grammarData += "\n\n----------------[TERMINALS]----------------\n";
            for (String s : terminalSymbols)
                grammarData += s + "\n";

            grammarData += "\n\n---------------[RIGHT SIDES]---------------\n";
            for (String s : rulesRightSides)
                grammarData += s + "\n";

            System.out.println("\n\n" + grammarData);

            Stack<String> stringsStack = new Stack<String>();

            System.out.println(stringsStack.size()); // 0

            System.out.println(stringsStack.isEmpty()); // true

            stringsStack.push("A"); // ["A"]
            stringsStack.push("B"); // ["A", "B"]
            stringsStack.push("C"); // ["A", "B", "C"]

            System.out.println(stringsStack); // ["A", "B", "C"]

            System.out.println(stringsStack.pop()); // "C"

            System.out.println(stringsStack); // ["A", "B"];

            System.out.println(stringsStack.peek()); // "B"

            System.out.println(stringsStack); // ["A", "B"];

            System.out.println(stringsStack.size()); // 2

            System.out.println(stringsStack.isEmpty()); // false

        } else {
            throw new Error("No input files paths specified.");
        }
    }
}

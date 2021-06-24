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

        LinkedList<Integer> list = new LinkedList<Integer>();

        System.out.println(list);


        list.add(1);

        System.out.println(list);

        list.add(5);

        System.out.println(list);

        System.out.println(list.get(0));
        System.out.println(list.getLast());
        System.out.println(list.getFirst());

        list.add(8);
        list.add(7);
        list.add(3);
        list.add(2);
        list.add(4);
        list.add(9);

        System.out.println(list);

        list.removeLast();

        System.out.println(list);

        list.removeFirst();

        System.out.println(list);

        list.remove(4);

        System.out.println(list);

        list.remove(0);

        System.out.println(list);


        System.out.println(list.getSize());

        list.remove(list.getSize() - 1);

        System.out.println(list);


    }
}

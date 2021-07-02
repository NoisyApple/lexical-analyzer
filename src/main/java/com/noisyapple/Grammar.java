package com.noisyapple;

import java.util.ArrayList;
import java.util.Arrays;

// Contains the program grammar.
public class Grammar {

    private String[] productionRules;
    private String[] nonTerminalSymbols;
    private String[] terminalSymbols;
    private String[] rulesRightSides;

    // Class constructor.
    public Grammar(String grammar) {

        String[] productionRules = grammar.split("\n");

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
                if (!terminalSymbols.contains(s) && !nonTerminalSymbols.contains(s)
                        && !s.equals("ε")) {
                    terminalSymbols.add(s);
                }
            }
        });

        this.productionRules = productionRules;
        this.nonTerminalSymbols = nonTerminalSymbols.toArray(new String[0]);
        this.terminalSymbols = terminalSymbols.toArray(new String[0]);
        this.rulesRightSides = rulesRightSides.toArray(new String[0]);

    }

    @Override
    public String toString() {
        String grammarData = "";

        grammarData += "------------[PRODUCTION RULES]-------------\n";
        for (String s : productionRules)
            grammarData += s + "\n";

        grammarData += "\n\n--------------[NON TERMINALS]--------------\n";
        for (String s : nonTerminalSymbols)
            grammarData += s + "\n";

        grammarData += "\n\n----------------[TERMINALS]----------------\n";
        for (String s : terminalSymbols)
            grammarData += s + "\n";

        grammarData += "\n\n---------------[RIGHT SIDES]---------------\n";
        for (String s : rulesRightSides)
            grammarData += s + "\n";

        System.out.println("Hey There!");


        return "\n\n" + grammarData;
    }

}

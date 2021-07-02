package com.noisyapple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Contains the program grammar.
public class Grammar {

    public final String EPSILON = "ε";

    private String[] productionRules;
    private String[] nonTerminalSymbols;
    private String[] terminalSymbols;
    private String[] rulesLeftSides;
    private String[] rulesRightSides;

    // Class constructor.
    public Grammar(String grammar) {

        String[] productionRules = grammar.split("\n");

        ArrayList<String> nonTerminalSymbols = new ArrayList<String>();
        ArrayList<String> terminalSymbols = new ArrayList<String>();
        ArrayList<String> rulesLeftSides = new ArrayList<String>();
        ArrayList<String> rulesRightSides = new ArrayList<String>();

        Arrays.stream(productionRules).forEach(pR -> {
            String[] ruleSides = pR.split(" -> ");

            if (ruleSides.length != 2) {
                throw new Error("Inconsistent production rule found.");
            }

            if (!nonTerminalSymbols.contains(ruleSides[0])) {
                nonTerminalSymbols.add(ruleSides[0]);
            }

            rulesLeftSides.add(ruleSides[0]);
            rulesRightSides.add(ruleSides[1]);
        });

        rulesRightSides.forEach(rS -> {
            String[] symbols = rS.split(" ");

            for (String s : symbols) {
                if (!terminalSymbols.contains(s) && !nonTerminalSymbols.contains(s)
                        && !s.equals(EPSILON)) {
                    terminalSymbols.add(s);
                }
            }
        });

        this.productionRules = productionRules;
        this.nonTerminalSymbols = nonTerminalSymbols.toArray(new String[0]);
        this.terminalSymbols = terminalSymbols.toArray(new String[0]);
        this.rulesLeftSides = rulesLeftSides.toArray(new String[0]);
        this.rulesRightSides = rulesRightSides.toArray(new String[0]);

    }

    public void getParsingTable() {

        Arrays.stream(nonTerminalSymbols).forEach(e -> {
            System.out.println(e + " -> " + getFirstSet(e));
        });

    }

    // Returns the first-set of the given non terminal.
    private ParsingTableSet getFirstSet(String nonTerminalSymbol) {

        ParsingTableSet firstSet = new ParsingTableSet();
        List<Integer> leftSideOcurrences = new ArrayList<Integer>();

        for (int i = 0; i < rulesLeftSides.length; i++) {
            if (rulesLeftSides[i].equals(nonTerminalSymbol))
                leftSideOcurrences.add(i);
        }

        leftSideOcurrences.forEach(ruleIndex -> {
            String[] rightSide = rulesRightSides[ruleIndex].split(" ");

            firstSet.union(getFirstSet(rightSide, ruleIndex));
        });

        return firstSet;

    }

    // Returns the first-set of the given non terminals found in the right side.
    private ParsingTableSet getFirstSet(String[] rightSide, int ruleIndex) {

        ParsingTableSet firstSet = new ParsingTableSet();
        String firstElement = rightSide[0];

        if (Arrays.asList(terminalSymbols).contains(firstElement)) { // TYPE 1.
            firstSet.add(firstElement, ruleIndex);

        } else if (firstElement.equals(EPSILON)) { // TYPE 2.
            firstSet.add(EPSILON, ruleIndex);

        } else if (Arrays.asList(nonTerminalSymbols).contains(firstElement)) { // TYPE 3.

            int index = 1; // Skips first index.
            firstSet.union(getFirstSet(rightSide[0]));

            do {

                if (firstSet.contains(EPSILON)) {
                    firstSet.union(getFirstSet(rightSide[index]));
                } else {
                    break;
                }

            } while (index < rightSide.length);

            firstSet.forEach(e -> {
                e.setRuleIndex(ruleIndex);
            });

        }

        return firstSet;

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

        grammarData += "\n\n---------------[LEFT SIDES]--------------\n";
        for (String s : rulesLeftSides)
            grammarData += s + "\n";

        grammarData += "\n\n---------------[RIGHT SIDES]---------------\n";
        for (String s : rulesRightSides)
            grammarData += s + "\n";

        System.out.println("Hey There!");


        return "\n\n" + grammarData;
    }

}

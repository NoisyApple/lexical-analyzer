package com.noisyapple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Contains the program grammar.
public class Grammar {

    public final String EPSILON = "ε";

    private String startSymbol;
    private String[] productionRules;
    private String[] nonTerminalSymbols;
    private String[] terminalSymbols;
    private String[] rulesLeftSides;
    private String[] rulesRightSides;
    private ParsingTable parsingTable;

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
        this.parsingTable = new ParsingTable(calculateParsingTable(), this.terminalSymbols,
                this.nonTerminalSymbols);

        // Filters all non terminal symbols which are not in any of the right sides.
        String[] startSymbols = Arrays.stream(this.nonTerminalSymbols).filter(nT -> {
            for (String rS : this.rulesRightSides) {
                if (Arrays.asList(rS.split(" ")).contains(nT)) {
                    return false;
                }
            }

            return true;
        }).toArray(String[]::new);

        if (startSymbols.length == 1) {
            this.startSymbol = startSymbols[0];
        } else {
            throw new Error("Multiple start symbols found");
        }

    }

    private int[][] calculateParsingTable() {

        int[][] parsingTable = new int[nonTerminalSymbols.length][terminalSymbols.length];

        // All cells are set to -1.
        for (int i = 0; i < parsingTable.length; i++) {
            for (int j = 0; j < parsingTable[i].length; j++) {
                parsingTable[i][j] = -1;
            }
        }

        for (String nT : nonTerminalSymbols) {
            int nTIndex = Arrays.asList(nonTerminalSymbols).indexOf(nT);

            ParsingTableSet firstSet = getFirstSet(nT);

            firstSet.forEach(eFirst -> {
                if (!eFirst.getSymbol().equals(EPSILON)) {
                    int tIndex = Arrays.asList(terminalSymbols).indexOf(eFirst.getSymbol());

                    parsingTable[nTIndex][tIndex] = eFirst.getRuleIndex();
                } else {

                    ParsingTableSet followSet = getFollowSet(nT);

                    followSet.forEach(eFollow -> {
                        int tIndex = Arrays.asList(terminalSymbols).indexOf(eFollow.getSymbol());

                        parsingTable[nTIndex][tIndex] = eFirst.getRuleIndex();

                    });

                }
            });
        }

        return parsingTable;

    }

    // Returns the first-set of the given non terminal.
    private ParsingTableSet getFirstSet(String symbol) {

        ParsingTableSet firstSet = new ParsingTableSet();
        List<Integer> leftSideOccurrences = new ArrayList<Integer>();

        // symbol is terminal.
        if (Arrays.asList(terminalSymbols).contains(symbol)) {
            firstSet.add(symbol, -1);
            return firstSet;
        }

        for (int i = 0; i < rulesLeftSides.length; i++) {
            if (rulesLeftSides[i].equals(symbol))
                leftSideOccurrences.add(i);
        }

        leftSideOccurrences.forEach(ruleIndex -> {
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

    private ParsingTableSet getFollowSet(String symbol) {

        ParsingTableSet followSet = new ParsingTableSet();
        List<Integer> rightSideOccurrences = new ArrayList<Integer>();

        if (symbol.equals(startSymbol)) { // TYPE 1.
            followSet.add("$", -1);
            return followSet;
        }

        for (int i = 0; i < rulesRightSides.length; i++) {
            if (Arrays.asList(rulesRightSides[i].split(" ")).contains(symbol))
                rightSideOccurrences.add(i);
        }

        rightSideOccurrences.forEach(ruleIndex -> {
            followSet.union(getFollowSet(symbol, ruleIndex));
        });

        return followSet;
    }

    private ParsingTableSet getFollowSet(String symbol, int ruleIndex) {

        ParsingTableSet followSet = new ParsingTableSet();

        String[] rightSideSymbols = rulesRightSides[ruleIndex].split(" ");

        for (int i = 0; i < rightSideSymbols.length; i++) {

            String currentSymbol = rightSideSymbols[i];
            String A = rulesLeftSides[ruleIndex];

            if (!currentSymbol.equals(symbol)) {
                continue;
            }

            // B = symbol
            if (i == rightSideSymbols.length - 1) { // A -> aB

                // Fo(B) = Fo(A) union Fo(B)
                if (!A.equals(symbol))
                    followSet.union(getFollowSet(A));

            } else { // A -> aBb
                ParsingTableSet bFirst = getFirstSet(rightSideSymbols[i + 1]);

                // Fi(b) = Fi(b) - EPSILON
                boolean didBFirstContainE = bFirst.remove(EPSILON);

                // Fo(B) = Fi(b) union Fo(B)
                followSet.union(bFirst);

                if (didBFirstContainE) {

                    // Fo(B) = Fo(A) union Fo(B)
                    if (!A.equals(symbol))
                        followSet.union(getFollowSet(A));

                }
            }
        }

        return followSet;

    }

    public String[] getRightSideArrayByIndex(int index) {

        String[] rightSideArray = rulesRightSides[index].split(" ");

        if (Arrays.asList(rightSideArray).contains(EPSILON))
            return new String[] {};

        return rightSideArray;
    }

    public int getParsingTableValue(String nonTerminalSymbol, String terminalSymbol) {

        int row = Arrays.asList(nonTerminalSymbols).indexOf(nonTerminalSymbol);
        int column = Arrays.asList(terminalSymbols).indexOf(terminalSymbol);

        return parsingTable.getTable()[row][column];
    }

    public boolean isNonTerminal(String symbol) {
        return Arrays.asList(nonTerminalSymbols).contains(symbol);
    }

    public boolean isTerminal(String symbol) {
        return Arrays.asList(terminalSymbols).contains(symbol);
    }

    // GETTERS +++

    public String[] getProductionRules() {
        return productionRules;
    }

    public String[] getNonTerminalSymbols() {
        return nonTerminalSymbols;
    }

    public String[] getTerminalSymbols() {
        return terminalSymbols;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public ParsingTable getParsingTable() {
        return parsingTable;
    }

    // GETTERS ---


    @Override
    public String toString() {
        String grammarData = "";

        // grammarData += "------------[PRODUCTION RULES]-------------\n";
        // for (String s : productionRules)
        // grammarData += s + "\n";

        grammarData += "--------------[NON TERMINALS]--------------\n";
        for (String s : nonTerminalSymbols)
            grammarData += s + "\n";

        grammarData += "\n\n----------------[TERMINALS]----------------\n";
        for (String s : terminalSymbols)
            grammarData += s + "\n";

        // grammarData += "\n\n---------------[LEFT SIDES]--------------\n";
        // for (String s : rulesLeftSides)
        // grammarData += s + "\n";

        grammarData += "\n\n---------------[RIGHT SIDES]---------------\n";
        for (String s : rulesRightSides)
            grammarData += s + "\n";

        return "\n\n" + grammarData;
    }

}

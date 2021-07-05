package com.noisyapple;

import java.util.Arrays;

public class ParsingTable {

    private int[][] table;
    private String[] terminalSymbols;
    private String[] nonTerminalSymbols;

    public ParsingTable(int[][] table, String[] terminalSymbols, String[] nonTerminalSymbols) {
        this.table = table;
        this.terminalSymbols = terminalSymbols;
        this.nonTerminalSymbols = nonTerminalSymbols;
    }

    public int[][] getTable() {
        return table;
    }

    @Override
    public String toString() {

        // TABLE STRINGIFICATION +++

        String stringifiedTable = "\t\t\u2503";

        for (int i = 0; i < terminalSymbols.length; i++) {

            String stringifiedSymbol = (terminalSymbols[i].length() < 8) ? terminalSymbols[i] + "\t"
                    : terminalSymbols[i].substring(0, 7);

            stringifiedTable +=
                    stringifiedSymbol + ((i != terminalSymbols.length - 1) ? "\u2503" : "\n");
        }

        stringifiedTable +=
                "\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u2501\u254B";

        for (int i = 0; i < terminalSymbols.length; i++) {
            stringifiedTable += "\u2501\u2501\u2501\u2501\u2501\u2501\u2501"
                    + ((i != terminalSymbols.length - 1) ? "\u254B" : "\n");
        }

        for (String nT : nonTerminalSymbols) {

            String stringifiedSymbol = (nT.length() <= 16) ? nT : nT.substring(0, 16);
            int[] row = table[Arrays.asList(nonTerminalSymbols).indexOf(nT)];

            stringifiedTable += stringifiedSymbol;

            for (int i = 0; i < Math.abs((nT.length() / 8) - 2); i++)
                stringifiedTable += "\t";

            stringifiedTable += "\u2503";

            for (int j = 0; j < row.length; j++) {
                stringifiedTable += ((row[j] != -1) ? row[j] : "") + "\t"
                        + ((j != row.length - 1) ? "\u2503" : "\n");
            }

        }

        return stringifiedTable;

        // TABLE STRINGIFICATION ---
    }

}

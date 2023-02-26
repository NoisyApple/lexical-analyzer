package com.noisyapple;

import java.util.Arrays;
import java.util.LinkedList;

// Models a SymbolTable.
public class SymbolTable {

    private LinkedList<Symbol> table;

    // Class constructor.
    public SymbolTable() {
        table = new LinkedList<Symbol>();
    }

    // Installs a token only if it is not already installed.
    public void installToken(Token token, int lineNumber) {

        // If the lexeme of an already installed token matches the passed, the
        // execution stops so that a token is never installed twice.
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getLexeme().equals(token.getLexeme())) {
                table.get(i).increaseOccurences();
                table.get(i).addLine(lineNumber);
                return;
            }
        }

        table.add(new Symbol(token, lineNumber));
    }

    public String resolveOperationType(Symbol symbolA, Symbol symbolB, String actionType) {
        String symbolAType = symbolA.getType();
        String symbolBType = symbolB.getType();
        String operationType = "";

        if (symbolAType == "INTEGER" && symbolBType == "INTEGER") {
            operationType = "INTEGER";
        } else if ((symbolAType == "INTEGER") && (symbolBType == "FLOAT")) {
            operationType = "FLOAT";
        } else if (symbolAType == "FLOAT" && symbolBType == "INTEGER") {
            operationType = "FLOAT";
        } else if (symbolAType == "FLOAT" && symbolBType == "FLOAT") {
            operationType = "FLOAT";
        } else {
            throw new Error("Semantic error ocurred");
        }

        if (actionType == "EXPRESSION") {
            actionType = operationType;
        }

        return operationType;

    }

    // Returns a String with the data of the table.
    @Override
    public String toString() {
        String data = "[[LEXEME], [TOKEN], [TYPE], [ATTRIBUTE], [OCCURENCES], [LINES]]\n";

        for (int i = 0; i < table.size(); i++) {
            data += table.get(i) + "\n";
        }

        return data;
    }

}

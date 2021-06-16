package com.noisyapple;

import java.util.LinkedList;

// Models a SymbolTable.
public class SymbolTable {

    private LinkedList<Token> table;

    // Class constructor.
    public SymbolTable() {
        table = new LinkedList<Token>();
    }

    // Installs a token only if it is not already installed.
    public void installToken(Token token) {

        // If the lexeme of an already installed token matches the passed, the execution stops so
        // that a token is never installed twice.
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getLexeme().equals(token.getLexeme()))
                return;
        }

        table.add(token);
    }

    // Returns a String with the data of the table.
    public String toString() {
        String data = "[[LEXEME], [CLASSIFICATION], [ATTRIBUTE]]\n";

        for (int i = 0; i < table.size(); i++) {
            data += table.get(i) + "\n";
        }

        return data;
    }

}

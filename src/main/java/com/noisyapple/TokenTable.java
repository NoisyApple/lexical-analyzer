package com.noisyapple;

import java.util.LinkedList;

// Models a TokenTable.
public class TokenTable {

    private LinkedList<Token> table;

    // Class constructor.
    public TokenTable() {
        table = new LinkedList<Token>();
    }

    // Adds a token to the table.
    public void addToken(Token token) {
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

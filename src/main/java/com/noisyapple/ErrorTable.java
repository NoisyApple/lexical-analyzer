package com.noisyapple;

import java.util.LinkedList;

// Models an ErrorTable.
public class ErrorTable {

    private LinkedList<LexicalError> table;

    // Class constructor.
    public ErrorTable() {
        table = new LinkedList<LexicalError>();
    }

    // Adds a passed LexicalError to the table.
    public void addLexicalError(LexicalError error) {
        table.add(error);
    }

    // Returns a String with the data of the table.
    public String toString() {
        String data = "[[LEXEME], [LINE], [COLUMN]]\n";

        for (int i = 0; i < table.size(); i++) {
            data += table.get(i) + "\n";
        }

        return data;
    }

}

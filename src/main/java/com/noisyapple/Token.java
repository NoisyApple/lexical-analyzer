package com.noisyapple;

import java.util.Hashtable;

// Models a Token.
public class Token {

    // Classifications constant.
    private final Hashtable<Integer, String> CLASSIFICATIONS = new Hashtable<Integer, String>() {
        {
            put(40, "Open parenthesis");
            put(41, "Close parenthesis");
            put(43, "Plus sign");
            put(44, "Comma");
            put(45, "Minus sign");
            put(47, "Slash");
            put(59, "Semi colon");
            put(61, "Equals sign");
            put(295, "Identifier");
            put(300, "Reserved word");
        }
    };

    private String lexeme;
    private String classification;
    private int attribute;

    // Class constructor.
    public Token(String lexeme, int attribute) {
        this.lexeme = lexeme;
        this.attribute = attribute;

        classification = CLASSIFICATIONS.get(attribute);
    }

    // Returns a String with the data of the token.
    public String toString() {
        return "[" + lexeme + ", " + classification + ", " + attribute + "]";
    }

}

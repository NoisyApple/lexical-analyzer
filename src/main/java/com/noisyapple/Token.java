package com.noisyapple;

import java.util.Hashtable;

// Models a Token.
public class Token {

    // Public classification constant values.
    public static final int IDENTIFIER = 295;
    public static final int NATURAL_INTEGER_NUMBER = 296;
    public static final int ZERO = 297;
    public static final int FLOATING_POINT_NUMBER = 298;
    public static final int RESERVED_WORD = 300;


    // Reserved words constant.
    public static final Hashtable<String, Integer> RESERVED_WORDS =
            new Hashtable<String, Integer>() {
                {
                    put("Programa", 300);
                    put("Real", 301);
                    put("Entero", 302);
                    put("Leer", 303);
                    put("Escribir", 304);
                    put("Inicio", 305);
                    put("Fin", 306);
                }
            };

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
            put(296, "Natural integer number");
            put(297, "Zero");
            put(298, "Floating point number");
            put(300, "Reserved word");
            put(301, "Reserved word");
            put(302, "Reserved word");
            put(303, "Reserved word");
            put(304, "Reserved word");
            put(305, "Reserved word");
            put(306, "Reserved word");
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

    // GETTERS +++

    public String getLexeme() {
        return lexeme;
    }

    // GETTERS ---

    // Returns a String with the data of the token.
    @Override
    public String toString() {
        return "[" + lexeme + ", " + classification + ", " + attribute + "]";
    }

}

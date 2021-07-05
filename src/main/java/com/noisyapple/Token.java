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
    private final Hashtable<Integer, String> CLASSIFICATIONS = new Hashtable<Integer, String>() {
        {
            put(40, "(");
            put(41, ")");
            put(43, "+");
            put(44, ",");
            put(45, "-");
            put(47, "/");
            put(59, ";");
            put(61, "=");
            put(295, "id");
            put(296, "intliteral");
            put(297, "realliteral");
            put(298, "realliteral");
            put(300, "Programa");
            put(301, "Real");
            put(302, "Entero");
            put(303, "Leer");
            put(304, "Escribir");
            put(305, "Inicio");
            put(306, "Fin");
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

    public String getClassification() {
        return classification;
    }

    // GETTERS ---

    // Returns a String with the data of the token.
    @Override
    public String toString() {
        return "[" + lexeme + ", " + classification + ", " + attribute + "]";
    }

}

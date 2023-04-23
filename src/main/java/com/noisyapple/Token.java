package com.noisyapple;

import java.util.Hashtable;

// Models a Token.
public class Token {

    // Public classification constant values.
    public static final int NATURAL_INTEGER_NUMBER = 296;
    public static final int ZERO = 297;
    public static final int FLOATING_POINT_NUMBER = 298;
    public static final int RESERVED_WORD = 400;
    public static final int IDENTIFIER = 500;

    public static int IDENTIFIER_COUNT = 500;

    // Reserved words constant.
    protected static final Hashtable<String, Integer> RESERVED_WORDS = new Hashtable<String, Integer>() {
        {
            put("Programa", 400);
            put("Real", 401);
            put("Entero", 402);
            put("Leer", 403);
            put("Escribir", 404);
            put("Inicio", 405);
            put("Fin", 406);
        }
    };

    protected static final Hashtable<String, String> DATA_TYPE_RESERVED_WORDS = new Hashtable<String, String>() {
        {
            put("Real", "FLOAT");
            put("Entero", "INTEGER");
        }
    };

    protected static final Hashtable<Integer, String> CLASSIFICATIONS = new Hashtable<Integer, String>() {
        {
            put(40, "(");
            put(41, ")");
            put(43, "+");
            put(44, ",");
            put(45, "-");
            put(47, "/");
            put(59, ";");
            put(61, "=");
            put(296, "intliteral");
            put(297, "realliteral");
            put(298, "realliteral");
            put(400, "Programa");
            put(401, "Real");
            put(402, "Entero");
            put(403, "Leer");
            put(404, "Escribir");
            put(405, "Inicio");
            put(406, "Fin");
            put(500, "id");
        }
    };

    protected static final Hashtable<String, String> SPECIAL_SYMBOLS_MAP = new Hashtable<String, String>() {
        {
            put("(", "OPEN PARENTHESIS");
            put(")", "CLOSED PARENTHESIS");
            put("+", "ADDITION");
            put(",", "COMMA");
            put("-", "SUBTRACTION");
            put("/", "DIVISION");
            put(";", "SEMI COLON");
            put("=", "ASSIGN");
        }
    };

    private String lexeme;
    private String classification;
    private int attribute;
    private int tokenId;

    // Class constructor.
    public Token(String lexeme, int attribute) {
        this.lexeme = lexeme;
        this.attribute = attribute;

        switch (attribute) {
            case IDENTIFIER:
                // FIXME: While installing tokens in symbol table this is still getting
                // incremented, that should not be happening.
                this.tokenId = Token.IDENTIFIER_COUNT++;
                break;
            default:
                this.tokenId = attribute;
                break;
        }

        classification = CLASSIFICATIONS.get(attribute);
    }

    // GETTERS +++

    public String getLexeme() {
        return lexeme;
    }

    public String getClassification() {
        return classification;
    }

    public int getAttribute() {
        return attribute;
    }

    public int getTokenId() {
        return tokenId;
    }

    // GETTERS ---

    // Returns the type based on the token's attribute.
    public String getTypeByAttribute(int attribute) {
        String type;

        if (attribute >= RESERVED_WORD && attribute < IDENTIFIER) {
            type = "RESERVED WORD";
        } else if (attribute >= IDENTIFIER) {
            type = "IDENTIFIER";
        } else if (attribute == NATURAL_INTEGER_NUMBER || attribute == ZERO) {
            type = "INTEGER";
        } else if (attribute == FLOATING_POINT_NUMBER) {
            type = "FLOAT";
        } else {
            type = "SPECIAL SYMBOL";
        }

        return type;
    }

    // Returns a String with the data of the token.
    @Override
    public String toString() {
        String type;
        String classificationString;

        type = getTypeByAttribute(attribute);

        if (attribute >= RESERVED_WORD && attribute < IDENTIFIER) {
            classificationString = "RESERVED WORD";
        } else if (attribute >= IDENTIFIER) {
            classificationString = "IDENTIFIER";
        } else if (attribute == NATURAL_INTEGER_NUMBER || attribute == ZERO
                || attribute == FLOATING_POINT_NUMBER) {
            classificationString = "CONSTANT";
        } else {
            classificationString = SPECIAL_SYMBOLS_MAP.get(classification);
        }

        return "[" + lexeme + ", " + type + ", " + classificationString + ", " + tokenId + "]";
    }

}

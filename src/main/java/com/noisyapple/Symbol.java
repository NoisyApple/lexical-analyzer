package com.noisyapple;

import java.util.ArrayList;

// Models a Symbol, based on a Token with a few differences.
public class Symbol extends Token {

    private int occurences;
    private ArrayList<Integer> lines;
    private String type;

    // Class contructor.
    public Symbol(Token token, int lineNumber) {
        super(token.getLexeme(), token.getAttribute());

        occurences = 1;
        lines = new ArrayList<Integer>();
        int attribute = token.getAttribute();

        switch (attribute) {
            case NATURAL_INTEGER_NUMBER:
                type = "INTEGER";
                break;
            case ZERO:
                type = "INTEGER";
                break;
            case FLOATING_POINT_NUMBER:
                type = "FLOAT";
                break;
            default:
                type = "PROCEDURE";
                break;
        }

        lines.add(lineNumber);
    }

    // Increases the amount of occurences of the symbol.
    public void increaseOccurences() {
        occurences += 1;
    }

    // Adds the given line number to the list of lines where the symbol was found.
    public void addLine(int lineNumber) {
        lines.add(lineNumber);
    }

    // GETTERS +++

    public int getOcurrences() {
        return occurences;
    }

    public ArrayList<Integer> getLines() {
        return lines;
    }

    public String getType() {
        return type;
    }

    // GETTERS ---

    // SETTERS +++

    public void setType(String type) {
        this.type = type;
    }

    // SETTERS ---

    // Returns a String with the data of the symbol.
    @Override
    public String toString() {
        String classificationString;
        String classification = this.getClassification();
        String lexeme = this.getLexeme();
        String type = this.getType();
        int attribute = this.getAttribute();
        int tokenId = this.getTokenId();

        if (attribute >= RESERVED_WORD && attribute < IDENTIFIER) {
            classificationString = "RESERVED WORD";
        } else if (attribute >= IDENTIFIER) {
            classificationString = "IDENTIFIER";
        } else if (attribute == NATURAL_INTEGER_NUMBER || attribute == ZERO
                || attribute == FLOATING_POINT_NUMBER) {
            classificationString = "CONSTANT";
        } else {
            classificationString = Token.SPECIAL_SYMBOLS_MAP.get(classification);
        }

        return "[" + lexeme + ", " + classificationString + ", " + type + ", " +
                tokenId + ", " + occurences + ", " + lines + "]";
    }

}

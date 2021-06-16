package com.noisyapple;

// Models a LexicalError detected by the LexicalAnalyzer.
public class LexicalError {

    private String lexeme;
    private int errorLine;
    private int errorColumn;

    // Class constructor.
    public LexicalError(String lexeme, int errorLine, int errorColumn) {
        this.lexeme = lexeme;
        this.errorLine = errorLine;
        this.errorColumn = errorColumn;
    }

    // Returns a string with the data of the LexicalError.
    public String toString() {
        return "[" + lexeme + "," + errorLine + "," + errorColumn + "]";
    }

}

package com.noisyapple;

import java.util.ArrayList;
import java.util.Arrays;

// Models an Lexical analyzer.
public class LexicalAnalyzer {

    // State label constans.
    public static final String NATURAL_INTEGER_NUMBER = "B";
    public static final String ZERO = "C";
    public static final String FLOATING_POINT_NUMBER = "E";
    public static final String IDENTIFIER = "F";
    public static final String SINGLE_CHARACTER = "H";
    public static final String RESERVED_WORD = "J";

    // Constant list with all valid reserved words.
    private final ArrayList<String> RESERVED_WORDS = new ArrayList<String>() {
        {
            add("Programa");
            add("Real");
            add("Entero");
            add("Leer");
            add("Escribir");
            add("Inicio");
            add("Fin");
        }
    };

    private String file;
    private int indexA;
    private int indexB;
    private Automaton automaton;
    private SymbolTable symbolTable;
    private TokenTable tokenTable;
    private ArrayList<String> errorTable;

    // Class constructor.
    public LexicalAnalyzer(String file) {
        this.file = file;

        indexA = 0;
        indexB = 0;

        symbolTable = new SymbolTable();
        tokenTable = new TokenTable();
        errorTable = new ArrayList<String>();

        // States.
        State sA = new State("A", true, false);
        State sB = new State("B", false, true);
        State sC = new State("C", false, true);
        State sD = new State("D", false, false);
        State sE = new State("E", false, true);
        State sF = new State("F", false, true);
        State sG = new State("G", false, false);
        State sH = new State("H", false, true);
        State sI = new State("I", false, false);
        State sJ = new State("J", false, true);

        // Transitions.
        Transition tA_B = new Transition(sA, sB, "[1-9]");
        Transition tA_C = new Transition(sA, sC, "0");
        Transition tA_F = new Transition(sA, sF, "[a-z]");
        Transition tA_H = new Transition(sA, sH, ";|=|\\+|-|/|\\(|\\)|,");
        Transition tA_I = new Transition(sA, sI, "[A-Z]");
        Transition tB_B = new Transition(sB, sB, "[0-9]");
        Transition tB_D = new Transition(sB, sD, "\\.");
        Transition tC_D = new Transition(sC, sD, "\\.");
        Transition tD_E = new Transition(sD, sE, "[0-9]");
        Transition tE_E = new Transition(sE, sE, "[0-9]");
        Transition tF_F1 = new Transition(sF, sF, "[a-z]");
        Transition tF_F2 = new Transition(sF, sF, "[0-9]");
        Transition tF_G = new Transition(sF, sG, "_");
        Transition tG_F1 = new Transition(sG, sF, "[a-z]");
        Transition tG_F2 = new Transition(sG, sF, "[0-9]");
        Transition tI_J = new Transition(sI, sJ, "[a-z]");
        Transition tJ_J = new Transition(sJ, sJ, "[a-z]");

        // Both states and Transitions are wrapped inside ArrayList to be passed into Automaton
        // constructor.
        ArrayList<State> states =
                new ArrayList<State>(Arrays.asList(sA, sB, sC, sD, sE, sF, sG, sH, sI, sJ));
        ArrayList<Transition> transitions =
                new ArrayList<Transition>(Arrays.asList(tA_B, tA_C, tA_F, tA_H, tA_I, tB_B, tB_D,
                        tC_D, tD_E, tE_E, tF_F1, tF_F2, tF_G, tG_F1, tG_F2, tI_J, tJ_J));

        automaton = new Automaton(states, transitions);
    }

    // Checking wether the content of the file matches the automaton's lexical units.
    public Token getNextToken() {

        Token token = null;
        boolean tokenFound = false;


        // Iterates while indexB is less or equal than the size of the data.
        while (indexB <= file.length() && !tokenFound) {

            if (indexB != file.length()) {
                // Whenever a character is found it is passed into the automaton.
                automaton.insertInput(file.charAt(indexB));
            } else {
                // A null current state is forced into the automaton so it stops reading the last
                // lexeme.
                automaton.insertInput('!');
            }

            // Whenever the current state of the automaton is null.
            // This happens when a the automaton doesn't find a matching transition for the given
            // value.
            if (automaton.getCurrentState() == null) {

                // When both indexes are in the same position.
                if (indexA == indexB) {

                    // Lexical error.
                    if (indexB < file.length()) {



                        // Spaces and newline characters are excluded.
                        if ((int) file.charAt(indexB) != 10 && (int) file.charAt(indexB) != 32) {

                            throw new Error(
                                    "Lexical error: '" + file.charAt(indexB) + "' is not valid.");
                            // errorTable.add("'" + file.charAt(indexB) + "' is not valid.");
                        }
                    }

                    // Indexes move to next character.
                    indexA += 1;
                    indexB += 1;

                } else { // Lexeme found.

                    String lexeme = "";

                    // A string is built based on the position of both indexes.
                    for (int i = indexA; i < indexB; i++) {
                        lexeme += file.charAt(i);
                    }

                    // The lexeme is evaluated through the automaton. If lexeme is valid the
                    // execution will enter into the if bellow.
                    if (automaton.evaluate(lexeme)) {

                        // In order to consider a lexeme a reserved word the current state should be
                        // the specified and the lexeme should be declared within the RESERVED_WORDS
                        // constant.
                        if (automaton.getCurrentState().getLabel() == LexicalAnalyzer.RESERVED_WORD
                                && !RESERVED_WORDS.contains(lexeme)) {

                            throw new Error("Lexical error: '" + lexeme + "' is not valid.");


                            // Otherwise, a lexical error is generated.
                            // errorTable.add("'" + lexeme + "' is not valid.");

                            // If the current state after the lexeme is evaluated is the specified
                            // here, a token will be generated and will be considered as an
                            // identifier, therefore it will be added into both the symbol table and
                            // the token table.
                        } else if (automaton.getCurrentState()
                                .getLabel() == LexicalAnalyzer.IDENTIFIER) {

                            token = generateToken(lexeme, automaton.getCurrentState());

                            symbolTable.installToken(token);
                            tokenTable.addToken(token);

                            tokenFound = true;

                            // If not the token is still generated but only added into the token
                            // table.
                        } else {
                            token = generateToken(lexeme, automaton.getCurrentState());
                            tokenTable.addToken(token);

                            tokenFound = true;
                        }

                        // If the lexeme is not valid through the automaton.
                    } else {

                        throw new Error("Lexical error: '" + lexeme + "' is not valid.");



                        // Lexical error.
                        // errorTable.add("'" + lexeme + "' is not valid.");
                    }

                    // After a lexeme being found indexA is set into the place of indexB.
                    indexA = indexB;
                }

                // The automaton is reset on either case.
                automaton.reset();

                // If the current state is not null.
            } else {

                // IndexB keeps going.
                indexB += 1;
            }

        }

        return token;

    }

    // Returns a token based in the passed lexeme and State.
    public Token generateToken(String lexeme, State foundState) {

        Token generatedToken;

        // Lexemes found are categorized based on the state where they were found.
        switch (foundState.getLabel()) {
            case LexicalAnalyzer.NATURAL_INTEGER_NUMBER:
                generatedToken = new Token(lexeme, Token.NATURAL_INTEGER_NUMBER);
                break;
            case LexicalAnalyzer.ZERO:
                generatedToken = new Token(lexeme, Token.ZERO);
                break;
            case LexicalAnalyzer.FLOATING_POINT_NUMBER:
                generatedToken = new Token(lexeme, Token.FLOATING_POINT_NUMBER);
                break;
            case LexicalAnalyzer.IDENTIFIER:
                generatedToken = new Token(lexeme, Token.IDENTIFIER);
                break;
            case LexicalAnalyzer.SINGLE_CHARACTER:
                // Ascii value is set as token's attribute.
                generatedToken = new Token(lexeme, (int) lexeme.charAt(0));
                break;
            case LexicalAnalyzer.RESERVED_WORD:
                // The actual lexeme is needed in order to determine its attribute.
                generatedToken = new Token(lexeme, Token.RESERVED_WORDS.get(lexeme));
                break;
            default:
                // When a non-managed state is passed.
                throw new Error("State label doesn't match with states in automaton.");
        }

        return generatedToken;
    }

    // Returns a string with the data of the LexicalAnalyzer.
    @Override
    public String toString() {
        String data = "";

        data += "--------------[SYMBOL TABLE]--------------\n";
        data += symbolTable.toString() + "\n\n";

        data += "-------------[RESERVED WORDS]-------------\n";
        for (int i = 0; i < RESERVED_WORDS.size(); i++) {
            data += RESERVED_WORDS.get(i) + "\n";
        }
        data += "\n\n";

        data += "--------------[TOKEN TABLE]---------------\n";
        data += tokenTable.toString() + "\n\n";

        data += "--------------[ERROR TABLE]---------------\n";
        for (int i = 0; i < errorTable.size(); i++) {
            data += errorTable.get(i) + "\n";
        }

        return data;
    }
}

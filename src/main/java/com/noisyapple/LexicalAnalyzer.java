package com.noisyapple;

import java.util.ArrayList;
import java.util.Arrays;

// Models an Lexical analyzer.
public class LexicalAnalyzer {
    private String file;
    private int indexA;
    private int indexB;
    private Automaton automaton;
    private SymbolTable symbolTable;

    // Class constructor.
    public LexicalAnalyzer(String file) {
        this.file = file;

        indexA = 0;
        indexB = 0;

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

        ArrayList<State> states =
                new ArrayList<State>(Arrays.asList(sA, sB, sC, sD, sE, sF, sG, sH, sI, sJ));
        ArrayList<Transition> transitions =
                new ArrayList<Transition>(Arrays.asList(tA_B, tA_C, tA_F, tA_H, tA_I, tB_B, tB_D,
                        tC_D, tD_E, tE_E, tF_F1, tF_F2, tF_G, tG_F1, tG_F2, tI_J, tJ_J));

        automaton = new Automaton(states, transitions);
    }

    // Checking wether the content of the file matches the automaton's lexical units.
    public void startAnalysis() {

        while (indexB <= file.length()) {

            if (indexB != file.length()) {
                automaton.insertInput(file.charAt(indexB));
            } else {
                automaton.insertInput('!');

            }

            if (automaton.getCurrentState() == null) {
                if (indexA == indexB) {

                    // Lexical error.
                    if (indexB < file.length()) {
                        if ((int) file.charAt(indexB) != 10 && (int) file.charAt(indexB) != 32) {
                            System.out.println("'" + file.charAt(indexB) + "' is not valid.");
                        }
                    }

                    indexA += 1;
                    indexB += 1;

                } else { // Lexeme found.
                    String lexeme = "";

                    for (int i = indexA; i < indexB; i++) {
                        lexeme += file.charAt(i);
                    }

                    if (automaton.evaluate(lexeme)) {
                        System.out.println(
                                "'" + lexeme + "' found at state: " + automaton.getCurrentState());

                        addToSymbolTable(lexeme, automaton.getCurrentState());

                    } else {
                        // Lexical error.
                        System.out.println("'" + lexeme + "' is not valid.");
                    }

                    indexA = indexB;
                }

                automaton.reset();
            } else {
                indexB += 1;
            }

        }

    }

    // Adds the passed lexeme into the symbol table.
    public void addToSymbolTable(String lexeme, State foundState) {

    }
}

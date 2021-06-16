package com.noisyapple;

import java.util.ArrayList;

public class Automaton {
    private ArrayList<State> states; // Q
    private ArrayList<String> alphabet; // Sigma.
    private State initialState; // q0.
    private ArrayList<Transition> transitions; // Delta.
    private ArrayList<State> finalStates; // F.

    private State currentState;

    // Class constructor.
    public Automaton(ArrayList<State> states, ArrayList<String> alphabet,
            ArrayList<Transition> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.transitions = transitions;

        finalStates = new ArrayList<State>();

        ArrayList<State> initialStatesFound = new ArrayList<State>();

        // Filters all initial states.
        states.forEach(s -> {
            if (s.isInitial()) {
                initialStatesFound.add(s);
            }
        });

        // Filters all final states.
        states.forEach(s -> {
            if (s.isFinal()) {
                finalStates.add(s);
            }
        });

        // Errors.
        if (initialStatesFound.size() == 0) {
            throw new Error("No initial state found.");
        } else if (initialStatesFound.size() > 1) {
            throw new Error("More than one initial state was found.");
        }

        // If no errors, initialState will be only state found after filtering.
        initialState = initialStatesFound.get(0);

        // Which also will be currentState.
        currentState = initialState;
    }

    // Updates the automaton based in the character passed as input.
    public void insertInput(char c) {

    }

    // Returns the current state of the Automaton.
    public State getCurrentState() {
        return currentState;
    }

    // Returns a string with the characteristics of the Automaton.
    public String toString() {
        String data = "";

        data += "[STATES] => " + states + "\n";
        data += "[ALPHABET] => " + alphabet + "\n";
        data += "[INITIAL STATE] => " + initialState + "\n";
        data += "[TRANSITIONS] => " + transitions + "\n";
        data += "[FINAL STATES] => " + finalStates + "\n";
        data += "[CURRENT STATE] => " + currentState + "\n";

        return data;
    }

}

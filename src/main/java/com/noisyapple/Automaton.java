package com.noisyapple;

import java.util.ArrayList;
import java.util.regex.Pattern;

// Models an Automaton.
public class Automaton {
    private ArrayList<State> states; // Q
    private ArrayList<String> alphabet; // Sigma.
    private State initialState; // q0.
    private ArrayList<Transition> transitions; // Delta.
    private ArrayList<State> finalStates; // F.

    private State currentState;

    // Class constructor.
    public Automaton(ArrayList<State> states, ArrayList<Transition> transitions) {
        this.states = states;
        this.transitions = transitions;

        alphabet = new ArrayList<String>();
        finalStates = new ArrayList<State>();

        // Adds regExps found in transitions.
        transitions.forEach(t -> {
            if (!alphabet.contains(t.getRegExp())) {
                alphabet.add(t.getRegExp());
            }
        });

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
        ArrayList<Transition> currentStateTransitions = new ArrayList<Transition>();

        // Filters all transitions that have currentState as their origin state.
        transitions.forEach(t -> {
            if (t.getOriginState().equals(currentState)) {
                currentStateTransitions.add(t);
            }
        });

        // If transitions were found after filter.
        if (currentStateTransitions.size() > 0) {
            ArrayList<Transition> matchingTransitions = new ArrayList<Transition>();

            // Filters all found transitions that matches the pattern of the character passed.
            currentStateTransitions.forEach(t -> {
                if (Pattern.matches(t.getRegExp(), String.valueOf(c))) {
                    matchingTransitions.add(t);
                }
            });

            // Error.
            if (matchingTransitions.size() > 1) {
                throw new Error("More than one matching transition was found.");
            }

            if (matchingTransitions.size() == 0) {
                currentState = null;
            } else {
                // The destinationState of the matching transition is set as the new currentState.
                currentState = matchingTransitions.get(0).getDestinationState();
            }

        } else { // If not, throws an error.
            currentState = null;
        }

    }

    // Returns wether the passed input is valid within the automaton.
    public boolean evaluate(String input) {
        reset();

        for (int i = 0; i < input.length(); i++) {
            insertInput(input.charAt(i));

            if (getCurrentState() == null) {
                return false;
            }
        }

        return getCurrentState().isFinal();
    }

    // Resets automaton to its original state.
    public void reset() {
        currentState = initialState;
    }

    // Returns the current state of the Automaton.
    public State getCurrentState() {
        return currentState;
    }

    // Returns a string with the characteristics of the Automaton.
    @Override
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

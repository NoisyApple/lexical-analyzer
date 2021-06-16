package com.noisyapple;

// Models a State of an Automaton.
public class State {
    private String label;
    private boolean isInitial;
    private boolean isFinal;

    // Class constructor.
    public State(String label, boolean isInitial, boolean isFinal) {
        this.label = label;
        this.isInitial = isInitial;
        this.isFinal = isFinal;
    }

    // GETTERS +++

    public boolean isInitial() {
        return isInitial;
    }

    public boolean isFinal() {
        return isFinal;
    }

    // GETTERS ---

    // Returns a string with the data of the State.
    public String toString() {
        String data = "";

        data += label;
        data += isInitial || isFinal ? "(" : "";
        data += isInitial ? "I" : "";
        data += isFinal ? "F" : "";
        data += isInitial || isFinal ? ")" : "";

        return data;
    }
}

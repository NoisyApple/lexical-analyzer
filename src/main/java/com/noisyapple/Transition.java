package com.noisyapple;

// Models a Transition of an Automaton.
public class Transition {

    private State originState;
    private State destinationState;
    private String regExp;

    // Class constructor.
    public Transition(State originState, State destinationState, String regExp) {
        this.originState = originState;
        this.destinationState = destinationState;
        this.regExp = regExp;
    }

    // Returns a string with the data of the Transition.
    public String toString() {
        return "(" + originState + ", " + regExp + ", " + destinationState + ")";
    }

}

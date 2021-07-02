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

    // GETTERS +++

    public State getOriginState() {
        return originState;
    }

    public State getDestinationState() {
        return destinationState;
    }

    public String getRegExp() {
        return regExp;
    }

    // GETTERS ---

    // Returns a string with the data of the Transition.
    @Override
    public String toString() {
        return "(" + originState + ", " + regExp + ", " + destinationState + ")";
    }

}

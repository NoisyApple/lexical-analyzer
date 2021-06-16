package com.noisyapple;

import java.util.ArrayList;

public class Automaton {
    private ArrayList<State> states; // Q
    private ArrayList<String> alphabet; // Sigma.
    private State initialState; // q0.
    private ArrayList<Transition> transitions; // Delta.
    private ArrayList<State> finalStates; // F.
}

package com.noisyapple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {

        State sA = new State("A", true, false);
        State sB = new State("B", false, true);
        State sC = new State("C", false, true);

        Transition tA_B = new Transition(sA, sB, "[1-9]");
        Transition tA_C = new Transition(sA, sC, "0");
        Transition tB_B = new Transition(sB, sB, "[0-9]");

        ArrayList<State> states = new ArrayList<State>(Arrays.asList(sA, sB, sC));
        ArrayList<String> alphabet = new ArrayList<String>(Arrays.asList("[1-9]", "[0-9]", "0"));
        ArrayList<Transition> transitions =
                new ArrayList<Transition>(Arrays.asList(tA_B, tA_C, tB_B));

        Automaton m = new Automaton(states, alphabet, transitions);

        System.out.println(m);

        m.insertInput('9');

        System.out.println(m.getCurrentState());

        m.insertInput('1');

        System.out.println(m.getCurrentState());

        m.insertInput('2');

        System.out.println(m.getCurrentState());


        // if (args.length > 0) {

        // String fileData = ExternalFileReader.fileToString(args[0]);
        // System.out.println(fileData);

        // } else {
        // System.out.println("No path specified.");
        // }
    }
}

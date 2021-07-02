package com.noisyapple;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// Models a set of terminal symbols, each of them with a specified production rule index.
class TerminalSymbolSet implements Iterable<TerminalSymbol> {

    private int pointer;
    private List<TerminalSymbol> data = new ArrayList<TerminalSymbol>();

    // Adds the passed data into the set only if the symbol doesn't exists within the set.
    public void add(String symbol, int ruleIndex) {
        if (!contains(symbol)) {
            data.add(new TerminalSymbol(symbol, ruleIndex));
            sort();
        }
    }

    // Removes the given symbol from the set, returns whether it was found or not.
    public boolean remove(String symbol) {
        int pointer = 0;

        for (TerminalSymbol t : data) {
            if (t.getSymbol().equals(symbol)) {
                data.remove(pointer);
                sort();
                return true;
            }

            pointer += 1;
        }

        return false;
    }

    // Returns wether the given value exists within the set or not.
    public boolean contains(String element) {

        for (TerminalSymbol t : data) {
            if (t.getSymbol().equals(element)) {
                return true;
            }
        }

        return false;
    }

    // Executes the given lambda expression for each element in the set.

    // Sorts the set.
    private void sort() {
        data.sort(new Comparator<TerminalSymbol>() {

            public int compare(TerminalSymbol a, TerminalSymbol b) {
                return a.getSymbol().compareTo(b.getSymbol());
            }

        });
    }

    @Override
    public Iterator<TerminalSymbol> iterator() {

        int size = data.size();

        Iterator<TerminalSymbol> t = new Iterator<TerminalSymbol>() {

            @Override
            public boolean hasNext() {
                return pointer < size;
            }

            @Override
            public TerminalSymbol next() {
                return data.get(pointer++);
            }

        };

        return t;
    }

    // Returns a stringified representation of the data.
    public String toString() {
        String stringifiedData = data.toString();

        return "{" + stringifiedData.substring(1, stringifiedData.length() - 1) + "}";
    }



}


// TerminalSymbol class.
class TerminalSymbol {

    private String symbol;
    private int ruleIndex;

    public TerminalSymbol(String symbol, int ruleIndex) {
        this.symbol = symbol;
        this.ruleIndex = ruleIndex;
    }

    // GETTERS +++

    public String getSymbol() {
        return symbol;
    }

    public int getRuleIndex() {
        return ruleIndex;
    }

    // GETTERS ---

    // SETTERS +++

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setRuleIndex(int ruleIndex) {
        this.ruleIndex = ruleIndex;
    }

    // SETTERS ---

    public String toString() {
        return "\"" + symbol + "\" => [" + ruleIndex + "]";
    }

}

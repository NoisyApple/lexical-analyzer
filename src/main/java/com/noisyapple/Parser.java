package com.noisyapple;

public class Parser {

    private Stack<String> symbolStack;
    private Grammar grammar;
    private LexicalAnalyzer lexicalAnalyzer;

    public Parser(Grammar grammar, LexicalAnalyzer lexicalAnalyzer) {
        symbolStack = new Stack<String>();

        this.grammar = grammar;
        this.lexicalAnalyzer = lexicalAnalyzer;
    }

    public void parse(String input) {

        String currentSymbol; // x
        String currentToken; // a

        symbolStack.push(grammar.getStartSymbol()); // Start symbol is added to the stack.

        // currentSymbol be always attached to the value of the stack peek.
        currentSymbol = symbolStack.peek();
        currentToken = lexicalAnalyzer.nextToken();

        // Parsing loop.
        while (!symbolStack.isEmpty()) {

            if (grammar.isNonTerminal(currentSymbol)) {

                int parsingTableValue = grammar.getParsingTableValue(currentSymbol, currentToken);

                if (parsingTableValue != ParsingTable.EMPTY) {

                    String[] rightSide = grammar.getRightSideArrayByIndex(parsingTableValue);

                    symbolStack.pop();

                    for (String s : rightSide) {
                        symbolStack.push(s);
                    }

                    // After for loop currentSymbol should be pointing to stack peek.

                } else {
                    throw new Error("Syntax error found!");
                }

            } else if (grammar.isTerminal(currentSymbol)) {

                if (currentSymbol.equals(currentToken)) {
                    symbolStack.pop(); // currentSymbol is updated.
                    currentToken = nextToken();
                } else {
                    throw new Error("Syntax error found!");
                }

            } else { // currentSymbol is neither terminal nor terminal.
                throw new Error("Syntax error found!");
            }

        }

    }

}

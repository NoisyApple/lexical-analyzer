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

    public void parse() {

        String currentSymbol; // x
        Token currentToken; // a

        symbolStack.push(grammar.getStartSymbol()); // Start symbol is added to the stack.
        System.out.println(grammar.getStartSymbol());


        // currentSymbol be always attached to the value of the stack peek.
        currentSymbol = symbolStack.peek();
        currentToken = lexicalAnalyzer.getNextToken();

        System.out.println(symbolStack);
        System.out.println();

        // Parsing loop.
        while (!symbolStack.isEmpty()) {

            System.out.println(symbolStack);
            System.out.println(currentSymbol);
            System.out
                    .println(currentToken.getLexeme() + " ::: " + currentToken.getClassification());
            System.out.println();



            if (grammar.isNonTerminal(currentSymbol)) {

                System.out.println(currentSymbol + " <=> " + currentToken.getClassification());
                int parsingTableValue = grammar.getParsingTableValue(currentSymbol,
                        currentToken.getClassification());

                if (parsingTableValue != ParsingTable.EMPTY) {

                    String[] rightSide = grammar.getRightSideArrayByIndex(parsingTableValue);

                    symbolStack.pop();

                    for (int i = rightSide.length - 1; i >= 0; i--) {
                        symbolStack.push(rightSide[i]);
                    }

                    currentSymbol = symbolStack.peek();


                } else {
                    throw new Error("Syntax error found!");
                }

            } else if (grammar.isTerminal(currentSymbol)) {

                if (currentSymbol.equals(currentToken.getClassification())) {
                    symbolStack.pop();
                    currentSymbol = symbolStack.peek();
                    currentToken = lexicalAnalyzer.getNextToken();
                } else {
                    throw new Error("Syntax error found!");
                }

            } else { // currentSymbol is neither terminal nor terminal.
                throw new Error("Syntax error found!");
            }

        }

        System.out.println("Empty stack");
        System.out.println(symbolStack);


    }

}

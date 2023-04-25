package com.noisyapple;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

// Class for containing multiple static utility methods.
public class Utils {

    protected static final HashMap<Character, Integer> PRECEDENCE_MAP = new HashMap<Character, Integer>() {
        {
            put('*', 3);
            put('/', 3);
            put('+', 2);
            put('-', 2);
            put('(', 1);
            put(')', 1);
            put('=', 0);
        }
    };

    // Reads the file found in the given path and returns a String with the content.
    public static String fileToString(String path) {
        String contentCopy = "";

        try {
            BufferedReader bR = new BufferedReader(new FileReader(new File(path)));

            String line;

            while ((line = bR.readLine()) != null) {
                contentCopy += line + "\n";
            }

            bR.close();

        } catch (Exception e) {
            System.out.println("File not found at specified path.");
        }

        return contentCopy;
    }

    // Returns the line number based on the given index.
    public static int getLineNumber(String file, int index) {

        int pointer = 0;
        int lineNumber = 1;

        if (index > file.length()) {
            return -1;
        }

        while (pointer <= index) {
            char currentCharacter = file.charAt(pointer);

            if (currentCharacter == '\n')
                lineNumber++;

            pointer++;
        }

        return lineNumber;
    }

    // Returns the contents of a line based on the given line number.
    public static String getLineContent(String file, int lineNumber) {
        int pointer = 0;
        int currentLineNumber = 1;
        String lineContent = "";

        while (pointer <= file.length() && currentLineNumber != lineNumber) {
            char currentCharacter = file.charAt(pointer);

            if (currentCharacter == '\n')
                currentLineNumber++;

            pointer++;
        }

        while (pointer <= file.length() && currentLineNumber == lineNumber) {
            char currentCharacter = file.charAt(pointer);

            if (currentCharacter == '\n')
                currentLineNumber++;

            lineContent += (currentCharacter != '\n') ? currentCharacter : "";

            pointer++;
        }

        return lineContent;
    }

    public static ArrayList<Token> infixToPrefix(ArrayList<Token> infixStatement) {
        Collections.reverse(infixStatement);

        Stack<Token> operators = new Stack<Token>();

        ArrayList<Token> prefix = new ArrayList<Token>();

        for (int i = 0; i < infixStatement.size(); i++) {
            Token token = infixStatement.get(i);

            String tokenClassification = Token.getTypeByAttribute(token.getAttribute());

            if (tokenClassification.equals("IDENTIFIER")
                    || tokenClassification.equals("RESERVED WORD")
                    || tokenClassification.equals("FLOAT")
                    || tokenClassification.equals("INTEGER")) {

                prefix.add(token);
            } else if (token.getLexeme().equals(")")) {
                operators.push(token);
            } else if (token.getLexeme().equals("(")) {
                while (!operators.isEmpty() && !operators.peek().getLexeme().equals(")")) {
                    prefix.add(operators.pop());
                }
                operators.pop(); // Remove the matching ')'
            } else {
                while (!operators.isEmpty() && PRECEDENCE_MAP
                        .get(operators.peek().getLexeme().charAt(0)) >= PRECEDENCE_MAP
                                .get(token.getLexeme().charAt(0))) {
                    prefix.add(operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            prefix.add(operators.pop());
        }

        Collections.reverse(prefix);

        return prefix;

        // return prefix.reverse().toString();
    }

    public static String infixToPrefix(String infix) {

        // Reverse the infix expression
        StringBuilder reverse = new StringBuilder(infix).reverse();

        Stack<Character> operators = new Stack<>();
        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < reverse.length(); i++) {
            char ch = reverse.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isLetterOrDigit(ch) || ch == '.') {
                prefix.append(ch);
            } else if (ch == ')') {

                operators.push(ch);
            } else if (ch == '(') {

                while (!operators.isEmpty() && operators.peek() != ')') {
                    prefix.append(operators.pop());
                }
                operators.pop(); // Remove the matching ')'
            } else {

                while (!operators.isEmpty() && PRECEDENCE_MAP.get(operators.peek()) >= PRECEDENCE_MAP.get(ch)) {
                    prefix.append(operators.pop());
                }
                operators.push(ch);
            }
        }

        while (!operators.isEmpty()) {
            prefix.append(operators.pop());
        }

        return prefix.reverse().toString();
    }

}

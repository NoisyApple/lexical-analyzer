package com.noisyapple.app;

public class App {
    public static void main(String[] args) {

        String fileData = ExternalFileReader.fileToString(args[0]);

        System.out.println(fileData);

    }
}

package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String filepath = args[0];
        FileHandler fileHandler = new FileHandler(filepath);
        fileHandler.run();

    }
}

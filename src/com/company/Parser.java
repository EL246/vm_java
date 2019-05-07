package com.company;

import com.company.command.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Parser {
    private String filename;
    private File file;
    private List<Command> linesToAdd;

    private static List<String> BRANCH_KEYWORDS = Arrays.asList("label", "goto", "if-goto");
    private static List<String> FUNCTION_KEYWORDS = Arrays.asList("function", "call", "return");

    Parser(String filepath) {
        file = new File(filepath);
        linesToAdd = new ArrayList<>();

        String[] f = filepath.split("/");
        int index = f.length - 1;
        this.filename = f[index].replace(".vm", "");
    }

    List<Command> handle() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            parseLine(scanner.nextLine());
        }
        return linesToAdd;
    }

    private void parseLine(String line) {
        line = removeEmptyOrCommentedLines(line);

        if (!line.isEmpty()) {
            System.out.println(line);
            Command command = processLine(line);
            linesToAdd.add(command);
        }
    }

    private String removeEmptyOrCommentedLines(String line) {
        String[] values = line.split("//", 2); // better way to parse this?
        return values[0];
    }

    private Command processLine(String line) {
        if (line.contains("push") || line.contains("pop")) {
            return parsePopOrPush(line);
        }
        if (isArithmeticOperation(line)) {
            return parseArithmetic(line);
        }
        if (isBranchOperation(line)) {
            return parseBranchOperation(line);
        }
        if (isFunctionOperation(line)) {
            return parseFunctionOperation(line);
        }
//        TODO: Should throw an exception instead
        throw new IllegalArgumentException("Parser unable to parse arguments");
    }

    private Command parseFunctionOperation(String line) {
        String[] words = line.split("\\s+");
        String operation = words[0].replaceAll("\\s+", "");
        if (operation.equals("return")) {
            return new FunctionCommand(operation);
        }
        String functionName = words[1].replaceAll("\\s+","");
        String n = words[2].replaceAll("\\s+","");
        int numArgs = Integer.valueOf(n);
        return new FunctionCommand(operation, functionName, numArgs);
    }

    private Command parseBranchOperation(String line) {
        String[] words = line.split("\\s+");
        String operation = words[0].replaceAll("\\s+", "");
        String label = words[1].replaceAll("\\s+", "");
        return new BranchingCommand(operation, label);
    }

    private boolean isBranchOperation(String line) {
        return doesArrayContain(line, BRANCH_KEYWORDS);
    }

    private boolean isFunctionOperation(String line) {
        return doesArrayContain(line, FUNCTION_KEYWORDS);
    }

    private boolean doesArrayContain(String line, List<String> list ) {
        String firstWord = getOperationKeyword(line);
        return list.contains(firstWord);
    }

    private String getOperationKeyword(String line) {
        String[] words = line.split("\\s+");
        return words[0].replaceAll("\\s+", "");
    }

    private boolean isArithmeticOperation(String line) {
        try {
            ArithmeticOperations.valueOf(line.replaceAll("\\s+", ""));
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private Command parseArithmetic(String line) {
        String operation = line.replaceAll("\\s+", "");
        return new ArithmeticCommand(operation);
    }

    private Command parsePopOrPush(String line) {
        String[] args = line.split("\\s+");
        String operation = args[0];
        String location = args[1];
        int var = Integer.valueOf(args[2]);
        return operation.equals("push") ? new PushCommand(location, var, filename) : new PopCommand(location, var, filename);
    }
}

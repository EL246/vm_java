package com.company;

import com.company.command.ArithmeticCommand;
import com.company.command.Command;
import com.company.command.PopCommand;
import com.company.command.PushCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Parser {
    private File file;
    private List<Command> linesToAdd;

    Parser(String filepath) {
        file = new File(filepath);
        linesToAdd = new ArrayList<>();
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
        } else {
            return parseArithmetic(line);
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
        Command command = operation.equals("push") ? new PushCommand(location,var) : new PopCommand(location, var);
        return command;
    }
}

package com.company.command;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    private String operation;
    private ArrayList<String> commands;

    Command(String operation) {
        this.operation = operation;
        this.commands = new ArrayList<>();
    }

    public abstract List<String> getCommands();

    String getOperation() {
        return operation;
    }

    ArrayList<String> getCommandArray() {
        return commands;
    }
}

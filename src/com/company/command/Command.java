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

    void reduceSP() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("M=M-1");
    }

    void incrementSP() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("M=M+1");
    }

    public abstract List<String> getCommands();

    String getOperation() {
        return operation;
    }

    ArrayList<String> getCommandArray() {
        return commands;
    }
}

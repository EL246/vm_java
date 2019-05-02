package com.company.command;

import java.util.List;

public abstract class Command {
    private String operation;
    private int var;

    Command(String operation, int var) {
        this.operation = operation;
        this.var = var;
    }

    public Command(String operation) {
        this.operation = operation;
    }

    public abstract List<String> getCommandString();
}

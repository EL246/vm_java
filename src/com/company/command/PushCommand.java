package com.company.command;

import java.util.List;

public class PushCommand extends Command {
    private String filename;

    public PushCommand(String operation, int var, String filename) {
        super(operation, var);
        this.filename = filename;
    }

    @Override
    public List<String> getCommandString() {
        return null;
    }
}

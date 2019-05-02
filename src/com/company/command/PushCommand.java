package com.company.command;

import java.util.List;

public class PushCommand extends Command {
    public PushCommand(String operation, int var) {
        super(operation, var);
    }

    @Override
    public List<String> getCommandString() {
        return null;
    }
}

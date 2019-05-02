package com.company.command;

import java.util.List;

public class PopCommand extends Command {
    public PopCommand(String operation, int var) {
        super(operation, var);
    }

    @Override
    public List<String> getCommandString() {
        return null;
    }
}

package com.company.command;

import java.util.List;

public class PushCommand extends PoporPushCommand {

    public PushCommand(String operation, int var, String filename) {
        super(operation, var, filename);
    }

    @Override
    public List<String> getCommands() {
        return null;
    }
}

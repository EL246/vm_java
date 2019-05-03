package com.company.command;

import java.util.List;

public class ArithmeticCommand extends Command {
    public ArithmeticCommand(String operation) {
        super(operation);
    }

    @Override
    public List<String> getCommands() {
        return null;
    }
}

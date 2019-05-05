package com.company.command;

import java.util.List;

public class ArithmeticCommand extends Command {
    public ArithmeticCommand(String operation) {
        super(operation);
    }

    @Override
    public List<String> getCommands() {
        ArithmeticOperations operation = ArithmeticOperations.valueOf(getOperation());
//        get arg1 (sp-2)
        return null;
    }
}

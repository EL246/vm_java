package com.company.command;

import java.util.List;

public class FunctionCommand extends Command{
    private String functionName;
    private int nArgs;

    public FunctionCommand(String operation) {
        super(operation);
    }

    public FunctionCommand(String operation, String functionName, int nArgs) {
        super(operation);
        this.functionName = functionName;
        this.nArgs = nArgs;
    }

    @Override
    public List<String> getCommands() {
        return null;
    }
}

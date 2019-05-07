package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class FunctionCommand extends Command {
    private String functionName;
    private int nArgs;
    private String filename;

    public FunctionCommand(String filename, String operation) {
        super(operation);
        this.filename = filename;

        getCommandArray().add("// " + getOperation());
    }

    public FunctionCommand(String filename, String operation, String functionName, int nArgs) {
        super(operation);
        this.functionName = functionName;
        this.nArgs = nArgs;
        this.filename = filename;

//        TODO: refactor this.
        getCommandArray().add("// " + getOperation() + " " + functionName + " " + nArgs);

    }

    @Override
    public List<String> getCommands() {
        switch (getOperation()) {
            case "function":
                createFunctionCommands();
                break;
            case "call":
                createCallCommands();
                break;
            case "return": 
                createReturnCommand();
                break;
        }
        return getCommandArray();
    }

    private void createReturnCommand() {
    }

    private void createCallCommands() {
        
    }

    private void createFunctionCommands() {
//        create label for function
//        filename.function
//        push 0 nArgs times (# of local variables)
        String functionLabel = filename + "." + functionName;
        createLabel(functionLabel);

        ArrayList<String> commands = getCommandArray();
        for (int i = 0; i < nArgs; i++) {
            PushCommand pushCommand = new PushCommand("local", 0, filename);
            commands.addAll(pushCommand.getCommands());
        }
    }
}

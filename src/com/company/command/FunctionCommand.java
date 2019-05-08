package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class FunctionCommand extends Command {
    private static int n = 0;
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
        ArrayList<String> commmands = getCommandArray();
//        FRAME = LCL (R14)
        String frameLocation = "@R14";
        commmands.add("@" + Symbol.LOCAL);
        commmands.add("D=M");
        commmands.add(frameLocation);
        commmands.add("M=D");

//        RET = *(FRAME-5)
        String retLocation = "R15";
        updatePointer(frameLocation, 5, retLocation);

//        *ARG = pop()
        PopCommand pop = new PopCommand("argument",0,filename);
        commmands.addAll(pop.getCommands());

//        SP = ARG+1
        commmands.add("@" + Symbol.ARGUMENT);
        commmands.add("D=M");
        commmands.add("@1");
        commmands.add("D=D+A");
        commmands.add("@" + Symbol.SP);
        commmands.add("M=D");

//        THAT = *(FRAME-1)
        updatePointer(frameLocation, 1, Symbol.THAT.toString());

//        THIS = *(FRAME-2)
        updatePointer(frameLocation, 2, Symbol.THIS.toString());

//        ARG = *(FRAME-3)
        updatePointer(frameLocation, 3, Symbol.ARGUMENT.toString());

//        LCL = *(FRAME-4)
        updatePointer(frameLocation,4,Symbol.LOCAL.toString());

//        goto RET
        commmands.add("@" + retLocation);
        commmands.add("D=M");
//        TODO: will this work? :
        BranchingCommand gotoRet = new BranchingCommand("goto","D");
    }

    private void updatePointer(String frameLocation, int offset, String pointer) {
        ArrayList<String> commmands = getCommandArray();
        getValueOffsetFromFrame(frameLocation, offset);
        commmands.add("@" + pointer);
        commmands.add("M=D");
    }

    private void getValueOffsetFromFrame(String frameLocation, int offset) {
        ArrayList<String> commmands = getCommandArray();
        commmands.add(frameLocation);
        commmands.add("D=M");
        commmands.add("@" + offset);
        commmands.add("D=D-A");
        commmands.add("A=D");
        commmands.add("D=M");
    }

    private void createCallCommands() {
        final String functionLabel = filename + "." + functionName;
//        push return address
        final String returnAddress = functionLabel + "$" + "returnAddr" + "." + n;
        n++;
        pushLabel(returnAddress);
//        push LCL
//            get lcl address
//            push constant lcl-address
        pushSymbol(Symbol.LOCAL);
//        push ARG
        pushSymbol(Symbol.ARGUMENT);
//        push THIS
        pushSymbol(Symbol.THIS);
//        push THAT
        pushSymbol(Symbol.THAT);
//        ARG = SP-n-5
        updateArg();
//        LCL = SP
        updateLCL();
//        goto f
//        TODO: add enums for commands
//        TODO: use function name or filename.functionname?
        BranchingCommand branchingCommand = new BranchingCommand("goto",functionLabel);
        getCommandArray().addAll(branchingCommand.getCommands());
//        (return address)
        createLabel(returnAddress);
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

    private void updateLCL() {
        ArrayList<String> commands = getCommandArray();
        getSPIndex();
        commands.add("@"+Symbol.LOCAL);
        commands.add("M=D");
    }

    private void updateArg() {
        ArrayList<String> commands = getCommandArray();

        getSPIndex();
        commands.add("@5");
        commands.add("D=D-M");
        commands.add("@"+nArgs);
        commands.add("D=D-M");
        commands.add("@"+Symbol.ARGUMENT);
        commands.add("M=D");
    }

    private void pushSymbol(Symbol symbol) {
        getCommandArray().add("@"+ symbol);
        pushReference();
    }

    private void pushReference() {
        getCommandArray().add("D=M");
        setSPAndIncrement();
    }

    private void pushLabel(String label) {
        getCommandArray().add("@"+ label);
        pushReference();
    }

    private void setSPAndIncrement() {
        setSPValue();
        incrementSP();
    }
}

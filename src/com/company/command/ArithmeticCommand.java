package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class ArithmeticCommand extends Command {
    private static int labelID = 0;

    public ArithmeticCommand(String operation) {
        super(operation);
    }

    @Override
    public List<String> getCommands() {
        ArithmeticOperations operation = ArithmeticOperations.valueOf(getOperation());
        getCommandArray().add(operation.toString());
        getArg();
        saveArgInD();
        reduceSP();
        if (operation.hasTwoArgs()) {
            getArg();
        }
        doOperation(operation);
        if (operation.hasTwoArgs()) {
            reduceSP();
        }
        storeValueInSP();
        incrementSP();
//        get arg2 (sp-1)
//            @SP
//            A=M-1
//            D=M
//         reduce SP
//            @SP;M=M-1
//        get arg1 (if needed)
//            @SP
//            A=M-1
//        do operation (?)
//            e.g. D = D-M
//        store in SP
//            @SP
//            A=M
//            M=D
//        increment SP
//            @SP
//            M=M+1
        return getCommandArray();
    }

    private void storeValueInSP() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("A=M");
        commands.add("M=D");
    }

    private void doOperation(ArithmeticOperations operation) {
//        arg1 = M
//        arg2 = D
//        e.g. sub => D = M-D
        if (operation.isComparisonFunction()) {
            handleComparisonOperation(operation);
        } else {
            getCommandArray().add(operation.getOperationString());
        }
    }

    private void handleComparisonOperation(ArithmeticOperations operation) {
        ArrayList<String> commands = getCommandArray();
        String trueLabel = "TRUE." + labelID;
        String endLabel = "END." + labelID;
        commands.add("@" + trueLabel);
        commands.add(operation.getOperationString());
        commands.add("D=0");
        commands.add("@" + endLabel);
        commands.add("(" + trueLabel + ")");
        commands.add("D=1");
        commands.add("(" + endLabel + ")");
        labelID++;
    }

    private void saveArgInD() {
        getCommandArray().add("D=M");
    }

    private void getArg() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("A=M-1");
    }
}

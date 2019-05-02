package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class PopCommand extends Command {
    public PopCommand(String operation, int var) {
        super(operation, var);
    }

    @Override
    public List<String> getCommandString() {
        ArrayList<String> commands = new ArrayList<>();

        String commentedOriginalLine = getOriginalVMLine();
        commands.add(commentedOriginalLine);

        getCommands(commands);

        return commands;
    }

    private String getOriginalVMLine() {
        return "// " + "pop" + this.getOperation() + " " + this.getVar();
    }

    private void getCommands(ArrayList<String> commands) {
//        get values at specified register (e.g. local 17)
        getRegisterValues(commands);

//        store in a temporary location (R13)
        commands.add("@R13");
        commands.add("M=D");

//        get stack value (SP)
        int sp = Config.getRegisterPointersKey("SP");
        commands.add(Integer.toString(sp));
        commands.add("A=M");
        commands.add("D=M");
        commands.add("@R13");
        commands.add("A=M");
        commands.add("M=D");

//        reduce stack pointer value (SP--)
        commands.add(Integer.toString(sp));
        commands.add("M=M-1");
    }

    private void getRegisterValues(ArrayList<String> result) {

//        TODO: check for null, and get key only if operation is of type arg/local/this/that
        int index = Config.getRegisterPointersKey(this.getOperation());

        result.add("@" + index);
        result.add("D=M");
        result.add("@" + getVar());
        result.add("D=D+A");
    }
}

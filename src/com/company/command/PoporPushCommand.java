package com.company.command;

import java.util.ArrayList;

abstract class PoporPushCommand extends Command {
    private String filename;
    private int var;

    PoporPushCommand(String operation, int var, String filename) {
        super(operation);
        this.filename = filename;
        this.var = var;
    }

    String getOriginalVMLine(String action) {
        return "// " + action + " " + this.getOperation() + " " + this.getVar();
    }

    void getRegisterLocation() {
        ArrayList<String> commands = getCommandArray();

//        TODO: check for null, and get key only if operation is of type arg/local/this/that
//        TODO: add final keyword
        final String pointer = Symbol.valueOf(this.getOperation().toUpperCase()).toString();

        commands.add("@" + pointer);
        commands.add("D=M");
        commands.add("@" + getVar());
        commands.add("D=D+A");
    }

    String getFilename() {
        return filename;
    }

    int getVar() {
        return var;
    }

}

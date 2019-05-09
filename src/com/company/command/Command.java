package com.company.command;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    private String operation;
    private ArrayList<String> commands;

    Command(String operation) {
        this.operation = operation;
        this.commands = new ArrayList<>();
    }

    void reduceSP() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("M=M-1");
    }

    void incrementSP() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("M=M+1");
    }

    void getConstantValue(String address){
        ArrayList<String> commands = getCommandArray();
        commands.add("@"+address);
        commands.add("D=A");
    }

    void getSpLastAddedValue() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
//        get last value on stack
        commands.add("A=M-1");
        commands.add("D=M");
    }

    void getSPIndex() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("D=M");
    }

    void createLabel(String labelName) {
        ArrayList<String> commands = getCommandArray();
        commands.add("(" + labelName + ")");
    }

    void setSPValue() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("A=M");
        commands.add("M=D");
    }

    public abstract List<String> getCommands();

    String getOperation() {
        return operation;
    }

    ArrayList<String> getCommandArray() {
        return commands;
    }
}

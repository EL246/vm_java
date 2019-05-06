package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class PushCommand extends PoporPushCommand {

    public PushCommand(String operation, int var, String filename) {
        super(operation, var, filename);
    }

    @Override
    public List<String> getCommands() {
        String commentedOriginalLine = getOriginalVMLine("push");
        getCommandArray().add(commentedOriginalLine);

        createCommands();

        return getCommandArray();
    }

    private void createCommands() {
//        get values at specified register (e.g. local 17)
        String location;
        switch (this.getOperation()) {
//            TODO: turn these values into enum
            case "local":
            case "argument":
            case "this":
            case "that":
                getRegisterLocation();
                getRegisterValue();
                setSPValue();
                incrementSP();
                break;
            case "static":
//                @foo.5
                location = "@" + getFilename() + "." + getVar();
                handleNonRegisterPushCommand(location);
                break;
            case "pointer":
                String target = getVar()==0? "THIS" : "THAT";
                String pointer = Symbol.valueOf(target).toString();
                location = "@" + pointer;
                handleNonRegisterPushCommand(location);
                break;
            case "temp":
                int address = 5 + getVar();
                location = "@" + address;
                handleNonRegisterPushCommand(location);
                break;
            case "constant":
                location = "@" + getVar();
                handleConstantPushCommand(location);
                break;
        }
    }

    private void handleConstantPushCommand(String location) {
        ArrayList<String> commands = getCommandArray();
        commands.add(location);
        commands.add("D=A");
        setSPValue();
        incrementSP();
    }

    private void handleNonRegisterPushCommand(String location) {
        getLocationValue(location);
        setSPValue();
        incrementSP();
    }

    private void getLocationValue(String location) {
        ArrayList<String> commands = getCommandArray();
        commands.add(location);
        commands.add("D=M");
    }

    private void setSPValue() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Symbol.SP);
        commands.add("A=M");
        commands.add("M=D");
    }

    private void getRegisterValue() {
        ArrayList<String> commands = getCommandArray();
        commands.add("A=D");
        commands.add("D=M");
    }
}

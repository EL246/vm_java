package com.company.command;

import com.company.config.Config;

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
                setSPValue();
                incrementSP();
                break;
            case "static":
//                @foo.5
                location = "@" + getFilename() + "." + getVar();
                handleNonRegisterPushCommand(location);
                break;
            case "pointer":
                String target = getVar()==0? "this" : "that";
                int addr = Config.getRegisterPointersKey(target);
                location = "@" + addr;
                handleNonRegisterPushCommand(location);
                break;
            case "temp":
                int address = 5 + getVar();
                location = "@" + address;
                handleNonRegisterPushCommand(location);
                break;
            case "constant":
                location = "@" + getVar();
                handleNonRegisterPushCommand(location);
                break;
        }
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
        int sp = getSp();
        commands.add("@" + Integer.toString(sp));
        commands.add("A=M");
        commands.add("M=D");
    }

    private void incrementSP() {
        ArrayList<String> commands = getCommandArray();
        int sp = getSp();
        commands.add("@" + Integer.toString(sp));
        commands.add("M=M+1");
    }
}

package com.company.command;

import com.company.config.Config;

import java.util.ArrayList;
import java.util.List;

public class PopCommand extends PoporPushCommand {

    public PopCommand(String operation, int var, String filename) {
        super(operation, var, filename);
    }

    @Override
    public List<String> getCommands() {
        String commentedOriginalLine = getOriginalVMLine();
        getCommandArray().add(commentedOriginalLine);

        createCommands();

        return getCommandArray();
    }

    private String getOriginalVMLine() {
        return "// " + "pop " + this.getOperation() + " " + this.getVar();
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
//                store in a temporary location (R13)
                location = "@R13";
                getCommandArray().add(location);
                getCommandArray().add("M=D");
//                get stack value (SP)
                getSpValue();
//              set stack value
                setSPToNewLocation(location);
//              reduce stack pointer value (SP--)
                reduceSP();
                break;
            case "static":
//                @foo.5
                location = "@" + getFilename() + "." + getVar();
                handleNonRegisterCommand(location);
                break;
            case "pointer":
//                TODO: does asm understand @THIS and @THAT, vs @0/@1 (if so, update)
                String target = getVar()==0? "this" : "that";
                location = "@" + target;
                handleNonRegisterCommand(location);
                break;
            case "temp":
                int addr = 5 + getVar();
                location = "@" + addr;
                handleNonRegisterCommand(location);
                break;
        }
    }

    private void handleNonRegisterCommand(String location) {
        ArrayList<String> commands = getCommandArray();
        getSpValue();
        commands.add(location);
        commands.add("M=D");
        reduceSP();
    }

    private void setSPToNewLocation(String location) {
        ArrayList<String> commands = getCommandArray();
        commands.add(location);
        commands.add("A=M");
        commands.add("M=D");
    }

    private void reduceSP() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + Integer.toString(getSp()));
        commands.add("M=M-1");
    }

    private void getSpValue() {
        ArrayList<String> commands = getCommandArray();
        int sp = getSp();
        commands.add("@" + Integer.toString(sp));
        commands.add("A=M");
        commands.add("D=M");
    }

    private int getSp() {
        return Config.getRegisterPointersKey("SP");
    }

    private void getRegisterLocation() {
        ArrayList<String> commands = getCommandArray();

//        TODO: check for null, and get key only if operation is of type arg/local/this/that
        int index = Config.getRegisterPointersKey(this.getOperation());

        commands.add("@" + index);
        commands.add("D=M");
        commands.add("@" + getVar());
        commands.add("D=D+A");

    }
}

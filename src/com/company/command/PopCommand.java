package com.company.command;

import com.company.config.Config;

import java.util.ArrayList;
import java.util.List;

public class PopCommand extends Command {
    private String filename;
    private ArrayList<String> commands;

    public PopCommand(String operation, int var, String filename) {
        super(operation, var);
        this.filename = filename;
        commands = new ArrayList<>();
    }

    @Override
    public List<String> getCommandString() {
        String commentedOriginalLine = getOriginalVMLine();
        commands.add(commentedOriginalLine);

        getCommands(commands);

        System.out.println("Pop command size: " + commands.size());
        return commands;
    }

    private String getOriginalVMLine() {
        return "// " + "pop " + this.getOperation() + " " + this.getVar();
    }

    private ArrayList<String> getCommands(ArrayList<String> commands) {
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
                commands.add(location);
                commands.add("M=D");
//                get stack value (SP)
                getSpValue();
//              set stack value
                setSPToNewLocation(location);
//              reduce stack pointer value (SP--)
                reduceSP();
                break;
            case "static":
//                @foo.5
                location = "@" + filename + "." + getVar();
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


        return commands;
    }

    private void handleNonRegisterCommand(String location) {
        getSpValue();
        commands.add(location);
        commands.add("M=D");
        reduceSP();
    }

    private void setSPToNewLocation(String location) {
        commands.add(location);
        commands.add("A=M");
        commands.add("M=D");
    }

    private void reduceSP() {
        commands.add("@" + Integer.toString(getSp()));
        commands.add("M=M-1");
    }

    private void getSpValue() {
        int sp = getSp();
        commands.add("@" + Integer.toString(sp));
        commands.add("A=M");
        commands.add("D=M");
    }

    private int getSp() {
        return Config.getRegisterPointersKey("SP");
    }

    private void getRegisterLocation() {

//        TODO: check for null, and get key only if operation is of type arg/local/this/that
        int index = Config.getRegisterPointersKey(this.getOperation());

        commands.add("@" + index);
        commands.add("D=M");
        commands.add("@" + getVar());
        commands.add("D=D+A");

    }
}

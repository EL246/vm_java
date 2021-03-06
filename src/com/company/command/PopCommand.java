package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class PopCommand extends PoporPushCommand {

    public PopCommand(String operation, int var, String filename) {
        super(operation, var, filename);
    }

    @Override
    public List<String> getCommands() {
        String commentedOriginalLine = getOriginalVMLine("pop");
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
//                store in a temporary location (R13)
                location = "@R13";
                getCommandArray().add(location);
                getCommandArray().add("M=D");
//                get stack value (SP)
                getSpLastAddedValue();
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
                String target = getVar()==0? "THIS" : "THAT";
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
        getSpLastAddedValue();
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
}

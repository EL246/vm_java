package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class BranchingCommand extends Command {
    private String labelName;

    public BranchingCommand(String operation, String labelName) {
        super(operation);
        this.labelName = labelName;
    }

    @Override
    public List<String> getCommands() {
        getCommandArray().add("//" + getOperation() + " " + labelName);

        switch (getOperation()) {
            case "label":
                createLabel(labelName);
                break;
            case "goto":
                createGoto();
                break;
            case "if-goto":
                createIfGoto();
                break;
        }
        return getCommandArray();
    }

    private void createIfGoto() {
        ArrayList<String> commands = getCommandArray();
        getSpValue();
        reduceSP();
        commands.add("@"+labelName);
        commands.add("D;JNE");
    }

    private void createGoto() {
        ArrayList<String> commands = getCommandArray();
        commands.add("@" + labelName);
        commands.add("0;JMP");
    }
}

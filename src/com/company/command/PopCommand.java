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

        String commentedOriginalLine = "// " + "pop" + this.getOperation() + " " + this.getVar();
        commands.add(commentedOriginalLine);

        return commands;
    }
}

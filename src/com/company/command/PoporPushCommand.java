package com.company.command;

import java.util.ArrayList;

abstract class PoporPushCommand extends Command {
    private String filename;
    private ArrayList<String> commands;

    PoporPushCommand(String operation, int var, String filename) {
        super(operation, var);
        this.filename = filename;
        this.commands = new ArrayList<>();
    }

    ArrayList<String> getCommandArray() {
        return commands;
    }

    String getFilename() {
        return filename;
    }
}

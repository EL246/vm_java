package com.company.command;

import java.util.ArrayList;
import java.util.List;

public class InitCommand extends Command {
    public InitCommand() {
        super("INIT");
    }

    @Override
    public List<String> getCommands() {
        System.out.println("Adding Init Commands");
        ArrayList<String> commands = getCommandArray();
//        SP=256
        commands.add("// Sys Init");
        getConstantValue(String.valueOf(256));
        commands.add("@" + Symbol.SP);
        commands.add("M=D");
//        call Sys.init
//        TODO: refactor this:
        FunctionCommand callSysInit = new FunctionCommand("Sys","call","Sys.init",0);
        commands.addAll(callSysInit.getCommands());

        return getCommandArray();
    }
}

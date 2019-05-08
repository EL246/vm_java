package com.company;

import com.company.command.Command;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

class CodeWriter {
    private String filePath;
    private List<Command> linesToAdd;

    CodeWriter(String filePath, List<Command> linesToAdd) {
        this.filePath = filePath;
        this.linesToAdd = linesToAdd;
    }

    void handle() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true));
        for (Command command : linesToAdd) {
            writeToFile(command, bufferedWriter);
        }

//        TODO: need a finally{} here?
        bufferedWriter.close();
    }

    //    TODO: better to do this for each line parsed rather than entire array of lines?
    private void writeToFile(Command commandToAdd, BufferedWriter bufferedWriter) throws IOException {
        List<String> newLines = getCommandStrings(commandToAdd);
        for (String line : newLines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
    }

    private List<String> getCommandStrings(Command commandToAdd) {
        return commandToAdd.getCommands();
    }
}

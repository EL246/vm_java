package com.company;

import com.company.command.Command;

import java.io.IOException;
import java.util.List;

class FileHandler {
    private String filePath;

    FileHandler(String filePath) {
        this.filePath = filePath;
    }

    void run() throws IOException {
        Parser parser = new Parser(filePath);
        List<Command> linesToAdd = parser.handle();

        String newFilePath = filePath.replace(".asm", ".hack");
        CodeWriter codeWriter = new CodeWriter(newFilePath, linesToAdd);
        codeWriter.handle();
    }
}

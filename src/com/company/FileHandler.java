package com.company;

import com.company.command.Command;

import java.io.File;
import java.io.IOException;
import java.util.List;

class FileHandler {
    private String filePath;

    FileHandler(String filePath) {
        this.filePath = filePath;
    }

    void run() throws IOException {
        getVMFiles(filePath);
//            check for files
//            process file if .asm
//            if directory, process directory
    }

    private void getVMFiles(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile() && f.getName().endsWith(".vm")) {
                        handleFile(f);
                        System.out.println("Found file: " + f.getName());
                    } else if (f.isDirectory()) {
                        getVMFiles(f.getPath());
                    }
                }
            }
        }

    }

    private void handleFile(File file) throws IOException {
        Parser parser = new Parser(file);
        List<Command> linesToAdd = parser.handle();

        String newFilePath = file.getPath().replace(".vm", ".asm");
        System.out.println("Codewriter filepath: " + newFilePath);
        CodeWriter codeWriter = new CodeWriter(newFilePath, linesToAdd);
        codeWriter.handle();
    }
}

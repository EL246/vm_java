package com.company;

import com.company.command.Command;

import java.io.File;
import java.io.IOException;
import java.util.List;

class FileHandler {
    private String filePath;
    private String asmFilePath;

    FileHandler(String filePath) {
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.isDirectory()) {
            asmFilePath = filePath.replace(".vm",".asm");
//            throw new IllegalArgumentException("File specified instead of directory");
        } else {
            String[] directories = filePath.split("/");
            asmFilePath = directories[directories.length-1] + ".asm";
            asmFilePath = filePath + "/" + asmFilePath;
        }
    }

    void run() throws IOException {
        //        create a new empty .asm file for CodeWriter to write to
        File asmFile = new File(asmFilePath);
//        TODO: include exception handling
        if (asmFile.exists()) {
            asmFile.delete();
        }
        asmFile.createNewFile();

        getVMFiles(filePath);
    }

/*          check for files
            process file if .asm
            if directory, process directory
*/
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

        System.out.println("Codewriter filepath: " + asmFilePath);
        CodeWriter codeWriter = new CodeWriter(asmFilePath, linesToAdd);
        codeWriter.handle();
    }
}

package com.company.command;

public enum Symbol {
    SP("SP"),
    LOCAL("LCL"),
    ARGUMENT("ARG"),
    THIS("THIS"),
    THAT("THAT");

    private String name;

    Symbol(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

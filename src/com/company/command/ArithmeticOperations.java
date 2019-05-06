package com.company.command;

//TODO: better way to represent eq, gt, lt?
public enum ArithmeticOperations {
    add("add", true, "D=M+D", false),
    sub("sub", true, "D=M-D", false),
    neg("neg", false, "D=-D", false),
    eq("eq", true, "D; JEQ", true),
    gt("gt", true, "D; JGT", true),
    lt("lt", true, "D; JLT", true),
    and("and", true, "D=M&D", false),
    or("or", true, "D=M|D", false),
    not("not", false, "D=!D", false);

    private boolean hasTwoArgs;
    private String operationString;
    private boolean comparisonFunction;
    private String name;

    ArithmeticOperations(String name, boolean hasTwoArgs, String operationString, boolean comparisonFunction) {
        this.hasTwoArgs = hasTwoArgs;
        this.operationString = operationString;
        this.comparisonFunction = comparisonFunction;
        this.name = name;
    }

    public boolean hasTwoArgs() {
        return hasTwoArgs;
    }

    public String getOperationString() {
        return operationString;
    }

    public boolean isComparisonFunction() {
        return comparisonFunction;
    }

    @Override
    public String toString() {
        return "// " + name;
    }
}

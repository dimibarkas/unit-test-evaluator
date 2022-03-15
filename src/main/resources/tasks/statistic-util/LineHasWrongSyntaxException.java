package com.test.app;

public class LineHasWrongSyntaxException extends Exception {
    private int lineNumber;

    public LineHasWrongSyntaxException(int lineNumber) {
        super("There is a syntax error in line: " + lineNumber);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}

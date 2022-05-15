package com.cqut.atao.compile;

public class errorWord {
    private int line;
    private String word;

    public errorWord(int line, String word) {
        this.line = line;
        this.word = word;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "line "+line+" , word "+word+" ERROR";
    }
}

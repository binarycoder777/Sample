package com.cqut.atao.compile;

public class wordlist {

    private Integer line;
    private String key;
    private Integer value;

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public wordlist(Integer line, String key, Integer value){
        this.key = key;
        this.value = value;
        this.line = line;

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String toString(){
        return "(" + line +" , " + key +" , " +value+")";
    }
}

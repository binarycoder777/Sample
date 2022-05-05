package com.cqut.atao.token;

import lombok.Data;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Token.java
 * @Description 种别码
 * @createTime 2022年04月18日 22:32:00
 */
@Data
public class Token implements Serializable {

    // token值
    private StringBuilder val;

    // token对应的码
    private Integer code;

    // token类别
    private String type;

    // 行号
    private Integer line;

    public Token() {
    }

    public Token(Integer line) {
        this.val = new StringBuilder();
        this.line = line;
    }

    public void addChar(char c){
        this.val.append(c);
    }

    public boolean isEmpty(){
        return val.length() == 0;
    }

    @Override
    public String toString() {
        return "Token{" +
                "val=" + val +
                ", code=" + code +
                ", type='" + type + '\'' +
                ", line=" + line +
                '}';
    }
}

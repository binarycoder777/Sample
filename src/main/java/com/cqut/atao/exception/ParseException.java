package com.cqut.atao.exception;

import com.cqut.atao.token.Token;

import java.io.IOException;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ParseException.java
 * @Description 解析异常
 * @createTime 2022年04月19日 11:32:00
 */
public class ParseException extends RuntimeException{
    public ParseException() {

    }

    // 构造方法
    public ParseException(IOException e) {
        super(e);
    }
    // 构造方法
    public ParseException(String msg) {
        super(msg);
    }
    // 构造方法
    public ParseException(Token t) {
        this("", t);
    }
    // 构造方法
    public ParseException(String msg, Token t) {
        super("syntax error around " + location(t) + ". " + msg);
    }
    // 定位错误
    private static String location(Token t) {
        return "\"" + t.getVal() + "\" at line " + t.getLine();
    }
}

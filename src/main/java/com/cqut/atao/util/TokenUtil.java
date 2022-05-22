package com.cqut.atao.util;

import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TokenUtil.java
 * @Description TokenUtil
 * @createTime 2022年05月15日 10:00:00
 */
public class TokenUtil {

    public static String displayToken(List<Token> tokens){
        String s = "";
        for (Token token: tokens){
            s += token.toString() +"\n";
        }
        return s;
    }

    public static String displayException(List<Exception> exceptions){
        String s = "";
        for (Exception exception: exceptions){
            s += exception.toString() +"\n";
        }
        return s;
    }

}

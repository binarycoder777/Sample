package com.cqut.atao.syntax;

import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Parser.java
 * @Description TODO
 * @createTime 2022年05月01日 13:49:00
 */
public class Parser {

    private TokenList<Token> tokens;

    private List<Exception> exceptions;

    public void syataxAnalysis(){
        Token token = tokens.getNextToken();
        while (token != null && !"main".equals(token.getVal())){
            if ("const".equals(token.getVal())){
                //
            }else if (judgeType(token)){
                //
            }else {
                // error
            }
        }
        token = tokens.getNextToken();
        if (!"(".equals(token.getVal())) {
            // error
        }
        if (!"(".equals(token.getVal())) {
            // error
        }
        // 处理复合语句
        token = tokens.getNextToken();
        while (token != null && judgeType(token)) {
            // 处理函数定义
            token = tokens.getNextToken();
        }
    }

    // 判断token是否是int、char、float类型
    public boolean judgeType(Token token){
        return "int".equals(token.getVal()) || "char".equals(token.getVal()) || "float".equals(token.getVal()) || "void".equals(token.getVal());
    }

}

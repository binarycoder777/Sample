package com.cqut.atao.syntax;

import com.cqut.atao.token.Token;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Parser.java
 * @Description TODO
 * @createTime 2022年05月01日 13:49:00
 */
public class Parser {

    private TokenList<Token> tokens;

    public void syataxAnalysis(){
        while (!tokens.isEnd()){
            Token token = tokens.getNextToken();

        }
    }

}

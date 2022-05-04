package com.cqut.atao.syntax.strategy;

import com.cqut.atao.exception.MyException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MainStrategy.java
 * @Description 主入口
 * @createTime 2022年05月01日 14:41:00
 */
public class MainStrategy implements MyStrategy{

    @Override
    public void recognition(TokenList<Token> tokens, List<MyException> exceptions) {
        Token token = tokens.getNextToken();
        if ("{".equals(token.getVal())){
            while (!"}".equals(token.getVal())){
                token = tokens.getNextToken();
                if ("const".equals(token.getVal())){

                }else if (judgeType(token)){

                }else if ("for".equals(token.getVal())){

                }else if ("if".equals(token.getVal())){

                }else if ("while".equals(token.getVal())){

                }else if ("do".equals(token.getVal())){

                }else if ("{".equals(token.getVal())){

                }
            }
        }
    }

    // 判断token是否是int、char、float类型
    public boolean judgeType(Token token){
        return "int".equals(token.getVal()) || "char".equals(token.getVal()) || "float".equals(token.getVal()) || "void".equals(token.getVal());
    }


}

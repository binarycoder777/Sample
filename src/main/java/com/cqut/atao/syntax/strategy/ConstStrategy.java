package com.cqut.atao.syntax.strategy;

import com.cqut.atao.exception.MyException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ConstStrategy.java
 * @Description 声明
 * @createTime 2022年05月01日 14:40:00
 */
public class ConstStrategy implements MyStrategy{

    @Override
    public void recognition(TokenList<Token> tokens, List<MyException> exceptions) {

    }
}

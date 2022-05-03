package com.cqut.atao.syntax.strategy;

import com.cqut.atao.exception.MyException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MyStrategy.java
 * @Description TODO
 * @createTime 2022年05月01日 14:39:00
 */
public interface MyStrategy {

    void recognition(TokenList<Token> tokens,List<MyException> exceptions);

}

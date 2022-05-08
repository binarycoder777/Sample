package com.cqut.atao.syntax.strategy.expression;

import com.cqut.atao.exception.MyException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Expression.java
 * @Description 表达式接口
 * @createTime 2022年05月02日 14:09:00
 */
public interface Expression {
    void recognition(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions);
}

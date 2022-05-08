package com.cqut.atao.syntax.strategy.expression;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ExpressionStatement.java
 * @Description TODO
 * @createTime 2022年05月08日 08:36:00
 */
public class ExpressionStatement implements Expression{

    @Override
    public void recognition(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {

    }

}

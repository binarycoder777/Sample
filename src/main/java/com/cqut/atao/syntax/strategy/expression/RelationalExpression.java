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
 * @ClassName RelationalExpression.java
 * @Description 关系表达式
 * @createTime 2022年05月03日 16:29:00
 */
public class RelationalExpression implements Expression{

    private ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        A(tree,tokens,exceptions);
    }

    private void A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("关系表达式"));
        Token token = tokens.getCurToken();
        if (token != null){
            tree.addChild(new TreeNode("算术表达式"));
            arithmeticExpression.recognition(tree,tokens,exceptions);
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && (">".equals(token.getType()) || "<".equals(token.getType())|| ">=".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType()))){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
            tree.addChild(new TreeNode("算术表达式"));
            arithmeticExpression.recognition(tree,tokens,exceptions);
            tree.traceBack();
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }
}

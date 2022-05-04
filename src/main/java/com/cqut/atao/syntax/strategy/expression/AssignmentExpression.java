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
 * @ClassName AssignmentExpression.java
 * @Description TODO
 * @createTime 2022年05月03日 17:43:00
 */
public class AssignmentExpression implements Expression{

    private ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        A(tree,tokens,exceptions);
    }

    /**
     * TOOD
     * 补充：表达式这里需要三选一，只完成了算术表达式
     * @param tree
     * @param tokens
     * @param exceptions
     */
    private void A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("赋值表达式"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token!=null&&"=".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                arithmeticExpression.recognition(tree,tokens,exceptions);
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }
}

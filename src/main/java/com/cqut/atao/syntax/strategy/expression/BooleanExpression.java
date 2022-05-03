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
 * @ClassName BooleanExpression.java
 * @Description TODO
 * @createTime 2022年05月03日 17:15:00
 */
public class BooleanExpression implements Expression{

    private RelationalExpression relationalExpression = new RelationalExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        A(tree,tokens,exceptions);
    }

    private void A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔表达式"));
        B(tree,tokens,exceptions);
        B1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void B1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B1"));
        Token token = tokens.getCurToken();
        if (token!=null && "||".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B(tree,tokens,exceptions);
            B1(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B"));
        C(tree,tokens,exceptions);
        B2(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void B2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B2"));
        Token token = tokens.getCurToken();
        if (token!=null && "&&".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B(tree,tokens,exceptions);
            B2(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    /**
     * TOOD
     * 补充：此处还应该加上算术表达式
     * @param tree
     * @param tokens
     * @param exceptions
     */
    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C"));
        Token token = tokens.getCurToken();
        if (token!=null && "!".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            A(tree,tokens,exceptions);
        }else if (token != null){
            tree.addChild(new TreeNode("关系表达式"));
            relationalExpression.recognition(tree,tokens,exceptions);
            tree.traceBack();
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }
}

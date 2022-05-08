package com.cqut.atao.syntax.strategy.expression;

import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;
import org.apache.commons.lang.SerializationUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName BooleanExpression.java
 * @Description 布尔表达式
 * @createTime 2022年05月03日 17:15:00
 */
public class BooleanExpression implements Expression{

    private MyTree t;

    private TokenList<Token> k;

    private List<Exception> e;

    private RelationalExpression relationalExpression = new RelationalExpression();

    private ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        A(tree,tokens,exceptions);
    }

    private void pass(){
        return;
    }

    private void A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔表达式"));
        B(tree,tokens,exceptions);
        A1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void A1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("A1"));
        Token token = tokens.getCurToken();
        if (token!=null && "||".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            A(tree,tokens,exceptions);
        }else if (token != null && ("&&".equals(token.getType()))){
            pass();
        } else if (token != null){
            exceptions.add(new ParseException("布尔表达式：Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔项"));
        C(tree,tokens,exceptions);
        B1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void B1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B1"));
        Token token = tokens.getCurToken();
        if (token!=null && "&&".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B(tree,tokens,exceptions);
        }else if(token != null && "||".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("布尔表达式: Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔因子"));
        Token token = tokens.getCurToken();
        if (token!=null && "!".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            A(tree,tokens,exceptions);
        }else if (token != null && ("(".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType())||"字符".equals(token.getType())||"标识符".equals(token.getType()))){
            arithmeticExpression.recognition(tree, tokens, exceptions);
            C1(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("布尔表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void C1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C1"));
        Token token = tokens.getCurToken();
        if (token != null && (">".equals(token.getType()) || "<".equals(token.getType())|| ">=".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            arithmeticExpression.recognition(tree, tokens, exceptions);
        }else if (token != null && ("&&".equals(token.getType()) || "||".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("布尔表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

}

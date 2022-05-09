package com.cqut.atao.syntax.strategy.expression;


import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;
import org.apache.commons.lang.SerializationUtils;

import java.util.*;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ArithmeticExpression.java
 * @Description 算术表达式（存在循环依赖问题）
 * @createTime 2022年05月02日 13:39:00
 */
public class ArithmeticExpression implements Expression{


    public ArithmeticExpression() {

    }


    @Override
    public void recognition(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "标识符".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType())|| "字符".equals(token.getType()))){
            S(tree,tokens,exceptions);
        }else {
            pass();
        }
    }

    void S(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions){
        tree.addChild(new TreeNode("算术表达式"));
        A(tree,tokens,exceptions);
        S1(tree,tokens,exceptions);
        tree.traceBack();
    }


    private void A(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("项"));
        B(tree,tokens,exceptions);
        A1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void S1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S1"));
        Token token = tokens.getCurToken();
        if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            S(tree,tokens,exceptions);
        }else if (token != null && ")".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void pass(){
        return;
    }

    private void A1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("A1"));
        Token token = tokens.getCurToken();
        if (token!=null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            A(tree,tokens,exceptions);
        }else if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType()) || ")".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void B(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("因子"));
        Token token = tokens.getCurToken();
        if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else if (token != null && "标识符".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            B1(tree,tokens, exceptions);
        }else if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            S(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            }else if (token != null){
                exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void B1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B1"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();

            I(tree,tokens,exceptions);

            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            }else if (token != null){
                exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null && ("*".equals(token.getType())||"/".equals(token.getType())||"%".equals(token.getType()) || "+".equals(token.getType()) || "-".equals(token.getType()) || ")".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void I(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("实参列表"));
        Token token = tokens.getCurToken();
        if (token != null){
            J(tree,tokens,exceptions);
        }else if (token != null && ")".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("实参"));
        K(tree,tokens,exceptions);
        J1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void J1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            J(tree,tokens,exceptions);
        }else if (token!=null && ")".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("算术表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void K(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K"));
        // TOOD 待定
        tree.traceBack();
    }



}

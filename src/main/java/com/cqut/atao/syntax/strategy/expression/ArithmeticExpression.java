package com.cqut.atao.syntax.strategy.expression;


import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.configuration.LLReader;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ArithmeticExpression.java
 * @Description 算术表达式
 * @createTime 2022年05月02日 13:39:00
 */
public class ArithmeticExpression implements Expression{

    // first集
    private Map<String,Set<String>> first;

    // follow集
    private Map<String,Set<String>> follow;

    public ArithmeticExpression() {
        load();
    }

    // 加载数据到map、first、follow集合中
    private void load(){
        List<Map<String, Set<String>>> maps = LLReader.readFirstAndFollow("/Users/weitao/Desktop/面试/项目/compler/src/main/java/com/cqut/atao/syntax/strategy/expression/ArithmeticExpression.txt");
        first = maps.get(0);
        follow = maps.get(1);
    }

    @Override
    public void recognition(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        S(tree,tokens,exceptions);
    }

    void S(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions){
        tree.addChild(new TreeNode("S"));
        A(tree,tokens,exceptions);
        S1(tree,tokens,exceptions);
        tree.traceBack();
    }


    private void A(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("A"));
        B(tree,tokens,exceptions);
        A1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void S1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S1"));
        Token token = tokens.getCurToken();
        if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            A(tree,tokens,exceptions);
            S1(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void A1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("A1"));
        Token token = tokens.getCurToken();
        if (token!=null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B(tree,tokens,exceptions);
            A1(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void B(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B"));
        Token token = tokens.getCurToken();
        if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else if (token != null && "标识符".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B1(tree,tokens, exceptions);
        }else if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B2(tree,tokens,exceptions);
            A1(tree,tokens,exceptions);
            S1(tree,tokens,exceptions);

            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void B1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B1"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            I(tree,tokens,exceptions);

            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void I(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("I"));
        J(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void J(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J"));
        K(tree,tokens,exceptions);
        J1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void J1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            K(tree,tokens,exceptions);
            J1(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void K(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        // 待定
        tree.addChild(new TreeNode("K"));
    }

    private void B2(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B2"));
        Token token = tokens.getCurToken();

        if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()))) {
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();

            B2(tree,tokens,exceptions);
            A1(tree,tokens,exceptions);
            S1(tree,tokens,exceptions);

            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            B1(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

}

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


    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C"));
        Token token = tokens.getCurToken();
        if (token!=null && "!".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            A(tree,tokens,exceptions);
        }else if (token != null){
            // 算术表达式和关系表达式
            MyTree t1 = (MyTree) SerializationUtils.clone(tree);
            MyTree t2 = (MyTree) SerializationUtils.clone(tree);
            TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
            TokenList<Token> k2 = (TokenList<Token>) SerializationUtils.clone(tokens);
            List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
            List<Exception> e2 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
            relationalExpression.recognition(t1,k1,e1);
            arithmeticExpression.recognition(t2,k2,e2);
            if (e1.size() <= e2.size() && k1.getIndex() > k2.getIndex()){
                t = t1;
                e = e1;
                k = k1;
            }else{
                t = t2;
                e = e2;
                k = k2;
            }
            tree.setRoot(t.getRoot());
            tree.setCurNode(t.getCurNode());

            exceptions.clear();
            exceptions.addAll(e);

            tokens.setIndex(k.getIndex());
            tokens.setTokens(k.getTokens());
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }
}

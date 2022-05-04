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
 * @ClassName AssignmentExpression.java
 * @Description 算术表达式
 * @createTime 2022年05月03日 17:43:00
 */
public class AssignmentExpression implements Expression{

    private MyTree t;

    private TokenList<Token> k;

    private List<Exception> e;


    private ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    private BooleanExpression booleanExpression = new BooleanExpression();

    private RelationalExpression relationalExpression = new RelationalExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        A(tree,tokens,exceptions);
    }

    /**
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
            // 表达式
            if (token!=null&&"=".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                MyTree t1 = (MyTree) SerializationUtils.clone(tree);
                MyTree t3 = (MyTree) SerializationUtils.clone(tree);
                MyTree t4 = (MyTree) SerializationUtils.clone(tree);
                TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
                TokenList<Token> k3 = (TokenList<Token>) SerializationUtils.clone(tokens);
                TokenList<Token> k4 = (TokenList<Token>) SerializationUtils.clone(tokens);
                List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
                List<Exception> e3 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
                List<Exception> e4 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
                arithmeticExpression.recognition(t1,k1,e1);
                booleanExpression.recognition(t3,k3,e3);
                relationalExpression.recognition(t4,k4,e4);
                if (k1.isEnd() && e1.size() <= e3.size() && e1.size() <= e4.size()){
                    t = t1;
                    e = e1;
                    k = k1;
                }else if (k3.isEnd() && e3.size() <= e1.size() && e3.size() <= e4.size()){
                    t = t3;
                    e = e3;
                    k = k3;
                }else {
                    t = t4;
                    e = e4;
                    k = k4;
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
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }
}

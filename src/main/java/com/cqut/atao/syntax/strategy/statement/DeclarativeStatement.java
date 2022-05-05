package com.cqut.atao.syntax.strategy.statement;

import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.Expression;
import com.cqut.atao.syntax.strategy.expression.ExpressionClient;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;
import org.apache.commons.lang.SerializationUtils;

import javax.swing.table.TableRowSorter;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName DeclarativeStatement.java
 * @Description TODO
 * @createTime 2022年05月05日 10:47:00
 */
public class DeclarativeStatement implements Expression {

    private MyTree t;

    private TokenList<Token> k;

    private List<Exception> e;

    ExpressionClient expressionClient = new ExpressionClient();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("声明语句"));
        S(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("表达式"));
        MyTree t1 = (MyTree) SerializationUtils.clone(tree);
        MyTree t2 = (MyTree) SerializationUtils.clone(tree);
        MyTree t3 = (MyTree) SerializationUtils.clone(tree);
        TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
        TokenList<Token> k2 = (TokenList<Token>) SerializationUtils.clone(tokens);
        TokenList<Token> k3 = (TokenList<Token>) SerializationUtils.clone(tokens);
        List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        List<Exception> e2 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        List<Exception> e3 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        B(t1,k1,e1);
        C(t2,k2,e2);
        D(t3,k3,e3);
        if (k1.getIndex() > k2.getIndex() && k1.getIndex() > k3.getIndex() && e1.size() <= e2.size() && e1.size() <= e3.size() ){
            t = t1;
            e = e1;
            k = k1;
        }else if (k2.getIndex() > k1.getIndex() && k2.getIndex() > k3.getIndex() && e2.size() <= e1.size() && e2.size() <= e3.size()){
            t = t2;
            e = e2;
            k = k2;
        }else {
            t = t3;
            e = e3;
            k = k3;
        }
        tree.setRoot(t.getRoot());
        tree.setCurNode(t.getCurNode());

        exceptions.clear();
        exceptions.addAll(e);

        tokens.setIndex(k.getIndex());
        tokens.setTokens(k.getTokens());

        tree.traceBack();
    }

    public void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                F(tree,tokens,exceptions);
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("F"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "=".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && ("字符".equals(token.getType()) || "实数".equals(token.getType()) || "字符串".equals(token.getType()) || "整数".equals(token.getType()))){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    F1(tree,tokens,exceptions);
                }else {
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("F1"));
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else if (token != null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && "=".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    token = tokens.getCurToken();
                    if (token != null && ("字符".equals(token.getType()) || "实数".equals(token.getType()) || "字符串".equals(token.getType()) || "整数".equals(token.getType()))){
                        tree.addChild(new TreeNode(token.getType()));
                        tokens.match();
                        tree.traceBack();
                        F2(tree,tokens,exceptions);
                    }else {
                        exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                    }
                }else {
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
            tree.traceBack();
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("F2"));
        Token token = tokens.getCurToken();
        if (token!=null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else if (token != null){
            F1(tree,tokens,exceptions);
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void D(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("D"));
        Token token = tokens.getCurToken();
        if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            J(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J"));
        K(tree,tokens,exceptions);
        J1(tree, tokens, exceptions);
        tree.traceBack();
    }

    private void J1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J1"));
        Token token = tokens.getCurToken();
        if (token!=null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else if (token!=null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            K(tree,tokens,exceptions);
            J2(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J2"));
        Token token = tokens.getCurToken();
        if (token!=null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else if (token != null){
            J1(tree,tokens,exceptions);
        } else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void K(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K"));
        Token token = tokens.getCurToken();
        if (token!=null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            K1(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    /**
     * @param tree
     * @param tokens
     * @param exceptions
     */
    private void K1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K1"));
        Token token = tokens.getCurToken();
        if (token!=null && "=".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
//            token = tokens.getCurToken();
            // TOOD 处理表达式
//            MyTree t1 = (MyTree) SerializationUtils.clone(tree);
//            TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
//            List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
            expressionClient.recognition(tree,tokens,exceptions);
//            if (k1.getIndex() > tokens.getIndex() && e1.size() == exceptions.size()){
//                t = t1;
//                e = e1;
//                k = k1;
//                tree.setRoot(t.getRoot());
//                tree.setCurNode(t.getCurNode());
//
//                exceptions.clear();
//                exceptions.addAll(e);
//
//                tokens.setIndex(k.getIndex());
//                tokens.setTokens(k.getTokens());
//                tree.traceBack();
//            }
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B"));
        Token token = tokens.getCurToken();
        if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && "(".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    O(tree,tokens,exceptions);
                    token = tokens.getCurToken();
                    if (token != null && ")".equals(token.getType())){
                        tree.addChild(new TreeNode(token.getType()));
                        tokens.match();
                        tree.traceBack();
                    }else {
                        exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                    }
                }else {
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void O(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("O"));
        Token token = tokens.getCurToken();
        if (token!=null){
            P(tree,tokens,exceptions);
        }
        tree.traceBack();
    }

    private void P(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("P"));
        Token token = tokens.getCurToken();
        if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            P1(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void P1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("P1"));
        Token token = tokens.getCurToken();
        if (token!=null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                P1(tree,tokens,exceptions);
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


}

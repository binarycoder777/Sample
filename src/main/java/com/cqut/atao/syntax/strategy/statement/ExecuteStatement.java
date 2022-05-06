package com.cqut.atao.syntax.strategy.statement;

import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.AssignmentExpression;
import com.cqut.atao.syntax.strategy.expression.Expression;
import com.cqut.atao.syntax.strategy.expression.ExpressionClient;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;
import org.apache.commons.lang.SerializationUtils;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ExecuteStatement.java
 * @Description TODO
 * @createTime 2022年05月06日 08:59:00
 */
public class ExecuteStatement implements Expression {

    private MyTree t;

    private TokenList<Token> k;

    private List<Exception> e;

    DeclarativeStatement declarativeStatement = new DeclarativeStatement();

    ExpressionClient expressionClient = new ExpressionClient();

    AssignmentExpression assignmentExpression = new AssignmentExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("执行语句"));
        Z(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void Z(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("Z"));
        MyTree t1 = (MyTree) SerializationUtils.clone(tree);
        MyTree t2 = (MyTree) SerializationUtils.clone(tree);
        MyTree t3 = (MyTree) SerializationUtils.clone(tree);
        TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
        TokenList<Token> k2 = (TokenList<Token>) SerializationUtils.clone(tokens);
        TokenList<Token> k3 = (TokenList<Token>) SerializationUtils.clone(tokens);
        List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        List<Exception> e2 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        List<Exception> e3 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        A(t1,k1,e1);
        B(t2,k2,e2);
        C(t3,k3,e3);
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

    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C"));
        K(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void K(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K"));
        L(tree,tokens,exceptions);
        K1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void L(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        MyTree t1 = (MyTree) SerializationUtils.clone(tree);
        MyTree t2 = (MyTree) SerializationUtils.clone(tree);
        TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
        TokenList<Token> k2 = (TokenList<Token>) SerializationUtils.clone(tokens);
        List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        List<Exception> e2 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        declarativeStatement.recognition(t1,k1,e1);
//        Z(t2,k2,e2);
        if (k1.getIndex() > k2.getIndex() && e1.size() <= e2.size() ){
            t = t1;
            e = e1;
            k = k1;
        }else {
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
    }

    private void K1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K1"));
        MyTree t1 = (MyTree) SerializationUtils.clone(tree);
        TokenList<Token> k1 = (TokenList<Token>) SerializationUtils.clone(tokens);
        List<Exception> e1 = (List<Exception>) JSONObject.parseObject(JSONObject.toJSONBytes(exceptions), List.class);
        declarativeStatement.recognition(t1,k1,e1);
        if (k1.getIndex() > tokens.getIndex() && e1.size() == exceptions.size()){
            t = t1;
            e = e1;
            k = k1;
            tree.setRoot(t.getRoot());
            tree.setCurNode(t.getCurNode());

            exceptions.clear();
            exceptions.addAll(e);

            tokens.setIndex(k.getIndex());
            tokens.setTokens(k.getTokens());
            K(tree,tokens,exceptions);
        }
        tree.traceBack();
    }



    private void B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B"));
        Token token = tokens.getCurToken();
        if (token!=null && "if".equals(token.getType())){
            F(tree,tokens,exceptions);
        }else if (token!=null && "for".equals(token.getType())){
            G(tree,tokens,exceptions);
        }else if (token!=null && "while".equals(token.getType())){
            H(tree,tokens,exceptions);
        }else if (token!=null && "do".equals(token.getType())){
            I(tree,tokens,exceptions);
        }else if (token!=null && "return".equals(token.getType())){
            J(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J"));
        Token token = tokens.getCurToken();
        if (token != null && "return".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            J1(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J1"));
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
        }else {
            expressionClient.recognition(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }
        tree.traceBack();
    }

    private void I(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("I"));
        Token token = tokens.getCurToken();
        if (token != null && "do".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            O(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && "while".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                expressionClient.recognition(tree,tokens,exceptions);
                token = tokens.getCurToken();
                if (token != null && ";".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    L(tree,tokens,exceptions);
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

    private void O(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("O"));
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            R(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())){
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

    private void R(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("R"));
        N(tree,tokens,exceptions);
        R1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void N(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("N"));
        Token token = tokens.getCurToken();
        if (token!=null && ("if".equals(token.getType())|| "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()))){
            Q(tree,tokens,exceptions);
        }else if (token != null && "{".equals(token.getType())){
            O(tree,tokens,exceptions);
        }else if (token!=null && ("const".equals(token.getType()) || "void".equals(token.getType()) || "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))){
            V(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void Q(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("Q"));
//        J(tree,tokens,exceptions);
        Token token = tokens.getCurToken();
        if (token!=null && "if".equals(token.getType())){
            S(tree,tokens,exceptions);
        }else if (token!=null && "for".equals(token.getType())){
            G(tree,tokens,exceptions);
        }else if (token!=null && "while".equals(token.getType())){
            H(tree,tokens,exceptions);
        }else if (token!=null && "do".equals(token.getType())){
            I(tree,tokens,exceptions);
        }else if (token!=null && "return".equals(token.getType())){
            J(tree,tokens,exceptions);
        }else if (token!=null && ("break".equals(token.getType()) || "contniue".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token!=null && ";".equals(token.getType())){
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

    private void S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && "(".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    expressionClient.recognition(tree,tokens,exceptions);
                    token = tokens.getCurToken();
                    if (token != null && ")".equals(token.getType())){
                        tree.addChild(new TreeNode(token.getType()));
                        tokens.match();
                        tree.traceBack();
                        N(tree,tokens,exceptions);
                        S1(tree,tokens,exceptions);
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

    private void S1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            N(tree,tokens,exceptions);
        }else {
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void V(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        declarativeStatement.recognition(tree,tokens,exceptions);
    }

    private void R1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("R1"));
        R(tree,tokens,exceptions);
        tree.traceBack();
    }



    private void H(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("H"));
        Token token = tokens.getCurToken();
        if (token != null && "while".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                expressionClient.recognition(tree,tokens,exceptions);
                token = tokens.getCurToken();
                if (token != null && ")".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    N(tree,tokens,exceptions);
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

    private void G(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("G"));
        Token token = tokens.getCurToken();
        if (token != null && "for".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                expressionClient.recognition(tree,tokens,exceptions);
                token = tokens.getCurToken();
                if (token != null && ";".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    expressionClient.recognition(tree,tokens,exceptions);
                    token = tokens.getCurToken();
                    if (token != null && ";".equals(token.getType())){
                        tree.addChild(new TreeNode(token.getType()));
                        tokens.match();
                        tree.traceBack();
                        expressionClient.recognition(tree,tokens,exceptions);
                        token = tokens.getCurToken();
                        if (token != null && ";".equals(token.getType())){
                            tree.addChild(new TreeNode(token.getType()));
                            tokens.match();
                            tree.traceBack();
                            expressionClient.recognition(tree,tokens,exceptions);
                            if (token != null && ")".equals(token.getType())){
                                tree.addChild(new TreeNode(token.getType()));
                                tokens.match();
                                tree.traceBack();
                                N(tree,tokens,exceptions);
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
        if (token != null && "if".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
                expressionClient.recognition(tree,tokens,exceptions);
                token = tokens.getCurToken();
                if (token != null && ")".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getType()));
                    tokens.match();
                    tree.traceBack();
                    L(tree,tokens,exceptions);
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
        if (token != null && "else".equals(token.getType())){
            tree.addChild(new TreeNode(token.getType()));
            tokens.match();
            tree.traceBack();
            L(tree,tokens,exceptions);
        }
        tree.traceBack();
    }


    private void A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("A"));
        expressionClient.recognition(tree,tokens,exceptions);
        tree.traceBack();
    }

}

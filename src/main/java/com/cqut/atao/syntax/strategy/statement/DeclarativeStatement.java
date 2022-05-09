package com.cqut.atao.syntax.strategy.statement;

import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.Expression;
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

    ExpressionStatement expressionStatement = new ExpressionStatement();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        S(tree,tokens,exceptions);
    }

    private void S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("声明语句"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())){
            C(tree,tokens,exceptions);
        }else if (token != null && ("int".equals(token.getType()) || "char".equals(token.getType()) || "void".equals(token.getType()) || "float".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            S1(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void S1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S1"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            S2(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void S2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S2"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            O(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null && "=".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            K(tree,tokens,exceptions);
            J1(tree,tokens,exceptions);
        }else if (token != null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            J(tree,tokens,exceptions);
        } else if (token != null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    public void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("常量声明"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                F(tree,tokens,exceptions);
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("常量声明列表"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "=".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && ("字符".equals(token.getType()) || "实数".equals(token.getType()) || "字符串".equals(token.getType()) || "整数".equals(token.getType()))){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    F1(tree,tokens,exceptions);
                }else {
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
            }else {
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("F1"));
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else if (token != null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            F(tree,tokens,exceptions);
            tree.traceBack();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void J(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("变量声明表"));
        K(tree,tokens,exceptions);
        J1(tree, tokens, exceptions);
        tree.traceBack();
    }

    private void J1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J1"));
        Token token = tokens.getCurToken();
        if (token!=null && ";".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else if (token!=null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            J(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void K(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("单体变量声明"));
        Token token = tokens.getCurToken();
        if (token!=null && ("标识符".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            K1(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void K1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K1"));
        Token token = tokens.getCurToken();
        if (token!=null && "=".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            expressionStatement.recognition(tree,tokens,exceptions);
            exceptions.remove(exceptions.size()-1);
            exceptions.remove(exceptions.size()-1);
            exceptions.remove(exceptions.size()-1);
        }else if (token != null && (",".equals(token.getType()) || ";".equals(token.getType()))){
            pass();
        } else if (token == null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void pass() {
        return;
    }

    private void O(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("函数声明形参列表"));
        Token token = tokens.getCurToken();
        if (token!=null && ("void".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType())||"float".equals(token.getType()))){
            P(tree,tokens,exceptions);
        }else if (token != null && ")".equals(token.getType())){
            pass();
        }else if (token!=null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void P(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("函数声明形参"));
        Token token = tokens.getCurToken();
        if (token!=null && ("void".equals(token.getType())||"float".equals(token.getType())||"int".equals(token.getType())||"char".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            P1(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void P1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("P1"));
        Token token = tokens.getCurToken();
        if (token!=null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            P(tree,tokens,exceptions);
        }else if (token != null && ")".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


}

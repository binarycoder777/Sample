package com.cqut.atao.syntax.strategy.statement;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.Expression;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName FunctionStatement.java
 * @Description TODO
 * @createTime 2022年05月08日 22:26:00
 */
public class FunctionStatement implements Expression {

    private ExecuteStatement executeStatement = new ExecuteStatement();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        S(tree,tokens,exceptions);
    }

    private void pass(){
        return;
    }

    private void S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("函数定义"));
        Token token = tokens.getCurToken();
        if (token != null && ("void".equals(token.getType()) || "int".equals(token.getType()) || "char".equals(token.getType()) || "float".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && "(".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                }else if (token != null){
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
                C(tree,tokens,exceptions);
                token = tokens.getCurToken();
                if (token != null && ")".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    executeStatement.recognition(tree,tokens,exceptions);
                }else if (token != null){
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }
        tree.traceBack();
    }

    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("函数定义参数列表"));
        Token token = tokens.getCurToken();
        if (token != null && ("int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))){
            E(tree,tokens,exceptions);
        }else if (token != null && ")".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void E(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("函数定义形参"));
        Token token = tokens.getCurToken();
        if (token != null && ("int".equals(token.getType()) || "char".equals(token.getType()) || "float".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                E1(tree,tokens,exceptions);
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }
        tree.traceBack();
    }

    private void E1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("E1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType()) ){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            E(tree,tokens,exceptions);
        }else if (token != null && ")".equals(token.getType())){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

}

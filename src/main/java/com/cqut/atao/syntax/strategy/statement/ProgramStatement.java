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
 * @ClassName ProgramStatement.java
 * @Description TODO
 * @createTime 2022年05月09日 00:19:00
 */
public class ProgramStatement implements Expression {

    private FunctionStatement functionStatement = new FunctionStatement();

    private ExecuteStatement executeStatement = new ExecuteStatement();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        S(tree,tokens,exceptions);
    }

    private void S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || "int".equals(token.getType()) || "char".equals(token.getType()) || "float".equals(token.getType()) || "void".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "main".equals(token.getVal().toString())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && "(".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    token = tokens.getCurToken();
                    if (token != null && ")".equals(token.getType())){
                        tree.addChild(new TreeNode(token.getVal().toString()));
                        tokens.match();
                        tree.traceBack();
                        executeStatement.recognition(tree,tokens,exceptions);
                        C(tree,tokens,exceptions);
                    }else if (token != null){
                        exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                    }
                }else if (token != null){
                    exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
                }
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C"));
        Token token = tokens.getCurToken();
        if (token != null && ("int".equals(token.getType()) || "char".equals(token.getType()) || "float".equals(token.getType()) || "void".equals(token.getType()))){
            functionStatement.recognition(tree,tokens,exceptions);
            C1(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void C1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C1"));
        Token token = tokens.getCurToken();
        if (token != null && ("int".equals(token.getType()) || "char".equals(token.getType()) || "float".equals(token.getType()) || "void".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            C(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

}

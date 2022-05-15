package com.cqut.atao.syntax;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Parser.java
 * @Description TODO
 * @createTime 2022年05月01日 13:49:00
 */
public class Parser {

    private TokenList<Token> tokens;

    private List<Exception> exceptions;

    public void syataxAnalysis(MyTree tree,TokenList<Token> tokens,List<Exception> exceptions){
        Token token = tokens.getNextToken();
        while (token != null && !"main".equals(token.getVal())){
            if ("const".equals(token.getVal())){
                // 处理常量声明
                token = tokens.getCurToken();
                if (token != null && ";".equals(token)){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                }else {
                    tokens.match();
                    exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
                }
            }else if (judgeType(token)){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                token = tokens.getCurToken();
                if (token != null && "标识符".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                }else {
                    tokens.match();
                    exceptions.add(new ParseException("不是标识符",tokens.getCurToken()));
                }
                token = tokens.getCurToken();
                if (token != null && ",".equals(token.getType()) || "=".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    // 处理变量声明
                    token = tokens.getCurToken();
                    if (token != null && ";".equals(token)){
                        tree.addChild(new TreeNode(token.getVal().toString()));
                        tokens.match();
                        tree.traceBack();
                    }else {
                        tokens.match();
                        exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
                    }
                }else if (token != null && "(".equals(token.getType())){
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    // 处理函数声明
                    token = tokens.getCurToken();
                    if (token != null && ";".equals(token)){
                        tree.addChild(new TreeNode(token.getVal().toString()));
                        tokens.match();
                        tree.traceBack();
                    }else {
                        tokens.match();
                        exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
                    }
                } else {
                    tokens.match();
                    exceptions.add(new ParseException("错误声明语句",tokens.getCurToken()));
                }
            }else {
                tokens.match();
                exceptions.add(new ParseException("错误声明语句",tokens.getCurToken()));
            }
            token = tokens.getCurToken();
        }
        if ( token!= null && "main".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }
        if (!"(".equals(token.getVal())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else {
            tokens.match();
            exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
        }
        if (!"(".equals(token.getVal())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else {
            tokens.match();
            exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
        }
        // 处理复合语句
        token = tokens.getNextToken();
        while (token != null && (judgeType(token) || "void".equals(token))) {
            // 处理函数定义
            token = tokens.getNextToken();
        }
    }

    // 判断token是否是int、char、float类型
    public boolean judgeType(Token token){
        return "int".equals(token.getVal()) || "char".equals(token.getVal()) || "float".equals(token.getVal());
    }

}

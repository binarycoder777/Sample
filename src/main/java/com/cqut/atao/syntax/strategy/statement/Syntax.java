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
 * @ClassName Syntax.java
 * @Description 语法分析
 * @createTime 2022年05月13日 14:03:00
 */
public class Syntax {

    public void math(MyTree tree,TokenList<Token> tokens){
         tree.addChild(new TreeNode(tokens.getCurToken().getVal().toString()));
        tokens.match();
         tree.traceBack();
    }

    public boolean judgeD(Token token){
        return ">".equals(token.getType()) || "<".equals(token.getType()) || ">=".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType());
    }

    public boolean judgeE(Token token){
        return "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()) || "void".equals(token.getType());
    }

    public boolean judgeConst(Token token){
        return "字符串".equals(token.getType())||"字符".equals(token.getType())||"实数".equals(token.getType())||"整数".equals(token.getType());
    }

    private void pass(){
        return;
    }

    public void expression(MyTree tree,TokenList<Token> tokens,List<Exception> exceptions){
         tree.addChild(new TreeNode("表达式"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            math(tree,tokens);
            AR(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            ar_A1(tree,tokens,exceptions);
            AR1(tree,tokens,exceptions);
            expression1(tree,tokens,exceptions);
        }else if (token != null && ("字符串".equals(token.getType()) || "字符".equals(token.getType()) || "整数".equals(token.getType())||"实数".equals(token.getType()))){
            math(tree,tokens);
            ar_A1(tree,tokens,exceptions);
            AR1(tree,tokens,exceptions);
            expression1(tree,tokens,exceptions);
        }else if (token != null && "标识符".equals(token.getType())){
            math(tree,tokens);
            expression4(tree,tokens,exceptions);
        }else if (token != null && "!".equals(token.getType())){
            math(tree,tokens);
            BO(tree,tokens,exceptions);
            bo_A1(tree,tokens,exceptions);
            BO1(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
         tree.traceBack();
    }

    private void expression4(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("expression4"));
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "=".equals(token.getType()))){
            expression3(tree,tokens,exceptions);
        }else if (token != null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType())||"+".equals(token.getType()) || "-".equals(token.getType()) || judgeD(token) || "&&".equals(token.getType()) || "||".equals(token.getType()))){
            ar_A1(tree,tokens,exceptions);
            AR1(tree,tokens,exceptions);
            expression1(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void expression3(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("expression3"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            math(tree,tokens);
            ar_D(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            ar_A1(tree,tokens,exceptions);
            AR1(tree,tokens,exceptions);
            expression1(tree,tokens,exceptions);
        }else if (token != null && "=".equals(token.getType())){
            math(tree,tokens);
            expression(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void expression1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("expression1"));
        Token token = tokens.getCurToken();
        if (token != null && judgeD(token)){
            math(tree,tokens);
            AR(tree,tokens,exceptions);
            expression2(tree,tokens,exceptions);
        }else if (token != null && ("&&".equals(token.getType()) || "||".equals(token.getType()))){
            bo_A1(tree,tokens,exceptions);
            BO1(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void expression2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("expression2"));
        bo_A1(tree,tokens,exceptions);
        BO1(tree,tokens,exceptions);
        // tree.traceBack();
    }


    private void AR(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("算术表达式"));
        ar_A(tree,tokens,exceptions);
        AR1(tree,tokens,exceptions);
         tree.traceBack();
    }



    private void AR1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("AR1"));
        Token token = tokens.getCurToken();
        if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType()))){
            math(tree,tokens);
            AR(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void ar_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_A"));
        ar_B(tree,tokens,exceptions);
        ar_A1(tree,tokens,exceptions);
        // tree.traceBack();
    }

    private void ar_A1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_A1"));
        Token token = tokens.getCurToken();
        if (token != null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()))){
            math(tree,tokens);
            ar_A(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void ar_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_B"));
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))){
            math(tree,tokens);
            AR(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
        }else if (token !=null && ("字符串".equals(token.getType())||"字符".equals(token.getType()) || "整数".equals(token.getType())||"实数".equals(token.getType()))){
            math(tree,tokens);
        }else if (token !=null && ("标识符".equals(token.getType()))){
            math(tree,tokens);
            ar_B1(tree,tokens,exceptions);
        } else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void ar_B1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_B1"));
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))){
            math(tree,tokens);
            ar_D(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void ar_C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("函数调用"));
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            ar_D(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
        }else if (token != null){
            // error
        }
         tree.traceBack();
    }

    private void ar_D(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_D"));
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "标识符".equals(token.getType())||judgeConst(token) || "!".equals(token.getType()))){
            ar_E(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void ar_E(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_E"));
        expression(tree,tokens,exceptions);
        ar_E1(tree,tokens,exceptions);
        // tree.traceBack();
    }

    private void ar_E1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ar_E1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())){
            math(tree,tokens);
            ar_E(tree,tokens,exceptions);
        }else if (token != null){
            // error
        }
        // tree.traceBack();
    }

    private void RE(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("关系表达式"));
        AR(tree,tokens,exceptions);
        Token token = tokens.getCurToken();
        if (token != null && judgeD(token)){
            math(tree,tokens);
        }else {
            tokens.match();
            exceptions.add(new ParseException("缺少关系运算符",tokens.getCurToken()));
        }
        AR(tree,tokens,exceptions);
         tree.traceBack();
    }

    private void BO(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("布尔表达式"));
        bo_A(tree,tokens,exceptions);
        BO1(tree,tokens,exceptions);
         tree.traceBack();
    }

    private void BO1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("BO1"));
        Token token = tokens.getCurToken();
        if (token != null && "||".equals(token.getType())){
            math(tree,tokens);
            BO(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void bo_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("bo_A"));
        bo_B(tree,tokens,exceptions);
        bo_A1(tree,tokens,exceptions);
        // tree.traceBack();
    }

    private void bo_A1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("bo_A1"));
        Token token = tokens.getCurToken();
        if (token != null && "&&".equals(token.getType())){
            math(tree,tokens);
            BO(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void bo_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("bo_B"));
        Token token = tokens.getCurToken();
        if (token != null && "！".equals(token.getType())){
            math(tree,tokens);
            BO(tree,tokens,exceptions);
        }else if (token != null && ("(".equals(token.getType()) || judgeConst(token) || "标识符".equals(token.getType()))){
            AR(tree,tokens,exceptions);
            bo_B1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void bo_B1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("bo_B1"));
        Token token = tokens.getCurToken();
        if (token != null && judgeD(token)){
            math(tree,tokens);
            AR(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void AS(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("赋值表达式"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null && "=".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少=",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    public void DE(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("声明语句"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null && judgeE(token)){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("声明类型",tokens.getCurToken()));
            }
            de_E(tree,tokens,exceptions);
        }else if (token != null && judgeE(token)){
            math(tree,tokens);
            DE1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    private void DE1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("DE1"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            math(tree,tokens);
            DE2(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void DE2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("DE2"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode("函数声明"));
            math(tree,tokens);
            de_H(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
            tree.traceBack();
        }else if (token != null && "=".equals(token.getType())){
            tree.addChild(new TreeNode("变量声明"));
            math(tree,tokens);
            expression(tree,tokens,exceptions);
            de_F1(tree,tokens,exceptions);
            tree.traceBack();
        }else if (token != null && (";".equals(token.getType()) || ",".equals(token.getType()))){
            de_F1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    private void de_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("值声明"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()))){
           de_C(tree,tokens,exceptions);
        }else if (token != null && (judgeE(token))){
           de_D(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    public void de_C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("常量声明"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()))){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&judgeE(token)){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("声明类型",tokens.getCurToken()));
            }
            de_E(tree,tokens,exceptions);
        }else if (token != null && (judgeE(token))){
            de_D(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    private void de_E(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("常量声明列表"));
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"=".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("声明类型",tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null &&judgeConst(token)){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("声明类型",tokens.getCurToken()));
            }
            de_E1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void de_E1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_E1"));
        Token token = tokens.getCurToken();
        if (token != null && (";".equals(token.getType()))){
            math(tree,tokens);
        }else if (token != null && (",".equals(token.getType()))){
            math(tree,tokens);
            de_E(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    public void de_D(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("变量声明"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))){
            math(tree,tokens);
            de_F(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }


    private void de_F(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_F"));
        de_G(tree,tokens,exceptions);
        de_F1(tree,tokens,exceptions);
        // tree.traceBack();
    }

    private void de_F1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_F1"));
        Token token = tokens.getCurToken();
        if (token != null && (";".equals(token.getType()))){
            math(tree,tokens);
        }else if (token != null && (",".equals(token.getType()))){
            math(tree,tokens);
            de_F(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void de_G(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_G"));
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))){
            math(tree,tokens);
            de_G1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void de_G1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_G1"));
        Token token = tokens.getCurToken();
        if (token != null && ("=".equals(token.getType()))){
            math(tree,tokens);
            expression(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    private void de_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_B"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"标识符".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符",tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null &&"(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            de_H(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void de_H(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_H"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token) || "标识符".equals(token.getType()))){
           de_I(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void de_I(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_I"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))){
            math(tree,tokens);
            de_I1(tree,tokens,exceptions);
        }else if (token!=null && "标识符".equals(token.getType())){
            math(tree,tokens);
            de_I1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void de_I1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("de_I1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())){
            math(tree,tokens);
            de_I(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    private void EX(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("执行语句"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            ex_A(tree,tokens,exceptions);
        }else if (token!=null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))){
            ex_B(tree,tokens,exceptions);
        }else if (token!=null && ("{".equals(token.getType()))){
            math(tree,tokens);
            ex_I(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&"}".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少}",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    private void ex_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_A"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())){
            math(tree,tokens);
            ex_A1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_A1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_A1"));
        Token token = tokens.getCurToken();
        if (token != null && "=".equals(token.getType())){
            math(tree,tokens);
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
        }else if (token!=null && "(".equals(token.getType())){
            math(tree,tokens);
            ar_D(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_B"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())){
            ex_D(tree,tokens,exceptions);
        }else if (token!=null && "for".equals(token.getType())){
            ex_E(tree,tokens,exceptions);
        }else if (token!=null && "while".equals(token.getType())){
            ex_F(tree,tokens,exceptions);
        }else if (token!=null && "do".equals(token.getType())){
            ex_G(tree,tokens,exceptions);
        }else if (token!=null && "return".equals(token.getType())){
            ex_H(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_C"));
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())){
            math(tree,tokens);
            ex_I(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&"}".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少}",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_I(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_I"));
        Statement(tree,tokens,exceptions);
        ex_I1(tree,tokens,exceptions);
        // tree.traceBack();
    }

    private void ex_I1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_I1"));
        Token token = tokens.getCurToken();
        if (token != null &&( "{".equals(token.getType()) || "const".equals(token.getType()) || judgeE(token) || "标识符".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))){
            ex_I(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_D(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("<if 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            Statement(tree,tokens,exceptions);
            ex_D1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }


    private void ex_D1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_D1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())){
            math(tree,tokens);
            Statement(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_E(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("<for 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "for".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            ex_K(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }


    private void ex_F(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("<while 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "while".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            ex_K(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }


    private void ex_G(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("<do while 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "do".equals(token.getType())){
            math(tree,tokens);
            ex_L(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&"while".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少while",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    private void ex_H(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("<return 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "return".equals(token.getType())){
            math(tree,tokens);
            ex_H1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    private void ex_H1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_H1"));
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())){
            math(tree,tokens);
        }else if (token!=null && ("(".equals(token.getType()) || "!".equals(token.getType()) || "标识符".equals(token.getType()) || judgeConst(token))){
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_K(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_K"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&(judgeE(token))){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少类型",tokens.getCurToken()));
            }
            de_E(tree,tokens,exceptions);
        }else if (token!=null && (judgeE(token))){
            math(tree,tokens);
            DE1(tree,tokens,exceptions);
        }else if (token!=null && "标识符".equals(token.getType())){
//            math(tree,tokens);
            DE1(tree,tokens,exceptions);
        } else if (token!=null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()))){
            ex_M(tree,tokens,exceptions);
        }else if (token!=null && "{".equals(token.getType())){
            ex_L(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    private void ex_L(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_L"));
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())){
            math(tree,tokens);
            ex_N(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&"}".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少}",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_N(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_N"));
        ex_K(tree,tokens,exceptions);
        ex_N1(tree,tokens,exceptions);
        // tree.traceBack();
    }

    private void ex_N1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_N1"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || judgeE(token) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()) || "{".equals(token.getType()) || "标识符".equals(token.getType()))){
          ex_N(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    private void ex_M(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_M"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())){
           ex_O(tree,tokens,exceptions);
        }else if (token != null && "for".equals(token.getType())){
            ex_E(tree,tokens,exceptions);
        }else if (token != null && "while".equals(token.getType())){
            ex_F(tree,tokens,exceptions);
        }else if (token != null && "do".equals(token.getType())){
            ex_G(tree,tokens,exceptions);
        }else if (token != null && "return".equals(token.getType())){
            ex_H(tree,tokens,exceptions);
        }else if (token != null && "break".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
        }else if (token != null && "continue".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&";".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少;",tokens.getCurToken()));
            }
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void ex_O(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_O"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            expression(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            ex_K(tree,tokens,exceptions);
            ex_O1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    private void ex_O1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("ex_O1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())){
            math(tree,tokens);
            ex_K(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    public void Statement(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("语句"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || judgeE(token))){
            DE(tree,tokens,exceptions);
        }else if (token!=null && ("标识符".equals(token.getType()) || "{".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))){
            EX(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }


    public void Function(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("函数"));
        Token token = tokens.getCurToken();
        if (token != null && ( judgeE(token))){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"标识符".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符",tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null &&"(".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
            }
            fu_A(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null &&")".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
            }
            ex_C(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
         tree.traceBack();
    }

    private void fu_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("fu_A"));
        Token token = tokens.getCurToken();
        if (token != null && ( judgeE(token))){
            fu_B(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void fu_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("fu_B"));
        Token token = tokens.getCurToken();
        if (token != null && ( judgeE(token))){
            math(tree,tokens);
            token = tokens.getCurToken();
            if (token != null &&"标识符".equals(token.getType())){
                math(tree,tokens);
            }else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符",tokens.getCurToken()));
            }
            fu_B1(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }

    private void fu_B1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("fu_B1"));
        Token token = tokens.getCurToken();
        if (token != null && ( ",".equals(token.getType()))){
            math(tree,tokens);
            fu_B(tree,tokens,exceptions);
        }else if (token!=null){
            // error
        }
        // tree.traceBack();
    }


    public void Program(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
         tree.addChild(new TreeNode("程序"));
        DE(tree,tokens,exceptions);
        Token token = tokens.getCurToken();
        if (token != null && ("main".equals(token.getVal().toString()))){
            math(tree,tokens);
        }else {
            tokens.match();
            exceptions.add(new ParseException("缺少main",tokens.getCurToken()));
        }
        token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))){
            math(tree,tokens);
        }else {
            tokens.match();
            exceptions.add(new ParseException("缺少(",tokens.getCurToken()));
        }
        token = tokens.getCurToken();
        if (token != null && (")".equals(token.getType()))){
            math(tree,tokens);
        }else {
            tokens.match();
            exceptions.add(new ParseException("缺少)",tokens.getCurToken()));
        }
        ex_C(tree,tokens,exceptions);
        po_A(tree,tokens,exceptions);
         tree.traceBack();
    }

    private void po_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        // tree.addChild(new TreeNode("po_A"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))){
            Function(tree,tokens,exceptions);
            po_A(tree,tokens,exceptions);
        }else if (token != null){

        }
        // tree.traceBack();
    }


}

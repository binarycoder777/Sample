package com.cqut.atao.syntax.strategy.expression;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ExpressionStatement.java
 * @Description TODO
 * @createTime 2022年05月08日 08:36:00
 */
public class ExpressionStatement implements Expression{

    @Override
    public void recognition(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        E(tree,tokens,exceptions);
    }

    private void pass(){
        return;
    }

    private void E(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("表达式"));
        Token token = tokens.getCurToken();
        if (token != null && ("实数".equals(token.getType())||"整数".equals(token.getType()) || "字符".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_A1(tree,tokens,exceptions);
            ar_S1(tree,tokens,exceptions);
            E1(tree,tokens,exceptions);
        }else if (token != null && ("(".equals(token.getType())||"整数".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_S(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && (")".equals(token.getType()))) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                ar_A1(tree,tokens,exceptions);
                ar_S1(tree,tokens,exceptions);
                E1(tree,tokens,exceptions);
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null && "标识符".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            E3(tree,tokens,exceptions);
        }else if (token != null && "!".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            bo_A(tree,tokens,exceptions);
            bo_B1(tree,tokens,exceptions);
            bo_A1(tree,tokens,exceptions);
        }
        tree.traceBack();
    }

    private void E3(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("E3"));
        Token token = tokens.getCurToken();
        if (token != null && "=".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            E(tree,tokens,exceptions);
        }else if (token != null && "(".equals(token.getType())){
            ar_B1(tree,tokens,exceptions);
            ar_A1(tree,tokens,exceptions);
            ar_S1(tree,tokens,exceptions);
            E1(tree,tokens,exceptions);
        }else if (token != null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()))){
            ar_A1(tree,tokens,exceptions);
            ar_S1(tree,tokens,exceptions);
            E1(tree,tokens,exceptions);
        }else if (token != null && (">".equals(token.getType())||">=".equals(token.getType()) || "<".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType()) || "&&".equals(token.getType()) || "||".equals(token.getType()))){
            E1(tree,tokens,exceptions);
        }else if (token != null && (",".equals(token.getType()) || ")".equals(token.getType())||"&&".equals(token.getType())||"||".equals(token.getType())||"+".equals(token.getType())||"-".equals(token.getType())||"*".equals(token.getType())||"/".equals(token.getType())||"%".equals(token.getType())||">".equals(token.getType())||"<".equals(token.getType())||">=".equals(token.getType())||"<=".equals(token.getType())||"==".equals(token.getType())||"!=".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void E1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("E1"));
        Token token = tokens.getCurToken();
        if (token != null && (">".equals(token.getType())||"<".equals(token.getType())||">=".equals(token.getType())||"<=".equals(token.getType())||"==".equals(token.getType())||"!=".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_S(tree,tokens,exceptions);
            E2(tree,tokens,exceptions);
        }else if (token != null && (",".equals(token.getType()) || ")".equals(token.getType())||"||".equals(token.getType())||"&&".equals(token.getType()))){
            bo_B1(tree,tokens,exceptions);
            bo_A1(tree,tokens,exceptions);
        }else if (token != null && (",".equals(token.getType()) || ")".equals(token.getType())||"&&".equals(token.getType())||"||".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void E2(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("E2"));
        Token token = tokens.getCurToken();
        if (token != null && (",".equals(token.getType()) || ")".equals(token.getType())||"||".equals(token.getType())||"&&".equals(token.getType()))){
            bo_B1(tree,tokens,exceptions);
            bo_A1(tree,tokens,exceptions);
        }else if (token != null && (",".equals(token.getType()) || ")".equals(token.getType())||"&&".equals(token.getType())||"||".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void ar_S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("算术表达式"));
        ar_A(tree,tokens,exceptions);
        ar_S1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void ar_S1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("ar_S1"));
        Token token = tokens.getCurToken();
        if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_S(tree,tokens,exceptions);
        }else if (token != null && (",".equals(token.getType()) || ")".equals(token.getType()) || "&&".equals(token.getType()) || "||".equals(token.getType()) || ">".equals(token.getType())||"<".equals(token.getType())||">=".equals(token.getType())||"<=".equals(token.getType())||"==".equals(token.getType())||"!=".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void ar_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("项"));
        ar_B(tree,tokens,exceptions);
        ar_A1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void ar_A1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("ar_S1"));
        Token token = tokens.getCurToken();
        if (token != null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_A(tree,tokens,exceptions);
        }else if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType())||",".equals(token.getType()) || ")".equals(token.getType()) || "&&".equals(token.getType()) || "||".equals(token.getType()) || ">".equals(token.getType())||"<".equals(token.getType())||">=".equals(token.getType())||"<=".equals(token.getType())||"==".equals(token.getType())||"!=".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void ar_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("因子"));
        Token token = tokens.getCurToken();
        if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        }else if (token != null && "标识符".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_B1(tree,tokens, exceptions);
        }else if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_S(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void ar_B1(MyTree tree,TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("ar_B1"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();

            ar_I(tree,tokens,exceptions);

            token = tokens.getCurToken();
            if (token!=null && ")".equals(token.getType())){
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null && ("*".equals(token.getType())||"/".equals(token.getType())||"%".equals(token.getType()) || "+".equals(token.getType()) || "-".equals(token.getType()) || ")".equals(token.getType()) || ",".equals(token.getType())||"||".equals(token.getType())||"&&".equals(token.getType()) || ">".equals(token.getType())||"<".equals(token.getType())||">=".equals(token.getType())||"<=".equals(token.getType())||"==".equals(token.getType())||"!=".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void ar_I(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("实参列表"));
        Token token = tokens.getCurToken();
        if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()) || "标识符".equals(token.getType()) || "(".equals(token.getType()) || "!".equals(token.getType()))){
            ar_J(tree,tokens,exceptions);
        }else if (token != null && (")".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void ar_J(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("实参"));
        E(tree,tokens,exceptions);
        ar_J1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void ar_J1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("ar_J1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            ar_J(tree,tokens,exceptions);
        }else if (token != null && (")".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void bo_A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔表达式"));
        bo_B(tree,tokens,exceptions);
        bo_A1(tree,tokens,exceptions);
        tree.traceBack();
    }

    private void bo_A1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("bo_A1"));
        Token token = tokens.getCurToken();
        if (token!=null && "||".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            bo_A(tree,tokens,exceptions);
        }else if (token!=null && (",".equals(token.getType())||")".equals(token.getType())||"&&".equals(token.getType())||"||".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void bo_B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔项"));
        bo_C(tree,tokens,exceptions);
        bo_B1(tree,tokens,exceptions);
        tree.traceBack();
    }


    private void bo_B1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("bo_B1"));
        Token token = tokens.getCurToken();
        if (token!=null && "&&".equals(token.getType())){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            bo_A(tree,tokens,exceptions);
        }else if (token!=null && (",".equals(token.getType())||")".equals(token.getType())||"&&".equals(token.getType())||"||".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void bo_C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("布尔因子"));
        Token token = tokens.getCurToken();
        if (token != null && ("!".equals(token.getType()))){
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            bo_A(tree,tokens,exceptions);
        }else if (token != null && ("实数".equals(token.getType())||"整数".equals(token.getType())||"字符".equals(token.getType()) ||"标识符".equals(token.getType())||"（".equals(token.getType()))){
            ar_S(tree,tokens,exceptions);
            bo_C1(tree,tokens,exceptions);
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void bo_C1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("bo_C1"));
        Token token = tokens.getCurToken();
        if (token != null && (">".equals(token.getType())||">=".equals(token.getType()) || "<".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType()) || "&&".equals(token.getType()) || "||".equals(token.getType()))){
            ar_S(tree,tokens,exceptions);
        }else if (token != null && (",".equals(token.getType()) || ")".equals(token.getType())||"&&".equals(token.getType()) || "||".equals(token.getType()))){
            pass();
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }

}

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
 * @ClassName RelationalExpression.java
 * @Description 关系表达式
 * @createTime 2022年05月03日 16:29:00
 */
public class RelationalExpression implements Expression{

    private ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType())||"字符".equals(token.getType())||"标识符".equals(token.getType()))){
            A(tree,tokens,exceptions);
        }else {
            pass();
        }
    }

    private void pass(){
        return;
    }

    private void A(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("关系表达式"));
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType())||"字符".equals(token.getType())||"标识符".equals(token.getType()))){
            arithmeticExpression.recognition(tree,tokens,exceptions);
            token = tokens.getCurToken();
            if (token != null && (">".equals(token.getType()) || "<".equals(token.getType())|| ">=".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType()))){
                tree.addChild(new TreeNode(token.getType()));
                tokens.match();
                tree.traceBack();
            }else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes",tokens.getPreToken()));
            }
            token = tokens.getCurToken();
            if (token != null && ("(".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType())||"字符".equals(token.getType())||"标识符".equals(token.getType()))){
                arithmeticExpression.recognition(tree,tokens,exceptions);
            }else if (token != null){
                exceptions.add(new ParseException("关系表达式:Grammar mistakes",tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("关系表达式:Grammar mistakes",tokens.getPreToken()));
        }
        tree.traceBack();
    }
}

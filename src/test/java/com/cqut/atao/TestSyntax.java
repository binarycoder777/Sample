package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.ArithmeticExpression;
import com.cqut.atao.syntax.strategy.expression.RelationalExpression;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.token.Token;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TestSyntax.java
 * @Description TODO
 * @createTime 2022年05月02日 16:23:00
 */
public class TestSyntax {

    Logger logger = LoggerFactory.getLogger(TestSyntax.class);

    private Lexer lexer = new Lexer();

    ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    RelationalExpression relationalExpression = new RelationalExpression();

    @Test
    public void testArithmeticExpression(){
        String text = "a*b+a-b;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        ArrayList<Exception> exceptions = new ArrayList<>();
        arithmeticExpression.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }

    @Test
    public void testRelationalExpression(){
        String text = "a!=b;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        ArrayList<Exception> exceptions = new ArrayList<>();
        relationalExpression.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }

}

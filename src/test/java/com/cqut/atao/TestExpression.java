package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.*;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.token.Token;
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
public class TestExpression {

    Logger logger = LoggerFactory.getLogger(TestExpression.class);

    private Lexer lexer = new Lexer();

    ArithmeticExpression arithmeticExpression = new ArithmeticExpression();

    RelationalExpression relationalExpression = new RelationalExpression();

    BooleanExpression booleanExpression = new BooleanExpression();

    AssignmentExpression assignmentExpression = new AssignmentExpression();

    ExpressionClient expression = new ExpressionClient();

    @Test
    public void testArithmeticExpression(){
        String text = "c_global = 2;";
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


    @Test
    public void testBooleanExpression(){
        String text = "10>b && 20<c;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        ArrayList<Exception> exceptions = new ArrayList<>();
        booleanExpression.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }


    @Test
    public void testAssignmentExpression(){
        String text = "c_global = 2;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        ArrayList<Exception> exceptions = new ArrayList<>();
        assignmentExpression.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }

    @Test
    public void testExpressionClient(){
        String text = "c_global = 2;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        expression.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }

}

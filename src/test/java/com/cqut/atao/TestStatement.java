package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.ExpressionStatement;
import com.cqut.atao.syntax.strategy.statement.DeclarativeStatement;
import com.cqut.atao.syntax.strategy.statement.ExecuteStatement;
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
 * @ClassName TestStatement.java
 * @Description TODO
 * @createTime 2022年05月05日 18:11:00
 */
public class TestStatement {

    Logger logger = LoggerFactory.getLogger(TestStatement.class);

    private Lexer lexer = new Lexer();

    private ExpressionStatement expressionStatement = new ExpressionStatement();

    private DeclarativeStatement declarativeStatement = new DeclarativeStatement();

    private ExecuteStatement executeStatement = new ExecuteStatement();

    @Test
    public void testDeclarativeStatement(){
        String text = "int d_global = 3;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        declarativeStatement.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }

    @Test
    public void testExecuteStatement(){
        String text = "while(10!=20) break;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        executeStatement.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }


    @Test
    public void testExpressionStatement(){
        String text = "a = c > d || e - 10 % f * (g + l)/2";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        expressionStatement.recognition(tree,tokenList,exceptions);
        tree.print();
        logger.error(exceptions.toString());
    }
}

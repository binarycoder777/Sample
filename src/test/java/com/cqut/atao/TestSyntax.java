package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.statement.*;
import com.cqut.atao.syntax.tree.MyTree;
//import com.cqut.atao.token.String;
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
public class TestSyntax {

    Logger logger = LoggerFactory.getLogger(TestSyntax.class);

    private Lexer lexer = new Lexer();

    private Syntax syntax = new Syntax();

    @Test
    public void testExpressionStatement() {
//        String text = "10 % f * (g + l - c)/2";
        String text = "a = 10;";
//        String text = "a > 10";
//        String text = "e > 10 || b > 10";
//        String text = "a = c > d || e - 10 % f * (g + l) / 2 > 3";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        syntax.expression();
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }

    @Test
    public void testDeclarativeStatement() {
//        String text = "int a = 10 + c;";
        String text = "void hello(int,float);";
//        String text = "int a = b = (10 + c) / 2 + ( b - d % 2 + e * 3 );";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        syntax.DE();
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }

    @Test
    public void testExecuteStatement() {
        String text = "a = seq(m-1);";
//        String text = "while(10!=20) break;";
//        String text = "for(i=10;i<20;i=i+ 1){ const int a = 1; }";
//        String text = "while( k > 10 ){ if ( a > 10 ) { int b = 20; } else { int a = g = (10+c)/2+(b-d)%2+e*3; } }";
//        String text = "do { " +
//                "if ( a > 10 ) " +
//                "{ " +
//                "int b = hello();" +
//                "int b =< 20+b/2+30; " +
//                "} " +
//                "} while k > 30;";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        syntax.Statement();
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }

    @Test
    public void testFunctionStatement() {
//        String text = "void if_fuc(int i,int j) { int a = 10; if(a>b) c=1; else c=2;}";
//        String text = "int for_fuc() { int i=10; for(i=1;i<10;i=1) { int a = b = (10+c)/2+(b-d%2+e*3);} }";
        String text = "int seq(int m) \n" +
                "{\n" +
                "      int s,a,b;\n" +
                "      if(m<2)\n" +
                "          s = 10;\n" +
                "      else{\n" +
                "//           a = seq(m-1);//这种写法可以正确运行\n" +
                "//           b = seq(m-2);\n" +
                "//           s=a+b;\n" +
                "     s=seq(m-1)+seq(m-2);//这种写法运行结果不正确，中间代码翻译没有问题，需再测试目标代码翻译\n" +
                "      }\n" +
                "      return s;\n" +
                "\n" +
                "}";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        syntax.Function();
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }


    @Test
    public void testProgramStatement() {
        String filePath = "/Users/weitao/Desktop/面试/项目/compler/src/main/resources/test/";
        String text = ChairmanshipCoder.readFile(filePath+"test1.txt");
        System.out.println(text);
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        syntax.Program();
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }

}
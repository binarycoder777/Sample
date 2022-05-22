package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.middle.MiddleCode;
import com.cqut.atao.syntax.Parser;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.statement.Syntax;
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
 * @ClassName TestStatement.java
 * @Description Parser
 * @createTime 2022年05月05日 18:11:00
 */
public class TestParser {

    Logger logger = LoggerFactory.getLogger(TestParser.class);

    private Lexer lexer = new Lexer();


    private Parser parser = new Parser();

    MyTree tree = new MyTree();

    MiddleCode middleCode = new MiddleCode();


    @Test
    public void testProgramStatement() {
        parser.getSyntax().setMiddleCode(middleCode);
//        String text = "// 双递归函数调用测试2：计算组合数\n" +
//                "const int a = 10, b = 20;\n"+
//                "const float c = 10, d = 20;\n"+
//                "int min(int,int);\n" +
//                "int max(float,float);\n" +
//                "main()\n" +
//                "{\n" +
//                "   int m,k,result;\n" +
//                "   const int hh = 20;\n" +
//                "   m = read();\n" +
//                "   k = read();\n" +
//                "   result = comp(m,k);\n" +
//                "   write(result);\n" +
//                "}\n" +
//                "\n" +
//                "int comp(int n,int i) {\n" +
//                "int a,b; \n" +
//                "if(n < i || i > 10)\n" +
//                "\t{\n" +
//                "\t\treturn 1;\n" +
//                "\t}\n" +
//                "\t a = 3 + comp(n-1,i);\n" +
//                "\t b = comp(n-1,i-1);\n" +
//                "\treturn a + b; \n" +
//                "}";
        String text = "// 双递归函数调用测试2：计算组合数\n" +
                "const int a = 10, b = 20;main(){" +
                "int a = 10 + b / 2;" +
                "}";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        List<Exception> exceptions = new ArrayList<>();

        parser.syataxAnalysis(tree, tokenList, exceptions);
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }

    @Test
    public void testFillTable() {
//        logger.error(middleCode.getTable().getConstTable().toString()+"\n\n");
//        logger.error(middleCode.getTable().getFunctionTable().toString());
        logger.error(middleCode.getFourTable().toString());
    }


    @Test
    public void testAR() {
        parser.getSyntax().setMiddleCode(middleCode);
        String text = "A = B + C * (-D);";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        List<Exception> exceptions = new ArrayList<>();
        parser.setPar(tree,tokenList,exceptions);
        parser.getSyntax().expression();
        tree.print();
        logger.error(middleCode.getFourTable().toString());
    }

    @Test
    public void testBO() {
        parser.getSyntax().setMiddleCode(middleCode);
//        String text = "a < b && c < d && e > f";
        String text = "A && B && C > D";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        List<Exception> exceptions = new ArrayList<>();
        parser.setPar(tree,tokenList,exceptions);
        parser.getSyntax().expression();
        tree.print();
        logger.error(middleCode.getFourTable().toString());
    }

    @Test
    public void testEX() {
        parser.getSyntax().setMiddleCode(middleCode);
        String text = "if(A && B && C > D) { i = i + 1;}";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        List<Exception> exceptions = new ArrayList<>();
        parser.setPar(tree,tokenList,exceptions);
        parser.getSyntax().EX();
        tree.print();
        logger.error(middleCode.getFourTable().toString());
    }

    @Test
    public void testPO() {
        parser.getSyntax().setMiddleCode(middleCode);
        String filePath = "/Users/weitao/Desktop/面试/项目/compler/src/main/resources/test/";
        String text = ChairmanshipCoder.readFile(filePath+"test2.txt");
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        List<Exception> exceptions = new ArrayList<>();
        parser.setPar(tree,tokenList,exceptions);
        parser.syataxAnalysis(tree,tokenList,exceptions);
        tree.print();
        logger.error(middleCode.getFourTable().toString());
        logger.error(middleCode.getTable().getTable());
    }



}
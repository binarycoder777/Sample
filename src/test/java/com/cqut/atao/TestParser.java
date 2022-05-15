package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.syntax.Parser;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.statement.Syntax;
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
 * @Description Parser
 * @createTime 2022年05月05日 18:11:00
 */
public class TestParser {

    Logger logger = LoggerFactory.getLogger(TestParser.class);

    private Lexer lexer = new Lexer();

    private Parser parser = new Parser();

    @Test
    public void testProgramStatement() {
        String text = "// 双递归函数调用测试2：计算组合数\n" +
                "const int a = 10;\n"+
                "int comp(int,int);\n" +
                "int comp(int,int);\n" +
                "main()\n" +
                "{\n" +
                "   int m,k,result;\n" +
                "   m = read();\n" +
                "   k = read();\n" +
                "   result = comp(m,k);\n" +
                "   write(result);\n" +
                "}\n" +
                "\n" +
                "int comp(int n,int i) {\n" +
                "int a,b; \n" +
                "if(n < i || i > 10)\n" +
                "\t{\n" +
                "\t\treturn 1;\n" +
                "\t}\n" +
                "\t a = comp(n-1,i);\n" +
                "\t b = comp(n-1,i-1);\n" +
                "\treturn a + b; \n" +
                "}";
        lexer.lexicalAnalysis(text);
        List<Token> tokens = lexer.getTokens();
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        parser.syataxAnalysis(tree, tokenList, exceptions);
        tree.print();
        for (Exception exception : exceptions) {
            logger.error(exception.toString() + "\n");
        }
    }

}
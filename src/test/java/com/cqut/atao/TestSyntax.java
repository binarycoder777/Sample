package com.cqut.atao;

import com.cqut.atao.lexical.Lexer;
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
//        String text = "//双递归函数调用测试2：求菲波拉契数列\n" +
//                "int seq(int);\n" +
//                "main()\n" +
//                "{\n" +
//                "   int n;\n" +
//                "   n=read();\n" +
//                "   write(seq(n));\n" +
//                "}\n" +
//                "\n" +
//                "int seq(int m) \n" +
//                "{\n" +
//                "      int s,a,b;\n" +
//                "      if(m<2)\n" +
//                "          s=1;\n" +
//                "      else{\n" +
//                "//           a=seq(m-1);//这种写法可以正确运行\n" +
//                "//           b=seq(m-2);\n" +
//                "//           s=a+b;\n" +
//                "     s=seq(m-1)+seq(m-2);//这种写法运行结果不正确，中间代码翻译没有问题，需再测试目标代码翻译\n" +
//                "      }\n" +
//                "      return s;\n" +
//                "\n" +
//                "}";
        String text = "// 双递归函数调用测试2：计算组合数\n" +
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
//       String text = "//for嵌套if,求1到给定数N以内所有奇数的和\n" +
//                "main()\n" +
//                "{\n" +
//                "  int i,N,sum = 10;\n" +
//                "  N = read();\n" +
//                "  for(i=1; i != N;i=i+1)\n" +
//                "\n" +
//                "     if(i%2 == 1)\n" +
//                "\t     sum = sum+i;\n" +
//                "\n" +
//                "  write(sum);\n" +
//                "}\n";
//        String text = "//if and while,�׳�\n" +
//                "main()\n" +
//                "{\n" +
//                "  int i,factor,n;\n" +
//                " i=10;\n" +
//                "  n = read();\n" +
//                "  if(n<1)\n" +
//                "{\n" +
//                "\tfactor=10;\n" +
//                "    }\n" +
//                "    else {\n" +
//                "       factor=1;\n" +
//                "    }\n" +
//                "  while(i<n)\n" +
//                "{\n" +
//                "      i=i+1;\n" +
//                "      factor=factor*i;\n" +
//                "    }\n" +
//                "  write(factor);\n" +
//                "}\n";
//        String text = "//˫��forѭ�����ԣ�����������ڵ�����\n" +
//                "main(){\n" +
//                "    int N = read() ;\n" +
//                "    int count=10,nprime=10,i,j;\n" +
//                "    for(i=2;i<N;i=i+1) {\n" +
//                "       nprime = 10;\n" +
//                "       for(j=2;j<i;j=j+1) {\n" +
//                "\t   if(i%j == 10) nprime = nprime + 1;\n" +
//                "       }\n" +
//                "       if(nprime == 10) {\n" +
//                "            write(i);\n" +
//                "            count = count + 1;\n" +
//                "        }\n" +
//                "     }\n" +
//                "\n" +
//                "}\n" +
//                "\n";
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
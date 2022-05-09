package com.cqut.atao;


import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.chain.Chain;
import com.cqut.atao.lexical.chain.ChainBuilder;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TestChain.java
 * @Description TODO
 * @createTime 2022年04月19日 20:36:00
 */
// @RunWith注解表示运行在Spring容器中，包括controller，service，dao等
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ChairmanshipCoder.class)
public class TestLexer {

    @Autowired
    private Map<String,Integer> codeMap;

    Logger logger = LoggerFactory.getLogger(TestLexer.class);

    @Test
    public void testChain(){
        Chain chain = ChainBuilder.builder();
        int state = chain.getState(new Token(1),'h');
        logger.warn("==="+state+"===");
    }

    @Test
    public void testIdentfiyerStrategy(){
//        logger.warn(codeMap.toString());
//        logger.warn(ChairmanshipCoder.readCoder().toString());
        int lineNumber = 1;
        String val  = "public class TestChain";
        Line line = new Line(val,lineNumber);
        line.analysisLine(line);
        logger.warn(line.getTokens().toString());
    }

    @Test
    public void testNumberStrategy(){
//        logger.warn(codeMap.toString());
//        logger.warn(ChairmanshipCoder.readCoder().toString());
        int lineNumber = 1;
        String val  = "10.234";
        Line line = new Line(val,lineNumber);
        line.analysisLine(line);
        logger.warn(line.getTokens().toString());
    }

    @Test
    public void testOperatorStrategy(){
//        logger.warn(codeMap.toString());
//        logger.warn(ChairmanshipCoder.readCoder().toString());
        int lineNumber = 1;
        String val  = "+=10";
        Line line = new Line(val,lineNumber);
        line.analysisLine(line);
        logger.warn(line.getTokens().toString());
    }


    @Test
    public void testDelimiterStrategy(){
//        logger.warn(codeMap.toString());
//        logger.warn(ChairmanshipCoder.readCoder().toString());
        int lineNumber = 1;
        String val  =
                "// 单行注释\n" +
                        "/*\n" +
                        "多行注释 \n" +
                        "*/ \n" +
                        "const int a_global = 2;\n" +
                        "const int b_global = 3;\n" +
                        "double c_global = 2.4;\n" +
                        "extern unsigned int d_global = 3;\n" +
                        "register long e_global = 5;\n" +
                        "static short f_global = 2;\n" +
                        "\n" +
                        "void if_fuc()\n" +
                        "{\n" +
                        "   int a=10;\n" +
                        "   int b=1;\n" +
                        "   int c;\n" +
                        "   if(a>b && a==b)\n" +
                        "      c=1;\n" +
                        "   else\n" +
                        "     c=2;\n" +
                        "}\n" +
                        "void do_while_fuc()\n" +
                        "{\n" +
                        "    int a,b;\n" +
                        "    a=10;\n" +
                        "    b=1;\n" +
                        "    while(b<10\n" +
                        "    {\n" +
                        "        a++;\n" +
                        "    }\n" +
                        "} \n" +
                        "\n" +
                        "\n" +
                        "    do\n" +
                        "   {\n" +
                        "    ++a;\n" +
                        "   }(b<10);\n" +
                        "}\n" +
                        "\n" +
                        "int for_fuc()\n" +
                        "{\n" +
                        "    int ,i;\n" +
                        "    a=10;\n" +
                        "    for(i=1 i<10;i++)\n" +
                        "    {\n" +
                        "      a++;\n" +
                        "    }\n" +
                        "    return a;\n" +
                        "}\n" +
                        "int main()\n" +
                        "{\n" +
                        "    int a,b,c;\n" +
                        "    float e =0.5;\n" +
                        "    char d='2';\n" +
                        "    a = 10\n" +
                        "    b = 10;\n" +
                        "    c = a*b+a-b;\n" +
                        "    c *= b;\n" +
                        "    c = a|b;\n" +
                        "    c = a&b;\n" +
                        "    c = a^b;\n" +
                        "    c = !a;\n" +
                        "    c = c<<1;\n" +
                        "    c = switch_fuc(int a, int b)++;\n" +
                        "    c = ++switch_fuc(a,b);\n" +
                        "    c = sizeof(switch_fuc(a, b);\n" +
                        "    printf (\"c=%d\\n\",c;\n" +
                        "    return 1\n" +
                        "}";
        Line line = new Line(val,lineNumber);
        line.analysisLine(line);
        logger.warn(line.getTokens().toString());
    }


    @Test
    public void testlexer(){
//        logger.warn(codeMap.toString());
//        logger.warn(ChairmanshipCoder.readCoder().toString());
        int lineNumber = 1;
        String val  = "+ (";
        Line line = new Line(val,lineNumber);
        line.analysisLine(line);
        logger.warn(line.getTokens().toString());
    }

}

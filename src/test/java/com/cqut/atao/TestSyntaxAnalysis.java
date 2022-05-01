package com.cqut.atao;

import com.cqut.atao.chain.Chain;
import com.cqut.atao.chain.ChainBuilder;
import com.cqut.atao.configuration.ChairmanshipCoder;
import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.lexical.Line;
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
public class TestSyntaxAnalysis {

    Logger logger = LoggerFactory.getLogger(TestLexer.class);

    @Test
    public void test(){

    }

}

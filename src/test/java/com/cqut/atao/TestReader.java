package com.cqut.atao;

import com.cqut.atao.syntax.configuration.LLReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TestReader.java
 * @Description TODO
 * @createTime 2022年04月24日 16:55:00
 */
public class TestReader {

    Logger logger = LoggerFactory.getLogger(TestReader.class);

    @Test
    public void read(){
        BufferedReader reader = null;
        String ans = "";
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader("/Users/weitao/Desktop/面试/项目/compler/src/main/resources/grammar.md"));
            String s = null;
            // 一次读入一行，直到读入null为文件结束
            while ((s = reader.readLine()) != null) {
                ans += (s+'\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        logger.warn(ans);
    }

    @Test
    public void readLL(){
        List<Map<String, Set<String>>> maps = LLReader.readFirstAndFollow("/Users/weitao/Desktop/面试/项目/compler/src/main/java/com/cqut/atao/syntax/strategy/expression/ArithmeticExpression.txt");
        logger.warn(maps.get(1).toString());
    }

}

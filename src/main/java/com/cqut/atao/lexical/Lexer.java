package com.cqut.atao.lexical;

import com.cqut.atao.token.Token;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Lexer.java
 * @Description TODO
 * @createTime 2022年04月19日 08:50:00
 */
@Data
public class Lexer {

    Logger logger = LoggerFactory.getLogger(Lexer.class);

    private List<Token> tokens;

    private List<Exception> exceptions;

    public void lexicalAnalysis(String text){
        tokens = new ArrayList<>();
        exceptions = new ArrayList<>();
        Queue<Line> queue = Text.parse(text);
        while (!queue.isEmpty()){
            Line line = queue.poll();
            logger.warn(line.analysisLine(line).toString());
            tokens.addAll(line.getTokens());
            exceptions.addAll(line.getExceptions());
        }
    }

}

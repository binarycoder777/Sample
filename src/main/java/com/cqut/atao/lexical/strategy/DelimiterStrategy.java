package com.cqut.atao.lexical.strategy;


import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName DelimiterStrategy.java
 * @Description TODO
 * @createTime 2022年04月19日 21:12:00
 */
public class DelimiterStrategy implements MyStrategy {

    private Map<String,Integer> codeMap;

    public DelimiterStrategy() {
        this.codeMap = ChairmanshipCoder.readCoder();
    }

    @Override
    public void recognition(Line line) {
        line.addChar();
        Token token = line.getToken();
        StringBuilder val = token.getVal();
        token.setType(val.toString());
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }
}

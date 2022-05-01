package com.cqut.atao.lexical.strategy;


import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName StrStrategy.java
 * @Description TODO
 * @createTime 2022年04月19日 21:12:00
 */
public class StrStrategy implements MyStrategy {

    private Map<String,Integer> codeMap;

    public StrStrategy() {
        this.codeMap = ChairmanshipCoder.readCoder();
    }

    @Override
    public void recognition(Line line) {
        int state = state(line);
        while (!line.isEnd()){
            switch (state){
                case -1:return;
                case 1:state = state1(line);break;
                case 2:state2(line);return;
            }
        }
        if (!line.getToken().isEmpty()){
            state2(line);
        }
    }

    protected int state(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        line.addChar();
        return 1;
    }

    protected int state1(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state1_1(c)){
            line.addChar();
            return 1;
        }else if (state1_2(c)){
            line.addChar();
            return 2;
        }
        return -1;
    }

    protected boolean state1_1(char c){
        return c != '"';
    }

    protected boolean state1_2(char c){
        return c == '"';
    }

    protected void state2(Line line){
        Token token = line.getToken();
        token.setType("字符串");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }


}

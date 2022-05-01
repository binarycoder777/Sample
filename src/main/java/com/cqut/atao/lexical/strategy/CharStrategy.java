package com.cqut.atao.lexical.strategy;


import com.cqut.atao.exception.ParseException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName CharStrategy.java
 * @Description TODO
 * @createTime 2022年04月19日 21:12:00
 */
public class CharStrategy implements MyStrategy {

    private Map<String,Integer> codeMap;

    public CharStrategy() {
        this.codeMap = ChairmanshipCoder.readCoder();
    }

    @Override
    public void recognition(Line line) {
        int state = state0(line);
        while (line.isEnd()){
            switch (state){
                case -1:return;
                case 1:state = state1(line);break;
                case 2:state = state2(line);break;
                case 3:state3(line);return;
                case 4:state4(line);return;
            }
        }
    }

    protected int state0(Line line){
        if (line.isEnd()) return -1;
        line.addChar();
        return 1;
    }

    protected int state1(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state1_2(c)){
            line.addChar();
            return 2;
        }else if (state1_3(c)){
            line.addChar();
            return 3;
        }else {
            return 4;
        }
    }

    protected boolean state1_2(char c){
        return c != '\'';
    }

    protected boolean state1_3(char c){
        return c == '\'';
    }

    protected int state2(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state2_3(c)){
            line.addChar();
            return 3;
        }else {
            return 4;
        }
    }

    protected void state3(Line line){
        Token token = line.getToken();
        token.setType("字符");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }

    protected void state4(Line line){
        line.addException(new ParseException(line.getToken()));
    }


    protected boolean state2_3(char c){
        return c == '\'';
    }


}

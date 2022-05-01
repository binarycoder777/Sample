package com.cqut.atao.lexical.strategy;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.chain.ChainBlank;
import com.cqut.atao.lexical.chain.ChainDelimiter;
import com.cqut.atao.lexical.chain.ChainIdentifier;
import com.cqut.atao.lexical.chain.ChainOprator;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName IdentifierStrategy.java
 * @Description TODO
 * @createTime 2022年04月19日 21:11:00
 */
@Component
public class IdentifierStrategy implements MyStrategy {

    private Map<String,Integer> codeMap;

    public IdentifierStrategy() {
        this.codeMap = ChairmanshipCoder.readCoder();
    }

    @Override
    public void recognition(Line line) {
        int state = 1;
        line.addChar();
        while (!line.isEnd()){
            switch (state){
                case -1:return;
                case 1:state = state1(line);break;
                case 2:state2(line);return;
                case 3:state3(line);return;
            }
        }
        if (!line.getToken().isEmpty()){
            state2(line);
        }
    }

    protected int state1(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state1_1(c)){
            line.addChar();
            return 1;
        }else if (state1_2(c)) {
            return 2;
        }else {
            return 3;
        }
    }

    protected void state2(Line line){
        Token token = line.getToken();
        StringBuilder val = token.getVal();
        if (codeMap.containsKey(val.toString())){
            token.setType(val.toString());
        }else {
            token.setType("标识符");
        }
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }

    protected void state3(Line line){
         line.addException(new ParseException(line.getToken()));
    }

    protected boolean state1_1(char c){
        return ChainIdentifier.judgeChar(c);
    }

    protected boolean state1_2(char c){
        return ChainBlank.judgeChar(c) || ChainDelimiter.judgeChar(c) || ChainOprator.judgeChar(c);
    }

}

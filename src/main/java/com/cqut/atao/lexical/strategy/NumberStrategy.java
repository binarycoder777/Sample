package com.cqut.atao.lexical.strategy;


import com.cqut.atao.exception.ParseException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.chain.ChainBlank;
import com.cqut.atao.lexical.chain.ChainDelimiter;
import com.cqut.atao.lexical.chain.ChainDigit;
import com.cqut.atao.lexical.chain.ChainOprator;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName NumberStrategy.java
 * @Description TODO
 * @createTime 2022年04月19日 21:10:00
 */
public class NumberStrategy implements MyStrategy {

    private Map<String,Integer> codeMap;

    public NumberStrategy() {
        this.codeMap = ChairmanshipCoder.readCoder();
    }

    @Override
    public void recognition(Line line) {
        int state = state0(line);
        while (!line.isEnd()){
            switch (state){
                case -1:break;
                case 1:state = state1(line);break;
                case 3:state = state3(line);break;
                case -3:state = state_3(line);break;
                case 4:state4(line);break;
                case 5:state = state5(line);break;
                case 6:state = state6(line);break;
                case 7:state7(line);break;
                case 8:state = state8(line);break;
                case 9:state = state9(line);break;
                case 10:state = state10(line);break;
                case 11:state = state11(line);break;
                case 12:state = state12(line);break;
                case 13:state13(line);return;
                case 14:state14(line);return;
                case 15:state15(line);return;
                case 16:state16(line);return;
                default:break;
            }
        }
        if (!line.getToken().isEmpty()){
            state13(line);
        }
    }

    protected int state0(Line line){
        char c = line.getCurChar();
        line.addChar();
        if (state0_1(c)) return 1;
        if (state0_3(c)) return 3;
        return -1;
    }

    protected boolean state0_1(char c){
        if (ChainDigit.judgeDigit(c) && c != '0') return true;
        return false;
    }

    protected boolean state0_3(char c){
        if (c == '0') return true;
        return false;
    }

    protected int state1(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state1_1(c)) {
            line.addChar();
            return 1;
        }else if (state1_8(c)){
            line.addChar();
            return 8;
        }else if (state1_10(c)){
            line.addChar();
            return 10;
        }else if (state1_15(c)){
            return 15;
        }else {
            line.addChar();
            return 16;
        }
    }

    protected boolean state1_1(char c){
        return Character.isDigit(c);
    }

    protected boolean state1_8(char c){
        return c == '.';
    }

    protected boolean state1_10(char c){
        return c == 'E' || c == 'e';
    }

    protected boolean state1_15(char c){
        return c == ' ' || c == '\n' || ChainDelimiter.judgeChar(c) || ChainOprator.judgeChar(c);
    }

    protected int state3(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c <= '7' && c >= '0'){
            line.addChar();
            return -3;
        }else if (c == 'x' || c == 'X'){
            line.addChar();
            return 5;
        }else if (c == '.'){
            line.addChar();
            return 8;
        }
        return 16;
    }

    protected int state_3(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c <= '7' && c >= '0'){
            line.addChar();
            return -3;
        }
        return 4;
    }

    protected void state4(Line line){
        Token token = line.getToken();
        token.setType("实数");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }



    protected int state5(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if ((c <= '9' && c >= '0') || (c <= 'f' && c >= 'a') || (c <= 'F' && c >= 'A')){
            line.addChar();
            return 6;
        }
        return 16;
    }

    protected int state6(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if ((c <= '9' && c >= '0') || (c <= 'f' && c >= 'a') || (c <= 'F' && c >= 'A')){
            line.addChar();
            return 6;
        }
        return 3;
    }

    protected void state7(Line line){
        Token token = line.getToken();
        token.setType("实数");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }

    protected int state8(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state8_9(c)){
            line.addChar();
            return 9;
        }
        return 16;
    }

    protected boolean state8_9(char c){
        return Character.isDigit(c);
    }

    protected int state9(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state9_9(c)){
            line.addChar();
            return 9;
        }else if (state9_10(c)){
            line.addChar();
            return 10;
        }else if (state9_14(c)){
            return 14;
        }else {
            return 16;
        }
    }

    protected boolean state9_9(char c){
        return Character.isDigit(c);
    }

    protected boolean state9_10(char c){
        return c == 'E' || c == 'e';
    }

    protected boolean state9_14(char c){
        return ChainBlank.judgeChar(c) || ChainDelimiter.judgeChar(c) || ChainOprator.judgeChar(c);
    }

    protected int state10(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state10_11(c)){
            line.addChar();
            return 11;
        }else if (state10_12(c)){
            line.addChar();
            return 12;
        }else {
            return 16;
        }
    }

    protected boolean state10_11(char c){
        return c == '+' || c == '-';
    }

    protected boolean state10_12(char c){
        return Character.isDigit(c);
    }

    protected int state11(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state11_12(c)){
            line.addChar();
            return 12;
        }else {
            return 16;
        }
    }

    protected boolean state11_12(char c){
        return Character.isDigit(c);
    }

    protected int state12(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (state12_12(c)){
            line.addChar();
            return 12;
        }else if (state12_13(c)){
            return 13;
        }else {
            return 16;
        }
    }

    protected boolean state12_12(char c){
        return Character.isDigit(c);
    }

    protected boolean state12_13(char c){
        return ChainBlank.judgeChar(c) || ChainOprator.judgeChar(c) || ChainDelimiter.judgeChar(c);
    }

    protected void state13(Line line){
        Token token = line.getToken();
        token.setType("实数");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }

    protected void state14(Line line){
        Token token = line.getToken();
        token.setType("实数");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }

    protected void state15(Line line){
        Token token = line.getToken();
        token.setType("整数");
        token.setCode(codeMap.get(token.getType()));
        line.addToken();
    }

    protected void state16(Line line){
        line.addException(new ParseException(line.getToken()));
    }

}

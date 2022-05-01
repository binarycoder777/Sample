package com.cqut.atao.lexical.strategy;


import com.cqut.atao.exception.ParseException;
import com.cqut.atao.lexical.Line;
import com.cqut.atao.lexical.chain.ChainBlank;
import com.cqut.atao.lexical.chain.ChainDelimiter;
import com.cqut.atao.lexical.chain.ChainIdentifier;
import com.cqut.atao.lexical.configuration.ChairmanshipCoder;
import com.cqut.atao.token.Token;

import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName OperatorStrategy.java
 * @Description TODO
 * @createTime 2022年04月19日 21:11:00
 */
public class OperatorStrategy implements MyStrategy {

    private Map<String,Integer> codeMap;

    private StrategyCilent cilent;

    public OperatorStrategy() {
        this.codeMap = ChairmanshipCoder.readCoder();
    }

    public OperatorStrategy(StrategyCilent cilent) {
        this.codeMap = ChairmanshipCoder.readCoder();
        this.cilent = cilent;
    }

    @Override
    public void recognition(Line line) {
        int state = state(line);
        while (!line.isEnd()){
            switch (state){
                case -1:break;
                case 1:state = state1(line);break;
                case 2:state = state2(line);break;
                case 3:state3(line);return;
                case 4:state = state4(line);break;
                case 5:state = state5(line);break;
                case 6:state = state6(line);break;
                case 7:state = state7(line);break;
                case 8:state = state8(line);break;
                case 9:state = state9(line);break;
                case 10:state = state10(line);break;
                case 11:state = state11(line);break;
                case 12:state = state12(line);break;
                case 13:state = state13(line);break;
                case 14:state = state14(line);break;
                case 15:state = state15(line);break;
                case 16:state = state16(line);break;
                case 17:state = state17(line);break;
                case 18:state = state18(line);break;
                case 19:state = state19(line);break;
                case 20:state = state20(line);break;
                case 21:state = state21(line);break;
                case 22:state = state22(line);break;
                case 23:state23(line);return;
            }
        }
        if (!line.getToken().isEmpty()){
            state3(line);
        }
    }

    protected int state(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        line.addChar();
        if (state0_1(c)) return 1;
        if (state0_5(c)) return 5;
        if (state0_9(c)) return 9;
        if (state0_11(c)) return 11;
        if (state0_13(c)) return 13;
        if (state0_16(c)) return 16;
        if (state0_19(c)) return 19;
        if (state0_21(c)) return 21;
        return -1;
    }

    protected boolean state0_1(char ch){
        return ch == '*' || ch == '%' || ch == '!' || ch == '=' || ch == '^' || ch == '~';
    }

    protected boolean state0_5(char ch){
        return ch == '/';
    }

    protected boolean state0_9(char ch){
        return ch == '+';
    }

    protected boolean state0_11(char ch){
        return ch == '-';
    }

    protected boolean state0_13(char ch){
        return ch == '>';
    }

    protected boolean state0_16(char ch){
        return ch == '<';
    }

    protected boolean state0_19(char ch){
        return ch == '|';
    }

    protected boolean state0_21(char ch){
        return ch == '&';
    }

    protected int state1(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c == '='){
            line.addChar();
            return 2;
        }else if (ChainBlank.judgeChar(c) || Character.isDigit(c) || ChainIdentifier.judgeChar(c) || c == '\'' || c == '"'){
            return 3;
        }
        return 23;
    }

    protected int state2(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || Character.isDigit(c) || ChainIdentifier.judgeChar(c) || c == '\'' || c == '"'){
            return 3;
        }
        return 23;
    }

    protected void state3(Line line){
        Token token = line.getToken();
        StringBuilder val = token.getVal();
        if (codeMap.containsKey(val.toString())){
            token.setType(val.toString());
            token.setCode(codeMap.get(token.getType()));
            line.addToken();
        }
    }

    protected int state4(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c)|| Character.isDigit(c) || ChainIdentifier.judgeChar(c) || c == '\'' || c == '"'){
            return 3;
        }
        return 23;
    }

    protected int state5(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c == '='){
            line.addChar();
            return 2;
        }else if (ChainBlank.judgeChar(c) ||Character.isDigit(c) || ChainIdentifier.judgeChar(c) || c == '\'' || c == '"'){
            return 3;
        }else if (c == '/'){
            line.addChar();
            return 6;
        }else if (c == '*'){
            line.addChar();
            return 7;
        }
        return 23;
    }

    protected int state6(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c == '\n') return 3;
        line.addChar();
        return 6;
    }

    protected int state7(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        line.addChar();
        if (c != '*') return 7;
        return 8;
    }

    protected int state8(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        line.addChar();
        if (c == '/') return 3;
        return 7;
    }

    protected int state9(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c == '=' || ChainBlank.judgeChar(c)){
            line.addChar();
            return 2;
        }else if (c == '+'){
            line.addChar();
            return 10;
        }else if (ChainBlank.judgeChar(c) || Character.isDigit(c) || ChainIdentifier.judgeChar(c) || c == '\'' || c == '"'){
            return 3;
        }
        return 23;
    }

    protected int state10(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || ChainDelimiter.judgeChar(c)){
            return 3;
        }
        return 23;
    }

    protected int state11(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (c == '='){
            line.addChar();
            return 2;
        }else if (c == '-'){
            line.addChar();
            return 12;
        }else if (ChainBlank.judgeChar(c) || Character.isDigit(c) || ChainIdentifier.judgeChar(c) || c == '\'' || c == '"'){
            return 3;
        }
        return 23;
    }

    protected int state12(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || ChainDelimiter.judgeChar(c)){
            return 3;
        }
        return 23;
    }

    protected int state13(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }else if (c == '>'){
            line.addChar();
            return 14;
        }
        return 23;
    }

    protected int state14(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }else if (c == '>'){
            line.addChar();
            return 15;
        }
        return 23;
    }

    protected int state15(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }
        return 23;
    }

    protected int state16(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }else if (c == '<'){
            line.addChar();
            return 17;
        }
        return 23;
    }

    protected int state17(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }else if (c == '<'){
            line.addChar();
            return 18;
        }
        return 23;
    }

    protected int state18(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }
        return 23;
    }

    protected int state19(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }else if (c == '|'){
            line.addChar();
            return 20;
        }
        return 23;
    }

    protected int state20(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }
        return 23;
    }

    protected int state21(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainIdentifier.judgeChar(c) || Character.isDigit(c) || ChainBlank.judgeChar(c)){
            return 3;
        }else if (c == '&'){
            line.addChar();
            return 22;
        }
        return 23;
    }

    protected int state22(Line line){
        if (line.isEnd()) return -1;
        char c = line.getCurChar();
        if (ChainBlank.judgeChar(c) || ChainIdentifier.judgeChar(c) || Character.isDigit(c)){
            return 3;
        }
        return 23;
    }

    protected void state23(Line line){
        line.addException(new ParseException(line.getToken()));
    }






}

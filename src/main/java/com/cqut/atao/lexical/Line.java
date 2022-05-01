package com.cqut.atao.lexical;



import com.cqut.atao.lexical.chain.Chain;
import com.cqut.atao.lexical.chain.ChainBuilder;
import com.cqut.atao.lexical.strategy.StrategyCilent;
import com.cqut.atao.token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Line.java
 * @Description TODO
 * @createTime 2022年04月19日 09:33:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line {

    // 行值
    private String val;

    // 行号
    private int lineNumber;

    // token
    private Token token;

    // token列表
    private List<Token> tokens;

    // token列表
    private List<Exception> exceptions;


    // 起始下标
    private int start;

    // 结束下标
    private int end;

    // 过滤链
    private Chain chain;

    // 策略
    private StrategyCilent strategyCilent;


    public Line(String val, int lineNumber) {
        this.val = val;
        this.lineNumber = lineNumber;
        this.tokens = new ArrayList<>();
        this.exceptions = new ArrayList<>();
        this.start = 0;
        this.end = val.length();
        this.lineNumber = lineNumber;
        this.chain = ChainBuilder.builder();
        this.token = new Token(lineNumber);
        this.strategyCilent = new StrategyCilent();
        this.strategyCilent.setStrategy(this.strategyCilent);
    }

    // 识别一行
    public List<Token> analysisLine(Line line){
        while (start < end){
            // 获取当前状态
            int state = getCurState();
            // 根据状态转换图进行抉择
            switch (state){
                case -1: break;
                case 0: pass();break;
                case 1: strategyCilent.recognition(1,line);break;
                case 2: strategyCilent.recognition(2,line);break;
                case 3: strategyCilent.recognition(3,line);break;
                case 4: strategyCilent.recognition(4,line);break;
                case 5: strategyCilent.recognition(5,line);break;
                case 6: strategyCilent.recognition(6,line);break;
            }
        }
        return tokens;
    }

    public void pass(){
        if (isEnd()) return;
        start++;
    }

    public boolean isEnd(){
        return start >= end;
    }

    public char getCurChar(){
        if (isEnd()) return '#';
        return val.charAt(start);
    }


    public boolean addChar(){
        if (isEnd()) return false;
        this.token.addChar(val.charAt(start++));
        return true;
    }

    public void addToken(){
        tokens.add(token);
        token = new Token(lineNumber);
    }

    public void addException(Exception e){
        exceptions.add(e);
    }

    /**
     * 获取当前状态
     * @return
     */
    public int getCurState(){
        if (start == end) return -1;
        return chain.getState(token,val.charAt(start));
    }

}

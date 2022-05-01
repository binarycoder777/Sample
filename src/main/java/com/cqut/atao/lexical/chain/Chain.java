package com.cqut.atao.lexical.chain;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.token.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Chain.java
 * @Description TODO
 * @createTime 2022年04月19日 11:18:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Chain {

    // 状态码
    private int state;

    // 下一个过滤节点
    private Chain nextChain;

    public Chain(int state) {
        this.state = state;
    }

    public final int getState(Token t, char c){
        if (judge(c)) return state;
        if (nextChain == null) throw new ParseException(t);
        return nextChain.getState(t,c);
    }

    public abstract boolean judge(char c);

}

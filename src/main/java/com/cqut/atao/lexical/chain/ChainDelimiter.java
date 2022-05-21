package com.cqut.atao.lexical.chain;

import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainDelimiter.java
 * @Description 界符过滤
 * @createTime 2022年04月19日 20:02:00
 */
@NoArgsConstructor
public class ChainDelimiter extends Chain {

    public ChainDelimiter(int state, Chain nextChain) {
        super(state, nextChain);
    }

    @Override
    public boolean judge(char c) {
        return c == '{' || c == '}' || c == ',' || c == ';' || c == '[' || c == ']' || c == '(' || c == ')';
    }


    public static boolean judgeChar(char c) {
        return c == '{' || c == '}' || c == ',' || c == ';' || c == '[' || c == ']' || c == '(' || c == ')';
    }
}

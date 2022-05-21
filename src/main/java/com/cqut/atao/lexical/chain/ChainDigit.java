package com.cqut.atao.lexical.chain;

import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainDigit.java
 * @Description 数字过滤
 * @createTime 2022年04月19日 20:01:00
 */
@NoArgsConstructor
public class ChainDigit extends Chain {

    public ChainDigit(int state, Chain nextChain) {
        super(state, nextChain);
    }

    @Override
    public boolean judge(char c) {
        return Character.isDigit(c);
    }
    public static boolean judgeDigit(char c) {
        return Character.isDigit(c);
    }
}

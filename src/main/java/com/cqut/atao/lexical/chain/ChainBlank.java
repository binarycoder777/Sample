package com.cqut.atao.lexical.chain;

import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainBlank.java
 * @Description 空格过滤
 * @createTime 2022年04月19日 11:55:00
 */
@NoArgsConstructor
public class ChainBlank extends Chain {

    public ChainBlank(int state, Chain nextChain) {
        super(state, nextChain);
    }

    @Override
    public boolean judge(char c) {
        return c == ' ' || c == '\n';
    }


    public static boolean judgeChar(char c) {
        return c == ' ' || c == '\n';
    }
}

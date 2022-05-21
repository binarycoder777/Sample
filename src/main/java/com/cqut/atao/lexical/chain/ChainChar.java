package com.cqut.atao.lexical.chain;

import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainChar.java
 * @Description 字符过滤
 * @createTime 2022年04月19日 20:02:00
 */
@NoArgsConstructor
public class ChainChar extends Chain {

    public ChainChar(int state, Chain nextChain) {
        super(state, nextChain);
    }

    @Override
    public boolean judge(char c) {
        return c == '\'';
    }
}

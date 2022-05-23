package com.cqut.atao.lexical.chain;

import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainIdentifier.java
 * @Description 标识符过滤
 * @createTime 2022年04月19日 16:24:00
 */
@NoArgsConstructor
public class ChainIdentifier extends Chain {

    public ChainIdentifier(int state, Chain nextChain) {
        super(state, nextChain);
    }

    @Override
    public boolean judge(char c) {
        return Character.isAlphabetic(c) || c == '_' || c == '$';
    }

    public static boolean judgeChar(char c){
        return Character.isAlphabetic(c) || c == '_' || c == '$';
    }
}

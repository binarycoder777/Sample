package com.cqut.atao.lexical.chain;

import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainOprator.java
 * @Description 运算符过滤
 * @createTime 2022年04月19日 20:01:00
 */
@NoArgsConstructor
public class ChainOprator extends Chain {

    public ChainOprator(int state, Chain nextChain) {
        super(state, nextChain);
    }

    @Override
    public boolean judge(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '!' || ch == '=' || ch == '^' || ch == '~' || ch == '|' || ch == '&' || ch == '>' || ch == '<';
    }

    public static boolean judgeChar(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%' || ch == '!' || ch == '=' || ch == '^' || ch == '~' || ch == '|' || ch == '&' || ch == '>' || ch == '<';
    }
}

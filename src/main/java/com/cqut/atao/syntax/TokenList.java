package com.cqut.atao.syntax;

import com.cqut.atao.token.Token;
import lombok.Data;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TokenList.java
 * @Description TODO
 * @createTime 2022年05月01日 13:50:00
 */
@Data
public class TokenList<T>{

    private int index;

    private List<T> tokens;

    public TokenList(List<T> tokens) {
        this.index = 0;
        this.tokens = tokens;
    }

    // 是否结束
    public boolean isEnd(){
        if (index < tokens.size()) return false;
        return true;
    }

    // 获取下一个token
    public T getNextToken(){
        if (isEnd()) return null;
        return tokens.get(index++);
    }

}

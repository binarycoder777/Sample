package com.cqut.atao.syntax;

import com.cqut.atao.token.Token;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TokenList.java
 * @Description TODO
 * @createTime 2022年05月01日 13:50:00
 */
@Data
public class TokenList<T> implements Serializable {

    private Integer index;

    private List<T> tokens;

    public TokenList() {

    }

    public TokenList(List<T> tokens) {
        this.index = 0;
        this.tokens = tokens;
    }

    // 是否结束
    public boolean isEnd(){
        return index >= (tokens.size() - 1);
    }

    // 获取下一个token
    public T getNextToken(){
        if (index >= tokens.size()) return null;
        return tokens.get(index++);
    }

    public T getCurToken(){
        if (index >= tokens.size()) return null;
        return tokens.get(index);
    }

    public T getPreToken(){
        return index == 0 ? tokens.get(0) : tokens.get(index-1);
    }

    public void match(){
        ++index;
    }
}

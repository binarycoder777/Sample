package com.cqut.atao.syntax.strategy;

import com.cqut.atao.exception.MyException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.token.Token;

import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName StrategyClient.java
 * @Description TODO
 * @createTime 2022年05月01日 14:54:00
 */
public class StrategyClient {

    private Map<Integer,MyStrategy> map;

    public StrategyClient(Map<Integer, MyStrategy> map) {
        this.map = map;
        map.put(0,new ConstStrategy());
        map.put(1,new TypeStrategy());
        map.put(2,new MainStrategy());
    }

    public void recognition(int type, TokenList<Token> tokens, List<MyException> exceptions){
        map.get(type).recognition(tokens,exceptions);
    }

}

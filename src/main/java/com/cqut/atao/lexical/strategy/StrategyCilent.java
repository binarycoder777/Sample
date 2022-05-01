package com.cqut.atao.lexical.strategy;

import com.cqut.atao.lexical.Line;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName StrategyCilent.java
 * @Description TODO
 * @createTime 2022年04月19日 21:13:00
 */
@AllArgsConstructor
@Data
public class StrategyCilent {

//    private MyStrategy strategy;

    private Map<Integer,MyStrategy> map;

    public StrategyCilent() {
        this.map = new HashMap<>();
    }

    public void setStrategy(StrategyCilent cilent){
        MyStrategy strategy1 = new IdentifierStrategy();
        MyStrategy strategy2 = new NumberStrategy();
        MyStrategy strategy3 = new OperatorStrategy(cilent);
        MyStrategy strategy4 = new CharStrategy();
        MyStrategy strategy5 = new StrStrategy();
        MyStrategy strategy6 = new DelimiterStrategy();
        this.map.put(1,strategy1);
        this.map.put(2,strategy2);
        this.map.put(3,strategy3);
        this.map.put(4,strategy4);
        this.map.put(5,strategy5);
        this.map.put(6,strategy6);
    }

    public void recognition(int type, Line line){
        map.get(type).recognition(line);
    }

}

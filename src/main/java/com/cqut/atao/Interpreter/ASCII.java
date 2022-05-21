package com.cqut.atao.Interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ASCll.java
 * @Description 含有转义字符的 ASCII 码表
 * @createTime 2022年05月21日 16:55:00
 */
public class ASCII {
    static Map<String, Integer> map;

    public ASCII () {
        map = new HashMap<>();
        map.put("\\n", 10);
        map.put("\\r", 13);
        map.put("\\t", 9);
        map.put("\\b", 8);
        map.put("\\f", 12);
        map.put("\\'", 39);
        map.put("\"", 34);
        map.put("\\", 92);
    }
}

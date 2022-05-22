package com.cqut.atao.lexical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Text.java
 * @Description TODO
 * @createTime 2022年04月19日 09:35:00
 */
@Data
@NoArgsConstructor
public class Text {

    public static Queue<Line> parse(String text){
        int count = 1;
        Queue<Line> res = new LinkedList<>();
        String[] lines = text.trim().split("\n");
        for (String l: lines){
            l = l.trim();
            if (l.equals("")){
                continue;
            }
            Line line = new Line(l,count++);
            res.add(line);
        }
        return res;
    }

}

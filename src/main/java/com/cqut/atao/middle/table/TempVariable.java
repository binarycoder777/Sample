package com.cqut.atao.middle.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TempVariable.java
 * @Description TODO
 * @createTime 2022年05月19日 22:19:00
 */
@Data
@AllArgsConstructor
public class TempVariable {


//    private Integer id;

    private String val;

    private int TC;

    private int FC;

    public TempVariable(String val) {
        this.val = val;
    }

    public TempVariable() {

    }


}

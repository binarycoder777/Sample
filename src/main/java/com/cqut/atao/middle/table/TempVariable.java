package com.cqut.atao.middle.table;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TempVariable.java
 * @Description 临时变量
 * @createTime 2022年05月19日 22:19:00
 */
@Data
@AllArgsConstructor
public class TempVariable {

    // 名称
    private String val;

    // 值
    private String res;

    // 类型
    private String type;

    // 真出口
    private int TC;

    // 假出口
    private int FC;

    public TempVariable(String val) {
        this.val = val;
    }

    public TempVariable() {

    }


}

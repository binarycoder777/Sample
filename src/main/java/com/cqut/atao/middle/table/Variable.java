package com.cqut.atao.middle.table;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName variableTable.java
 * @Description 变量符号表
 * @createTime 2022年05月16日 10:43:00
 */
@Data
@AllArgsConstructor
public class Variable {

    // 变量类型
    private String type;

    // 变量名
    private String name;

    // 变量值
    private String val;

    // 作用域
    private Integer socp;

    // 真出口
    private int TC;

    // 假出口
    private int FC;

    public Variable() {

    }

    public Variable(TempVariable variable) {
        this.name = variable.getVal();
        this.val = variable.getVal();
        this.type = variable.getType();
    }

    public Variable(int socp) {
        this.socp = socp;
    }

    public Variable(String type) {
        this.type = type;
    }


    @Override
    public int hashCode() {
        return Objects.hash(type, name, val, socp);
    }
}

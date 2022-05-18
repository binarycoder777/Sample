package com.cqut.atao.middle.table;

import lombok.Data;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName variableTable.java
 * @Description 变量符号表
 * @createTime 2022年05月16日 10:43:00
 */
@Data
public class Variable {

    // 入口
    private Integer id;

    // 变量名
    private String name;

    // 变量类型
    private String type;

    // 变量值
    private String val;


}

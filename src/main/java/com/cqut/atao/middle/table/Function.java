package com.cqut.atao.middle.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName variableTable.java
 * @Description 函数符号表
 * @createTime 2022年05月16日 10:43:00
 */
@Data
public class Function {

    // 函数返回类型
    private String returnType;

    // 函数名
    private String functionName;

    // 参数名
    private List<String> parameterType;

    public Function() {
        parameterType = new ArrayList<>();
    }
}

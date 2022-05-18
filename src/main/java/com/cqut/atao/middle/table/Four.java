package com.cqut.atao.middle.table;

import lombok.Data;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Four.java
 * @Description 四元式
 * @createTime 2022年05月17日 16:31:00
 */
@Data
public class Four {

    // 编号
    private String id;

    // 运算符
    private String op;

    // 运算值1
    private String num1;

    // 运算值2
    private String num2;

    // 运算结果
    private String result;

    public Four(String op, String num1, String num2, String result) {
        this.op = op;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
    }

    public Four() {
    }

    public Four(String id, String op, String num1, String num2, String result) {
        this.id = id;
        this.op = op;
        this.num1 = num1;
        this.num2 = num2;
        this.result = result;
    }
}

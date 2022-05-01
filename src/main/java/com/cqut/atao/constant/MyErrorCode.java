package com.cqut.atao.constant;

import lombok.Data;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MyErrorCode.java
 * @Description TODO
 * @createTime 2022年05月01日 13:52:00
 */
public enum MyErrorCode {

    SYNTAX_ERROR("100","语法错误");

    private String info;

    private String code;

    MyErrorCode(String info, String code) {
        this.info = info;
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

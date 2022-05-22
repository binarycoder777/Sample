package com.cqut.atao.lexical.chain;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ChainBuilder.java
 * @Description 构造器
 * @createTime 2022年04月19日 20:06:00
 */
public class ChainBuilder {

    public static Chain builder(){
        Chain delimiter = new ChainDelimiter(6,null);
        Chain str = new ChainStr(5,delimiter);
        Chain ch = new ChainChar(4,str);
        Chain oprator = new ChainOprator(3,ch);
        Chain digit = new ChainDigit(2,oprator);
        Chain identifier = new ChainIdentifier(1,digit);
        Chain blank = new ChainBlank(0,identifier);
        return blank;
    }

}

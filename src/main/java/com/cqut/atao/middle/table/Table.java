package com.cqut.atao.middle.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Table.java
 * @Description 符号表
 * @createTime 2022年05月16日 18:18:00
 */
@Data
public class Table <A,B,C> {

    private List<A> constTable = new ArrayList<>();

    private List<B> varTable = new ArrayList<>();

    private List<C> functionTable = new ArrayList<>();

    public void addConst(A a){
        constTable.add(a);
    }

    public void addVar(B b){
        varTable.add(b);
    }

    public void addFun(C c){
        functionTable.add(c);
    }

}

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

    private List<Const> constTable = new ArrayList<>();

    private List<Variable> varTable = new ArrayList<>();

    private List<Function> functionTable = new ArrayList<>();

    public void addConst(Const a){
        constTable.add(a);
    }

    public void addVar(Variable b){
        if (b.getType() != null){
            varTable.add(b);
        }
    }

    public void addFun(Function c){
        functionTable.add(c);
    }

    public String getTable(){
        String c = "常量表:";
        for (Const con: constTable){
            c += "\n"+con.toString();
        }
        String v = "变量表:";
        for (Variable var: varTable){
            v += "\n"+var.toString();
        }
        String f = "函数表:";
        for (Function fun: functionTable){
            f += "\n"+fun.toString();
        }
        return c + "\n" + v + "\n" + f + "\n";
    }

}

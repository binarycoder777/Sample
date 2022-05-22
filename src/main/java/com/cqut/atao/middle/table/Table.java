package com.cqut.atao.middle.table;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Table.java
 * @Description 符号表
 * @createTime 2022年05月16日 18:18:00
 */
@Data
public class Table {


    private List<Const> constTable = new ArrayList<>();

    // K:函数名 V:函数
    private Map<String,Function> functionTable = new HashMap<>();

    public void addConst(Const a){
        constTable.add(a);
    }

    // 添加变量到函数的变量表
    public void addVar(String functionName,Variable b){
        if (functionTable.containsKey(functionName)){
            functionTable.get(functionName).addVar(b);
        }
    }

    // 添加参数到函数的参数列表
    public void addPara(String functionName,Variable b){
        if (functionTable.containsKey(functionName)){
            functionTable.get(functionName).addPara(b);
        }
    }

    public void addFun(Function c){
        functionTable.put(c.getFunctionName(),c);
    }

    public String getTable(){
        String c = "常量表:";
        for (Const con: constTable){
            c += "\n"+con.toString();
        }
        String f = "函数表:";
        for (Function fun: functionTable.values()){
            f += "\n"+fun.toString();
        }
        return c + "\n" + f + "\n";
    }

}

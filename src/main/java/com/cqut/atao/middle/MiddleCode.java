package com.cqut.atao.middle;

import com.cqut.atao.middle.table.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MiddleCode.java
 * @Description 中间代码对象
 * @createTime 2022年05月16日 18:15:00
 */
@Data
public class MiddleCode {

    // 四元式指针
    private int NXQ = 0;

    // 临时变量ID
    private int tmpID = 0;

    // 符号表
    private Table table = new Table();

    // 四元式表
    private List<Four> fourTable = new ArrayList<>();

    // 临时表
    private Map<String,TempVariable> tmpTable = new HashMap<>();


    // 生成四元表达式
    public void gencode(String functionName,String op,String arg1,String arg2,String result){
        if (tmpTable.containsKey(result)){
            Function function  = table.getFunctionTable().get(functionName);
            Variable variable = new Variable();
            variable.setName(result);
            variable.setVal(result);
            if (function.getVarTable().containsKey(arg1)){
                variable.setType(function.getVarTable().get(arg1).getType());
            }
            function.addVar(variable);
        }
        fourTable.add(new Four((NXQ++),op,arg1,arg2,result));
    }

    // 生成一个临时变量
    public TempVariable newtemp(){
        TempVariable var = new TempVariable();
        var.setTC(NXQ);
        var.setFC(NXQ);
        var.setVal("T"+(++tmpID));
        tmpTable.put(var.getVal(),var);
        return var;
    }


    // 添加临时变量
    public void addTemp(TempVariable variable){
        tmpTable.put(variable.getVal(),variable);
    }

    // 获取临时变量
    public TempVariable getTemp(String key){
        if (tmpTable.containsKey(key)){
            return tmpTable.get(key);
        }
        return null;
    }

    public void buckpatch(int key,int NXQ){
        Four four = fourTable.get(key);
        four.setResult((NXQ)+"");
    }

    public void merge(int k1, int k2){
        Four p1 = fourTable.get(k1);
        Four p2 = fourTable.get(k2);
        p2.setResult(p1.getId()+"");
    }



}
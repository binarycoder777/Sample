package com.cqut.atao.middle;

import com.cqut.atao.middle.table.*;
import com.cqut.atao.syntax.tree.MyTree;
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
    private Table<Const, Variable, Function> table = new Table<>();

    // 四元式表
    private List<Four> fourTable = new ArrayList<>();

    // 临时变量表
    private Map<Integer,Variable> tempTable = new HashMap<>();


    // 生成四元表达式
    public void gencode(String op,String arg1,String arg2,String result){
        fourTable.add(new Four((NXQ++)+"",op,arg1,arg2,result));
    }

    // 生成一个临时变量
    public Variable newtemp(){
        Variable var = new Variable();
        var.setId(tmpID);
        tempTable.put(tmpID++,var);
        return var;
    }

    // 获取临时变量
    public String gettemp(String key){
        if (!tempTable.containsKey(key)){
            return null;
        }
        return tempTable.get(key).toString();
    }

}

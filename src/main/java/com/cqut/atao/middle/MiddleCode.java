package com.cqut.atao.middle;

import com.cqut.atao.middle.table.*;
import com.cqut.atao.syntax.tree.MyTree;
import lombok.Data;
import org.aspectj.weaver.ast.Var;

import java.util.*;

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


    // 生成四元表达式
    public void gencode(String op,String arg1,String arg2,String result){
        fourTable.add(new Four((NXQ++),op,arg1,arg2,result));
    }

    // 生成一个临时变量
    public TempVariable newtemp(){
        TempVariable var = new TempVariable();
        var.setTC(NXQ);
        var.setFC(NXQ);
        var.setVal("T"+(++tmpID));
        return var;
    }

    // 生成一个临时变量
    public TempVariable newtempOfNO(){
        TempVariable var = new TempVariable();
        var.setTC(NXQ);
        var.setFC(NXQ);
        return var;
    }

    public int getTempID(){
        return ++tmpID;
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

    public void fillVar(String key,String val){
        List<Variable> list = table.getVarTable();
        for(Variable v: list){
            if (key.equals(v.getName())){
                v.setVal(val);
                break;
            }
        }
    }


}
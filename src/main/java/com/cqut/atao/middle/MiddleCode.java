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
    private Map<String,Four> fourTable = new HashMap<>();

    // 临时变量表
    private Map<Integer,TempVariable> tempTable = new LinkedHashMap<Integer,TempVariable>(100,0.75f,true);


    // 生成四元表达式
    public void gencode(String key,String op,String arg1,String arg2,String result){
        fourTable.put(key,new Four((NXQ++),op,arg1,arg2,result));
    }

    // 生成一个临时变量
    public TempVariable newtemp(){
        TempVariable var = new TempVariable();
        var.setId(++tmpID);
        var.setVal("T"+tmpID);
        tempTable.put(tmpID,var);
        return var;
    }

    public int getTempID(){
        return ++tmpID;
    }


    public String entryOfVarByName(String name){
        for (Variable var: table.getVarTable()) {
            if (var.getName().equals(name)){
                return var.getId();
            }
        }
        return null;
    }

    public void buckpatch(String key,int NXQ){
        Four four = fourTable.get(key);
        four.setResult((NXQ)+"");
    }

    public void merge(String k1, String k2){
        Four p1 = fourTable.get(k1);
        Four p2 = fourTable.get(k2);
        System.out.println(p1.getId()+" "+p2.getId());
        p2.setResult(p1.getId()+"");
    }

    public List<Four> getFourTable(){
        List<Four> list = new ArrayList<>();
        for(Four four: fourTable.values()){
            list.add(four);
        }
        Collections.sort(list,(a,b)->{
            return a.getId() - b.getId();
        });
        return list;
    }

}

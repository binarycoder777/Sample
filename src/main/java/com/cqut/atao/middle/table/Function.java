package com.cqut.atao.middle.table;

import lombok.Data;
import org.aspectj.weaver.ast.Var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName variableTable.java
 * @Description 函数符号表
 * @createTime 2022年05月16日 10:43:00
 */
@Data
public class Function {

    // 起始位置
    private int position;

    // 函数返回类型
    private String returnType;

    // 函数名
    private String functionName;

    // 参数类型
    private List<String> parameterType = new ArrayList<>();

    // 参数列表
    public List<String> vars = new ArrayList<>();

    // 参数表(K:参数名， V：变量)
    public Map<String, Variable> args = new HashMap<>();

    // 变量表:K:变量名 V:变量
    private Map<String,Variable> varTable = new HashMap<>();

    // 临时变量表:K:变量名 V:变量
    private Map<String,Variable> tmpTable = new HashMap<>();



    // 添加变量到变量表
    public void addVar(Variable b){
        if (b.getType() != null){
            String key = b.getName();
            varTable.put(key,b);
        }
    }

    public void fillVars(){
        for (String s: args.keySet()){
            vars.add(s);
        }
        for (String s: varTable.keySet()){
            vars.add(s);
        }
        for (String s: tmpTable.keySet()){
            vars.add(s);
        }
    }

    public Variable getVarInfo(String id) {
        if (args.containsKey(id))
            return args.get(id);
        if (varTable.containsKey(id))
            return varTable.get(id);
        if (tmpTable.containsKey(id))
            return tmpTable.get(id);
        return null;
    }

    @Override
    public String toString() {
        return "Function{" +
                "position=" + position + '\n' +
                ", returnType='" + returnType + '\n' +
                ", functionName='" + functionName + '\n' +
                ", parameterType=" + parameterType + '\n' +
                ", args=" + args + '\n' +
                ", varTable=" + varTable + '\n' +
                ", tmpTable=" + tmpTable + '\n' +
                '}' + '\n';
    }
}

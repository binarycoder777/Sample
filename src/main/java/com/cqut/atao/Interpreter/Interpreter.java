package com.cqut.atao.Interpreter;

import com.cqut.atao.middle.table.Four;
import com.cqut.atao.middle.table.Function;
import com.cqut.atao.middle.table.Variable;
import javafx.scene.control.TextArea;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Interpreter.java
 * @Description 解释器
 * @createTime 2022年05月21日 16:42:00
 */
@Data
public class Interpreter {

    private Function fun;				// 函数信息表

    private Activity curActivity;			// 当前活动

    private List<Variable> varStack;	    // 函数变量栈

    private List<Activity> activityStack;	// 活动记录栈

    private String returnValue;					// 返回值

    private TextArea textArea;              // 文本域

    private StringBuilder buffer;               // 输入和输出缓冲区

    private List<String> paraList;          // 参数列表


    public Interpreter(TextArea text) {
        new ASCII();
        this.textArea = text;
    }

    // 解释程序
    public void interpreter(Map<String, Function> funcsInfoMap, List<Four> fourItemList) {
        // 初始化
        buffer = new StringBuilder();
        activityStack = new ArrayList<>();
        varStack = new ArrayList<>();
        paraList = new ArrayList<>();
        // main函数入栈
        activityStack.add(new Activity("main", 0, funcsInfoMap.get("main").getPosition()-1));
        // 当前活动栈
        curActivity = activityStack.get(0);
        // main函数的函数信息
        fun = funcsInfoMap.get("main");
        fun.fillVars();
        // 将 main 函数所有变量压栈
        for (String var : fun.vars) {
//            String type = fun.getVarInfo(var).getType();
            varStack.add(fun.getVarInfo(var));
        }
        // 解释执行
        while (!activityStack.isEmpty() && curActivity.now < fourItemList.size()) {
            // 当前四元式
            Four fourItem = fourItemList.get(curActivity.now++);
            // 运算符
            String op = fourItem.getOp();
            // 参数1
            String arg1Val = getVal(fourItem.getNum1());
            // 参数2
            String arg2Val = getVal(fourItem.getNum2());
            // 存储结果
            String result = fourItem.getResult();
            // 结果类型
            String resultType = getType(fourItem.getNum1());
            // 结果值
            String resultVal = getVal(result);
            // 假
            String f = "0";
            // 真
            String t = "1";
            // 解释执行
            if (!"para".equals(op) && !"call".equals(op)) paraList.clear();
            switch(op) {
                case "+" :
                    if (resultType.equals("int")) {
                        setVal(result, (Integer.parseInt(arg1Val) + Integer.parseInt(arg2Val)) + "");
                    } else if (resultType.equals("float")) {
                        setVal(result, (Float.parseFloat(arg1Val) + Float.parseFloat(arg2Val)) + "");
                    } else if (resultType.equals("char")) {
                        setVal(result, (Integer.parseInt(arg1Val) + Integer.parseInt(arg2Val)) + "");
                    }
                    break;
                case "-" :
                    if (resultType.equals("int")) {
                        setVal(result, (Integer.parseInt(arg1Val) - Integer.parseInt(arg2Val)) + "");
                    } else if (resultType.equals("float")) {
                        setVal(result, (Float.parseFloat(arg1Val) - Float.parseFloat(arg2Val)) + "");
                    } else if (resultType.equals("char")) {
                        setVal(result, (Integer.parseInt(arg1Val) - Integer.parseInt(arg2Val)) + "");
                    }
                    break;
                case "*" :
                    if (resultType.equals("int")) {
                        setVal(result, (Integer.parseInt(arg1Val) * Integer.parseInt(arg2Val)) + "");
                    } else if (resultType.equals("float")) {
                        setVal(result, (Float.parseFloat(arg1Val) * Float.parseFloat(arg2Val)) + "");
                    } else if (resultType.equals("char")) {
                        setVal(result, (Integer.parseInt(arg1Val) * Integer.parseInt(arg2Val)) + "");
                    }
                    break;
                case "/" :
                    if (resultType.equals("int")) {
                        setVal(result, (Integer.parseInt(arg1Val) / Integer.parseInt(arg2Val)) + "");
                    } else if (resultType.equals("float")) {
                        setVal(result, (Float.parseFloat(arg1Val) / Float.parseFloat(arg2Val)) + "");
                    } else if (resultType.equals("char")) {
                        setVal(result, (Integer.parseInt(arg1Val) / Integer.parseInt(arg2Val)) + "");
                    }
                    break;
                case "%" :
                    setVal(result, (Integer.parseInt(arg1Val) % Integer.parseInt(arg2Val)) + "");
                    break;
                case "&" :
                    setVal(result, (Integer.parseInt(arg1Val) & Integer.parseInt(arg2Val)) + "");
                    break;
                case "|" :
                    setVal(result, (Integer.parseInt(arg1Val) | Integer.parseInt(arg2Val)) + "");
                    break;
                case "^" :
                    setVal(result, (Integer.parseInt(arg1Val) ^ Integer.parseInt(arg2Val)) + "");
                    break;
                case "<" :
                    if (Float.parseFloat(arg1Val) < Float.parseFloat(arg2Val))
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case ">" :
                    if (Float.parseFloat(arg1Val) > Float.parseFloat(arg2Val))
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "=" :
                    setVal(result, arg1Val);
                    break;
                case "<=" :
                    if (Float.parseFloat(arg1Val) <= Float.parseFloat(arg2Val))
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case ">=" :
                    if (Float.parseFloat(arg1Val) >= Float.parseFloat(arg2Val))
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "!=" :
                    if (Float.parseFloat(arg1Val) != Float.parseFloat(arg2Val))
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "==" :
                    if (Float.parseFloat(arg1Val) == Float.parseFloat(arg2Val))
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "&&" :
                    if (Float.parseFloat(arg1Val) != 0 && Float.parseFloat(arg2Val) != 0)
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "||" :
                    if (Float.parseFloat(arg1Val) != 0 || Float.parseFloat(arg2Val) != 0)
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "~" :
                    setVal(result, (~Integer.parseInt(arg1Val)) + "");
                    break;
                case "!" :
                    if (Float.parseFloat(arg1Val) == 0)
                        setVal(result, t);
                    else
                        setVal(result, f);
                    break;
                case "j<" :
                    if (Integer.parseInt(arg1Val) < Integer.parseInt(arg2Val)){
                        curActivity.now = Integer.parseInt(result);
                    }
                    break;
                case "j==" :
                    if (Integer.parseInt(arg1Val) == Integer.parseInt(arg2Val)){
                        curActivity.now = Integer.parseInt(result);
                    }
                    break;
                case "j!=" :
                    if (Integer.parseInt(arg1Val) != Integer.parseInt(arg2Val)){
                        curActivity.now = Integer.parseInt(result);
                    }
                    break;
                case "j" :
                    curActivity.now = Integer.parseInt(result);
                    break;
                case "para":
                    paraList.add(arg1Val);
                    break;
                case "ret" :
                    returnValue = getVal(result);
                    for (int i = 0; i < fun.getVarTable().values().size(); i++)
                        varStack.remove(varStack.size()-1);
                    activityStack.remove(activityStack.size()-1);
                    if (!activityStack.isEmpty()) {
                        curActivity = activityStack.get(activityStack.size()-1);
                        fun = funcsInfoMap.get(curActivity.func);
                    }
                    break;
                case "call":
                    // 系统调用打印结果
                    if ("write".equals(arg1Val)){
                        textArea.setText(paraList.toString());
                    }else if (!curActivity.flag) {  // 自定义函数调用
                        curActivity.now--;
                        curActivity.flag = true;
                        // 获取实参
                        String[] args = new String[paraList.size()];
                        for (int i=0;i<paraList.size();++i){
                            args[i] = paraList.get(i);
                        }
                        for (int i = 0; i < args.length; i++) {
                            args[i] = getVal(args[i]);
                        }
                        // 替换形参
                        int tmpK = 0;
                        Map<String,Variable> variables = funcsInfoMap.get(arg1Val).getArgs();
                        for(Variable variable: variables.values()){
                            variable.setVal(args[tmpK++]);
                        }
                        // 压栈
                        activityStack.add(new Activity(arg1Val, varStack.size(), funcsInfoMap.get(arg1Val).getPosition() - 1));
                        curActivity = activityStack.get(activityStack.size() - 1);
                        curActivity.start = varStack.size();
                        fun = funcsInfoMap.get(arg1Val);
                        fun.fillVars();
                        for (String var : fun.vars) {
                            Variable v = fun.getVarInfo(var);
                            varStack.add(v);
                        }
                    } else {
                        curActivity.flag = false;
                        setVal(result, returnValue);
                    }
                    break;
            }
        }
    }

    // 根据参数名获取变量值
    private String getVal(String id) {
        if (id.isEmpty())
            return id;
        if (fun.vars.contains(id)) {
            int index = fun.vars.indexOf(id);
            return varStack.get(curActivity.start + index).getVal();
        } else if (id.charAt(0) == '\'') {
            id = id.substring(1, id.length()-1);
            if (ASCII.map.containsKey(id))
                return ASCII.map.get(id) + "";
            else
                return (int)id.charAt(0) + "";
        }
        return id;
    }

    // 获取类型
    private String getType(String id) {
        if (id.isEmpty())
            return id;
        if (fun.vars.contains(id)) {
            int index = fun.vars.indexOf(id);
            return varStack.get(curActivity.start + index).getType();
        } else if (id.charAt(0) == '\'') {
            return "char";
        } else if (id.charAt(0) == '\"') {
            return "string";
        } else {
            return null;
        }
    }

    // 设置值
    private void setVal(String id, String val) {
        int index = fun.vars.indexOf(id);
        varStack.get(curActivity.start + index).setVal(val);
    }

}

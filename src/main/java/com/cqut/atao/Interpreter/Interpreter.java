package com.cqut.atao.Interpreter;

import com.cqut.atao.middle.table.Four;
import com.cqut.atao.middle.table.Function;
import com.cqut.atao.middle.table.Variable;
import javafx.scene.control.TextArea;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.String;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Interpreter.java
 * @Description 解释器
 * @createTime 2022年05月21日 16:42:00
 */
@Data
public class Interpreter {

    private Function funcInfo;				// 函数信息表
    private Activity curActivity;			// 当前活动
    private List<Variable> varStack;	    // 函数变量栈
    private List<Activity> activityStack;	// 活动记录栈
    private String retVal;					// 返回值
    private TextArea textArea;              // 文本域
    private StringBuilder sb;               // 输入和输出缓冲区

    public Interpreter (TextArea text) {
        new ASCII();
        this.textArea = text;
    }

    // 解释程序
    public void interpreter(Map<String, Function> funcsInfoMap, List<Four> fourItemList) {
        // 初始化
        sb = new StringBuilder();
        activityStack = new ArrayList<>();
        varStack = new ArrayList<>();
        // main函数入栈
        activityStack.add(new Activity("main", 0, funcsInfoMap.get("main").getPosition()-1));
        // 当前活动栈
        curActivity = activityStack.get(0);
        // main函数的函数信息
        funcInfo = funcsInfoMap.get("main");
        funcInfo.fillVars();
        // 将 main 函数所有变量压栈
        for (String var : funcInfo.vars) {
            String type = funcInfo.getVarInfo(var).getType();
            varStack.add(new Variable(type));
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
            String resultType = getType(fourItem.getResult());
            // 结果值
            String resultVal = getVal(result);
            // 假
            String f = "0";
            // 真
            String t = "1";
            // 解释执行
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
                case "j" :
                    curActivity.now = Integer.parseInt(result);
                    break;
                case "jnz" :
                    if (arg1Val.equals("1"))
                        curActivity.now = Integer.parseInt(result) - 1;
                    break;
                case "jz" :
                    if (arg1Val.equals("0"))
                        curActivity.now = Integer.parseInt(result) - 1;
                    break;
                case "print" :
                    if (resultType.equals("char"))
                        resultVal = (char)Integer.parseInt(resultVal) + "";
                    else if (resultType.equals("string"))
                        resultVal = resultVal.substring(1, resultVal.length()-1);
//                    textArea.append(resultVal + "\n");
                    sb = new StringBuilder(textArea.getText());
                    textArea.setText(sb.toString());
                    break;
                case "scan" :
                    String input = textArea.getText().substring(sb.length());
                    while (input.isEmpty() || !input.contains("\n")) {
                        input = textArea.getText().substring(sb.length());
                    }
                    input = input.replace("\n", "");
                    if (resultType.equals("char")) {
                        if (ASCII.map.containsKey(input))
                            setVal(result, ASCII.map.get(input) + "");
                        else
                            setVal(result, (int)input.charAt(0) + "");
                    } else if (resultType.equals("string")) {
                        setVal(result, "\"" + input + "\"");
                    } else {
                        setVal(result, input);
                    }
                    sb = new StringBuilder(textArea.getText());
                    break;
                case "ret" :
                    retVal = getVal(result);
                    for (int i = 0; i < funcInfo.getVarTable().values().size(); i++)
                        varStack.remove(varStack.size()-1);
                    activityStack.remove(activityStack.size()-1);
                    if (!activityStack.isEmpty()) {
                        curActivity = activityStack.get(activityStack.size()-1);
                        funcInfo = funcsInfoMap.get(curActivity.func);
                    }
                    break;
                case "call":
                    if (!curActivity.flag) {
                        curActivity.now--;
                        curActivity.flag = true;

                        if (!arg2Val.isEmpty())
                            arg2Val = arg2Val.substring(1, arg2Val.length()-1);
                        String[] args = arg2Val.replace(" ", "").split(",");

                        for (int i = 0; i < args.length; i++) {
                            args[i] = getVal(args[i]);
                        }

                        activityStack.add(new Activity(arg1Val, varStack.size(), funcsInfoMap.get(arg1Val).getPosition() - 1));
                        curActivity = activityStack.get(activityStack.size() - 1);
                        curActivity.start = varStack.size();
                        funcInfo = funcsInfoMap.get(arg1Val);


                        for (int i = 0; i < funcInfo.getArgs().size(); i++) {
                            setVal(funcInfo.getArgs().get(i).getVal(), args[i]);
                        }

                    } else {
                        curActivity.flag = false;
                        setVal(result, retVal);
                    }
                    break;
            }
        }

        textArea.setText("运行结束\n");
    }

    private String getVal(String id) {
        if (id.isEmpty())
            return id;
        if (funcInfo.vars.contains(id)) {
            int index = funcInfo.vars.indexOf(id);
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
        if (funcInfo.vars.contains(id)) {
            int index = funcInfo.vars.indexOf(id);
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
        int index = funcInfo.vars.indexOf(id);
        varStack.get(curActivity.start + index).setVal(val);
    }

}

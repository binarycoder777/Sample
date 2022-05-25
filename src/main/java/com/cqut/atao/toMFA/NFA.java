package com.cqut.atao.toMFA;

import com.cqut.atao.toMFA.entity.FA;
import com.cqut.atao.toMFA.entity.Priority;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName NFA.java
 * @Description 无穷状态机(正规集->NFA)
 * @createTime 2022年05月22日 08:54:00
 */
public class NFA {

    // 大于
    private static final int GT = 1;
    // 小于
    private static final int LT = -1;
    // 等于
    private static final int EQ = 2;
    // 错误
    private static final int ER = 3;
    // 空
    public static final String E = "ε";
    // 操作符号
    private static List<String> operator = new ArrayList<String>();
    // 优先队列
    private static List<Priority> priorities = new ArrayList<Priority>();
    // 栈
    private static Stack<String> stack = new Stack<String>();
    // 空白符号
    public static ArrayList<String> empty = new ArrayList<String>();
    // 起始下标
    private static int FROM = 0;
    // 终止下标
    private static int TO = 0;

    // 定义正规式符号以及其优先级
    static {
        operator.add(".");
        operator.add("|");
        operator.add("(");
        operator.add(")");
        operator.add(E);

        priorities.add(new Priority(".", ".", GT));
        priorities.add(new Priority(".", "|", GT));
        priorities.add(new Priority(".", "(", LT));
        priorities.add(new Priority(".", ")", GT));
        priorities.add(new Priority(".", E, GT));

        priorities.add(new Priority("|", ".", LT));
        priorities.add(new Priority("|", "|", GT));
        priorities.add(new Priority("|", "(", LT));
        priorities.add(new Priority("|", ")", GT));
        priorities.add(new Priority("|", E, GT));

        priorities.add(new Priority("(", ".", LT));
        priorities.add(new Priority("(", "|", LT));
        priorities.add(new Priority("(", "(", LT));
        priorities.add(new Priority("(", ")", EQ));
        priorities.add(new Priority("(", E, ER));

        priorities.add(new Priority(")", ".", GT));
        priorities.add(new Priority(")", "|", GT));
        priorities.add(new Priority(")", "(", GT));
        priorities.add(new Priority(")", ")", ER));
        priorities.add(new Priority(")", E, GT));

        priorities.add(new Priority(E, ".", LT));
        priorities.add(new Priority(E, "|", LT));
        priorities.add(new Priority(E, "(", LT));
        priorities.add(new Priority(E, ")", ER));
        priorities.add(new Priority(E, E, EQ));
    }

    // 正规式->NFA
    public static List<FA> parse(String nfa) {
        List<FA> result = new ArrayList<FA>();
        // 空入栈
        stack.push(E);
        // 记录
        String ch = "";
        // 计数
        int count = 0;
        // 遍历
        for (int i = 0; i < nfa.length(); i++) {
            // 当前字符
            ch = nfa.charAt(i) + "";
            // 为空则跳过
            if (isEmpty(ch)) {
                continue;
            }
            // 判断是否是规定的正规集符号
            if (operator.contains(ch)) {
                // 获取符号优先级
                int priority = judgePriority(ch);
                // 栈顶的符号的优先级比当前低则运算符入栈，等待后面计算
                if (priority == LT) {
                    stack.push(ch);
                } else if (priority == GT) { // 反之则可以进行转换
                    String op = stack.isEmpty() ? "" : stack.pop(); // 获取当前符号
                    while (judgePriority(op) != GT) {
                        // 符号为字母，则加入结果集
                        if (isLetter(op)) {
                            result.add(new FA(count++, op, count++));
                        } else if ("|".equals(op)) {
                            // 获取栈顶两个符号的to
                            int to1 = result.get(result.size() - 1).getForm();
                            int to2 = result.get(result.size() - 2).getForm();
                            // 获取栈顶两个符号的from
                            int from1 = result.get(result.size() - 1).getTo();
                            int from2 = result.get(result.size() - 2).getTo();
                            // 进行组合运算
                            // 加入结果集
                            result.add(new FA(count, E, to1));
                            result.add(new FA(count++, E, to2));
                            // 加入结果集
                            result.add(new FA(from1, E, count));
                            result.add(new FA(from2, E, count++));
                            // 更新from和to
                            FROM = result.get(result.size() - 3).getForm();
                            TO = result.get(result.size() - 2).getTo();
                        }
                        // 弹栈
                        op = stack.pop();
                    }
                } else if (priority == EQ) {
                    // 无需处理
                    pass();
                } else if (priority == ER) {
                    // 错误
                    JOptionPane.showMessageDialog(null, "算符顺序有误", "提示", JOptionPane.ERROR_MESSAGE);
                }

            } else if ("*".equals(ch)) { // 匹配任意符号
                int from = FROM;
                int to = TO;
                // 将空加入结果集(双向)
                result.add(new FA(count++, E, from));
                result.add(new FA(to, E, count++));
                int fromw = result.get(result.size() - 1).getForm();
                int tow = result.get(result.size() - 2).getTo();
                result.add(new FA(fromw, E, tow));
                result.add(new FA(tow + 2, E, fromw + 2));
            } else { // 字母
                // from E to
                if (i != 0 && (isLetter(nfa.charAt(i-1) + "") || "*".equals(nfa.charAt(i-1) + ""))) {
                    result.add(new FA(count - 1, E, count));
                }
                result.add(new FA(count++, ch, count++));
            }
        }

        return result;
    }


    // 判断优先级
    public static int judgePriority(String op2) {
        // 为空则是目前最高优先级
        if (stack.isEmpty()) {
            return GT;
        }
        // 获取栈顶运算符
        String op1 = stack.peek();
        // 比较优先级
        for (Priority priority : priorities) {
            // 根据规则计算优先级
            if (priority.getO1().equals(op1) && priority.getO2().equals(op2)) {
                return priority.getFlag();
            }
        }
        return LT;
    }

    // 计算开始状态集
    public static int calculateStart(List<FA> nfas) {
        for (int i = 0; i < nfas.size(); i++) {
            int start = nfas.get(i).getForm();
            for (int j = 0; j < nfas.size(); j++) {
                if (start == nfas.get(j).getTo()) {
                    break;
                } else if (j == nfas.size() - 1) {
                    return start;
                }
            }
        }
        return 0;
    }

    // 计算结束状态集
    public static int calculateEnd(List<FA> nfas) {
        return nfas.get(nfas.size() - 1).getTo();
    }

    //判断是否是字母
    public static boolean isLetter(String str) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[a-zA-Z]+");
        java.util.regex.Matcher m = pattern.matcher(str);
        return m.matches();
    }

    // 判断是否是空白符号
    public static boolean isEmpty(String word) {
        return empty.contains(word);
    }

    // 空
    public static void pass(){
        return;
    }


}

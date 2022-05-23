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
 * @createTime 2022年05月23日 08:54:00
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

    public static final String E = "ε";

    private static List<String> operator = new ArrayList<String>();
    private static List<Priority> priorities = new ArrayList<Priority>();
    private static Stack<String> stack = new Stack<String>();
    public static ArrayList<String> empty = new ArrayList<String>();// 空白符号

    private static int FROM = 0;
    private static int TO = 0;

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

    public static List<FA> parse(String nfa) {
        List<FA> result = new ArrayList<FA>();

        stack.push(E);
        String ch = "";
        // 计数
        int count = 0;

        for (int i = 0; i < nfa.length(); i++) {

            ch = nfa.charAt(i) + "";
            if (isEmpty(ch)) {
                continue;
            }
            if (operator.contains(ch)) {
                // 低
                int priority = judgePriority(ch);
                if (priority == LT) {
                    // 运算符入栈
                    stack.push(ch);
                } else if (priority == GT) {
                    String op = stack.isEmpty() ? "" : stack.pop();
                    while (judgePriority(op) != GT) {
                        if (isLetter(op)) {
                            result.add(new FA(count++, op, count++));
                        } else if ("|".equals(op)) {
                            int to1 = result.get(result.size() - 1).getForm();
                            int to2 = result.get(result.size() - 2).getForm();

                            int from1 = result.get(result.size() - 1).getTo();
                            int from2 = result.get(result.size() - 2).getTo();

                            result.add(new FA(count, E, to1));
                            result.add(new FA(count++, E, to2));

                            result.add(new FA(from1, E, count));
                            result.add(new FA(from2, E, count++));

                            FROM = result.get(result.size() - 3).getForm();
                            TO = result.get(result.size() - 2).getTo();

                        } else if ("(".equals(op) && ")".equals(ch)) {
                        }
                        op = stack.pop();
                    }

                } else if (priority == EQ) {

                } else if (priority == ER) {
                    // 错误
                    JOptionPane.showMessageDialog(null, "算符顺序有误", "提示", JOptionPane.ERROR_MESSAGE);

                }

            } else if ("*".equals(ch)) {
                int from = FROM;// result.get(result.size() - 1).getForm();
                int to = TO;// result.get(result.size() - 2).getTo();

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

        if (stack.isEmpty()) {
            return GT;
        }

        String op1 = stack.peek();
        for (Priority priority : priorities) {
            if (priority.getO1().equals(op1) && priority.getO2().equals(op2)) {
                // System.out.println(op1 + ":" + op2 + ":" +
                // priority.getFlag());
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

    public static void main(String[] args) {
        List<FA> list = parse("(a|b)*b");
        for (FA nfa : list) {
            System.out.println(nfa);
        }
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


}

package com.cqut.atao.toMFA;

import com.cqut.atao.toMFA.entity.FA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName DFA.java
 * @Description 有穷状态机(NFA->DFA)
 * @createTime 2022年05月22日 08:59:00
 */
public class DFA {

    // 符号
    public static List<String> letter = new ArrayList<String>();
    // 临时存储dfa
    private static List<List<Integer>> tempdfas;

    private static List<List<Integer>> tempI;
    // 行
    private static List<List<List<Integer>>> rows = new ArrayList<List<List<Integer>>>();
    // 空白符号
    public static ArrayList<String> empty = new ArrayList<String>();
    // 空
    public static final String E = "ε";
    // 结束
    private static List<Integer> ends;

    /** NFA->DFA */
    public static List<FA> parse(List<FA> nfas) {
        // 初始化工作
        ends = new ArrayList<Integer>();
        rows.clear();
        tempdfas = new ArrayList<List<Integer>>();
        tempI = new ArrayList<List<Integer>>();
        letter = getLetter(nfas);
        List<Integer> start = getE(NFA.calculateStart(nfas), nfas);
        tempI.add(start);
        tempdfas.add(start);

        int i = 0;
        // 遍历NFA
        for (i = 0; i < tempI.size(); i++) {
            //  存储一行结果
            List<List<Integer>> row = new ArrayList<List<Integer>>();
            row.add(tempI.get(i));
            //  遍历字符
            for (int j = 0; j < letter.size(); j++) {
                // 获取闭包I
                List<Integer> tI = getI(tempI.get(i), letter.get(j), nfas);
                // 根据闭包去遍历
                List<Integer> tIE = getIE(tI, nfas);

                int k;
                for (k = 0; k < tempI.size(); k++) {
                    // 查看该闭包是否重复
                    if (equalList(tempI.get(k), tIE)) {
                        break;
                    }
                }
                // 不存在则加入临时闭包
                if (k == tempI.size()) {
                    tempI.add(tIE);
                }
                // DFA表新增一行
                row.add(tIE);

                int m = 0;
                for (m = 0; m < tempdfas.size(); m++) {
                    // 查看该闭包是否重复
                    if (equalList(tIE, tempdfas.get(m))) {
                        break;
                    }
                }
                // 不重复则加入dfa
                if (m == tempdfas.size()) {
                    tempdfas.add(tIE);
                }
            }
            rows.add(row);
        }

        // 存储结果DFA
        List<FA> dfas = new ArrayList<FA>();
        // 计算结束位置
        int end = NFA.calculateEnd(nfas);
        // 遍历表格,转为等价DFA
        for (List<List<Integer>> row : rows) {
            for (int j = 0; j < letter.size(); j++) {
                // 获取该字符所指向节点
                int to = getListIndex(row.get(j + 1));
                // 加入新节点
                dfas.add(new FA(getListIndex(row.get(0)), letter.get(j),
                        to));
                // 计算end
                if(row.get(j+1).contains(end)){
                    if(!ends.contains(to)){
                        ends.add(to);
                    }
                }
            }
        }
        return dfas;
    }

    // 判断两个list是否相等
    public static boolean equalList(List<Integer> list1, List<Integer> list2) {

        String temp1 = "";
        for (Integer o : list1) {
            temp1 += o.toString();
        }

        String temp2 = "";
        for (Integer o : list2) {
            temp2 += o.toString();
        }

        return temp1.equals(temp2);
    }

    // 获取E
    public static List<Integer> getE(int from, List<FA> nfas) {
        List<Integer> list = new ArrayList<Integer>();

        for (FA nfa : nfas) {
            if (nfa.getForm() == from && nfa.getVarch().equals(E)) {
                int to = nfa.getTo();
                if (!list.contains(to)) {
                    list.add(to);

                    list.addAll(getE(to, nfas));
                }
            }
        }

        return list;
    }

    // 获取I
    public static List<Integer> getI(List<Integer> from, String ch, List<FA> nfas) {
        List<Integer> list = new ArrayList<Integer>();

        for (Integer o : from) {
            for (FA nfa : nfas) {
                if (nfa.getForm() == o && nfa.getVarch().equals(ch)) {
                    int to = nfa.getTo();
                    if (!list.contains(to)) {
                        list.add(to);
                        list.addAll(getI(list, ch, nfas));
                    }
                }
            }
        }
        return list;
    }

    // 获取IE
    public static List<Integer> getIE(List<Integer> from, List<FA> nfas) {
        List<Integer> list = new ArrayList<Integer>();
        list.addAll(from);

        for (Integer o : from) {
            for (FA nfa : nfas) {
                if (nfa.getForm() == o && nfa.getVarch().equals(E)) {
                    int to = nfa.getTo();
                    if (!list.contains(to)) {
                        list.add(to);
                        list.addAll(getIE(list, nfas));
                    }
                }
            }
        }

        Set<Integer> set = new HashSet<Integer>();
        set.addAll(list);

        list.clear();
        list.addAll(set);

        return list;
    }

    /** 获取输入符号 */
    public static List<String> getLetter(List<FA> nfas) {
        List<String> letter = new ArrayList<String>();
        for (FA nfa : nfas) {
            if (isLetter(nfa.getVarch())) {
                if (!letter.contains(nfa.getVarch()))
                    letter.add(nfa.getVarch());
            }
        }
        return letter;
    }

    // 获取列表下标
    public static int getListIndex(List<Integer> list) {
        int i = 0;
        for (List<Integer> l : tempdfas) {
            if (equalList(l, list)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    //获取DFA的结束符
    public static List<Integer> calculateEnd(){
        return ends;
    }

    //字母判断
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

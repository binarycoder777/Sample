package com.cqut.atao.LL1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName LL1.java
 * @Description LL(1)预测分析法
 * @createTime 2022年05月22日 13:47:00
 */
public class LL1 {


    // First集
    private Map<String,List<String>> First = new HashMap<>();

    // Follow集
    private Map<String,List<String>> Follow = new HashMap<>();

    // LL(1)文法表
    private Map<String,List<String>> LAN = new HashMap<>();

    // 预测分析表
    private Map<String,Object> table = new HashMap<>();

    // VT终结符
    private Set<String> VT = new HashSet<>();

    // 存储过程
    private Map<Integer,List<String>> process = new HashMap<>();

    // 错误标志
    private boolean error = false;

    // 开始符号
    private Character startV;

    // 读取LL1文法
    public String readLAN(String filePath){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String s = "";
            // 一次读入一行，直到读入null为文件结束
            int i = 0;
            while ((s = reader.readLine()) != null) {
                // 获取开始符号
                if (i == 0){
                    startV = s.charAt(0);
                    i = 1;
                }
                String key = s.charAt(0)+"";
                String[] val = s.substring(3).split("\\|");
                LAN.put(key, Arrays.asList(val.clone()));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        String res = "";
        for (Map.Entry entry:LAN.entrySet()){
            res += (entry.getKey()+"\t"+entry.getValue());
        }
        return res;
    }



    // 求First集
    public String getFirst(){
        Map<String,Object> length = new HashMap<>();
        for (String key: LAN.keySet()){
            First.put(key,new ArrayList<>());
            length.put(key,0);
        }
        boolean flag = true;
        while (flag){
            for (String key: LAN.keySet()){
                List<String> l = LAN.get(key);
                for (String s: l){
                    if (!Character.isUpperCase(s.charAt(0)) || s.charAt(0) == 'ε'){
                        First.get(key).add(s.charAt(0)+"");
                    }else if (Character.isUpperCase(s.charAt(0))){
                         List<String> temp = First.get(s.charAt(0) + "");
                         for (int i=0;i<temp.size();++i){
                             if ("ε".equals(temp.get(i))) {
                                 temp.remove(i);
                             }
                         }
                        First.get(key).addAll(temp);
                         for (int i=0;i<s.length()-1;++i){
                             if (Character.isUpperCase(s.charAt(i)) && First.get(s.charAt(i)+"").contains("ε")){
                                 if (Character.isUpperCase(s.charAt(i+1))){
                                     List<String> k = First.get(s.charAt(i+1)+"");
                                     if (k.contains("ε")){
                                         k.remove("ε");
                                     }
                                     First.get(key).addAll(k);
                                 }else {
                                     First.get(key).add(s.charAt(i+1)+"");
                                     break;
                                 }
                             }else {
                                 break;
                             }
                         }
                         int ft = 0;
                         for (char i: s.toCharArray()){
                             if (Character.isUpperCase(i) && First.get(i+"").contains('ε'+"")){
                                 continue;
                             }else {
                                 ft = 1;
                                 break;
                             }
                         }
                         if (ft == 0){
                             First.get(key).add("ε");
                         }
                    }
                    // 去重
                    List<String> list = First.get(key);
                    Set<String> set = new HashSet<>();
                    for (String s1: list){
                        set.add(s1);
                    }
                    First.get(key).clear();
                    First.get(key).addAll(set);
                }
            }
            int fg = 0;
            for (String key: LAN.keySet()){
                if ((Integer) length.get(key) != First.get(key).size()){
                    length.put(key,First.get(key).size());
                    fg = 1;
                }
            }
            if (fg == 0){
                flag = false;
            }
        }
        System.out.println("First集:"+First);
        String res = "";
        for (Map.Entry entry: First.entrySet()){
            res += entry.getKey().toString() + entry.getValue().toString() + "\n";
        }
        return res;
    }

    public String getFollow(){
        for (String k: LAN.keySet()){
            Follow.put(k,new ArrayList<>());
            List<String> lanTemp = new ArrayList<>(LAN.keySet());
            if (lanTemp.contains(k)){
                Follow.get(k).add("#");
            }
        }
        for (int i=0;i<3;++i){
            for (String k: LAN.keySet()){
                List<String> l = LAN.get(k);
                for (String s: l){
                    // 若A→αB是一个产生式，则把FOLLOW(A)加至FOLLOW(B)中
                    if (Character.isUpperCase(s.charAt(s.length()-1))){
                        Follow.get(s.charAt(s.length()-1)+"").addAll(Follow.get(k));
                        // 去除空
                        List<String> temp = Follow.get(s.charAt(s.length()-1)+"");
                        temp.remove("ε");
                    }
                    for (int j=0;j<s.length()-1;++j){
                        if (Character.isUpperCase(s.charAt(j))){
                            // 若A→αBC是一个产生式，则把FIRST(C)\{ε}加至FOLLOW(B)中；
                            if (Character.isUpperCase(s.charAt(j+1))){
                                Follow.get(s.charAt(j)+"").addAll(First.get(s.charAt(j+1)+""));
                                // 去除空
                                List<String> temp = Follow.get(s.charAt(j)+"");
                                temp.remove("ε");
                            }
                            // 若A→αBc是一个产生式，c不为'ε'，则把c加至FOLLOW(B)中；
                            if (!Character.isUpperCase(s.charAt(j+1)) && s.charAt(j+1) != 'ε'){
                                Follow.get(s.charAt(j)+"").add(s.charAt(j+1)+"");
                            }
                            int emptyFlag = 1;
                            for (int z = j+1; z < s.length();++z){
                                if (!Character.isUpperCase(s.charAt(z)) || (Character.isUpperCase(s.charAt(z) ) & !First.get(s.charAt(z)+"").contains("ε"))){
                                    emptyFlag = 0;
                                    break;
                                }
                            }
                            // A→αBβ是一个产生式而(即ε属于FIRST(β))，则把FOLLOW(A)加至FOLLOW(B)中
                            if (emptyFlag == 1){
                                Follow.get(s.charAt(j)+"").addAll(Follow.get(k));
                                // 去除空
                                List<String> temp = Follow.get(s.charAt(j)+"");
                                temp.remove("ε");
                            }
                        }
                    }
                }
            }
        }
        for (String k :Follow.keySet()){
            // 去重
            List<String> list = Follow.get(k);
            Set<String> set = new HashSet<>();
            for (String s1: list){
                set.add(s1);
            }
            Follow.get(k).clear();
            Follow.get(k).addAll(set);
        }
        System.out.println("Follow:"+Follow);
        String res = "";
        for (Map.Entry entry: Follow.entrySet()){
            res += entry.getKey().toString() + entry.getValue().toString() + "\n";
        }
        return res;
    }

    public String getVT(){
        VT.add("#");
        for (List list: LAN.values()){
            for (int i=0;i<list.size();++i){
                String s = (String)list.get(i);
                for (Character c: s.toCharArray()){
                    if (!Character.isUpperCase(c) && (c != 'ε')){
                        VT.add(c+"");
                    }
                }
            }
        }
        System.out.println("终结符:"+VT);
        String res = "";
        for (String s: VT){
            res += s + "\n";
        }
        return res;
    }

    public String generateTable(){
        getVT();
        for (String k: LAN.keySet()){
            table.put(k,new HashMap<>());
            for (String e: VT){
                ((Map)table.get(k)).put(e,null);
            }
        }
        for (String k: LAN.keySet()){
            List<String> l = LAN.get(k);
            for (String s: l){
                if (Character.isUpperCase(s.charAt(0))){
                    for (String e: VT){
                        int fg = 0;
                        for (Character j: s.toCharArray()){
                            if (First.get(j+"").contains(e)){
                                ((Map)table.get(k)).put(e,s);
                            }
                            if (!First.get(j+"").contains("ε")){
                                fg = 1;
                                break;
                            }
                        }
                        if (fg == 0){
                            for (String c:Follow.get(k)){
                                ((Map)table.get(k)).put(c,s);
                            }
                        }
                    }
                }
                if (VT.contains(s.charAt(0)+"")){
                    ((Map)table.get(k)).put(s.charAt(0)+"",s);
                }
                if ("ε".equals(s)){
                    for (String c: Follow.get(k)){
                        ((Map)table.get(k)).put(c,s);
                    }
                }
            }
        }
        System.out.println("分析表:"+table);
        String res = "";
        for (Map.Entry entry: table.entrySet()){
            res += entry.getKey().toString() + "\t" + entry.getValue().toString() + "\n";
        }
        return res;
    }

    public String analyze(String input){
        String inputS = input+"#";
        Stack<Character> inputStr = new Stack<>();
        for (int i=inputS.length()-1;i>=0;--i){
            inputStr.add(inputS.charAt(i));
        }
        // 入栈
        Stack<Character> stack = new Stack<>();
        stack.add('#');
        // 开始符入栈
        stack.add(startV);
        // 出错符号
        int errorFlag = 0;
        // 插入列表索引
        int count = 0;
        // 清空存储过程
        process.clear();
        // 加入
        List<String> tmp = new ArrayList<>();
        String t = "";
        for (Character s: stack){
            t = t + s;
        }
        tmp.add(t);
        t = "";
        for (Character s: inputStr){
            t = s + t;
        }
        tmp.add(t);
        tmp.add(" ");
        process.put(count,tmp);
        // 开始分析
        while (true){
            for (Character i: inputStr){
                if (!VT.contains(i+"")){
                    errorFlag = 1;
                    break;
                }
            }
            if (errorFlag == 1){
                break;
            }
            count += 1;
            String current = stack.pop()+"";
            if (VT.contains(current) && !current.equals("#")){
                if (current.equals(inputStr.peek()+"")){
                    inputStr.pop();
                }else {
                    errorFlag = 1;
                    break;
                }
            }else if (current.equals("#")){
                if (!current.equals(inputStr.peek()+"")){
                    errorFlag = 1;
                }
                break;
            }else if (((Map)table.get(current)).get(inputStr.peek()+"") != null){
                if (!((Map)table.get(current)).get(inputStr.peek()+"").equals("ε")){
                    String temp = (String) ((Map)table.get(current)).get(inputStr.peek()+"");
                    for (int i=temp.length()-1;i>=0;--i){
                        stack.add(temp.charAt(i));
                    }
                    List<String> list = new ArrayList<>();
                    String t1 = "";
                    for (Character s: stack){
                        t1 += s;
                    }
                    list.add(t1);
                    t1 = "";
                    for (Character s: inputStr){
                        t1 += s;
                    }
                    list.add(t1);
                    t1 = "";
                    t1 = current + "->" + ((Map<?, ?>) table.get(current)).get(inputStr.peek()+"");
                    list.add(t1);
                    process.put(count,list);
                }else {
                    String t1 = "";
                    List<String> list = new ArrayList<>();
                    for (Character s: stack){
                        t1 += s;
                    }
                    list.add(t1);
                    t1 = "";
                    for (Character s: inputStr){
                        t1 += s;
                    }
                    list.add(t1);
                    t1 = "";
                    t1 = current + "-> ε";
                    list.add(t1);
                    process.put(count,list);
                }
            }else {
//                System.out.println(((Map)table.get(current)));
                errorFlag = 1;
                break;
            }
        }
        if (errorFlag == 1){
            System.out.println("分析失败");
        }else {
            String res = "步骤\\t符号栈\\t输入串\\t所用产生式\n";
            System.out.println("分析成功");
            System.out.println("步骤\\t符号栈\\t输入串\\t所用产生式");
            int size = 0;
            for (Integer i: process.keySet()){
                size = Math.max(size,i);
            }
            for (int i=0;i<size;++i){
                if (process.containsKey(i)){
                    res += (i+"\t\t"+process.get(i).get(0)+"\t\t"+process.get(i).get(1)+"\t\t"+process.get(i).get(2)+"\n");
                    System.out.println(i+"\t\t"+process.get(i).get(0)+"\t\t"+process.get(i).get(1)+"\t\t"+process.get(i).get(2));
                }
            }
            return res;
        }
        return "分析有错";
    }

}

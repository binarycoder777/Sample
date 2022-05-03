package com.cqut.atao.syntax.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName LLReader.java
 * @Description TODO
 * @createTime 2022年05月02日 14:35:00
 */
public class LLReader {

    public static String parseKey(String s){
        int start = s.indexOf("(");
        int end = s.indexOf(")");
        return s.substring(start+1,end);
    }

    public static Set<String> parseVal(String s){
        Set<String> set = new HashSet<>();
        int start = s.indexOf("{");
        int end = s.lastIndexOf("}");
        String res = s.substring(start+1,end);
        String[] tmp = res.split("、");
        for (String t: tmp){
            set.add(t);
        }
        return set;
    }

    /**
     * 读取文法
     * @return
     */
    public static List<Map<String,Set<String>>> readFirstAndFollow(String fileName){
        List<Map<String,Set<String>>> ans = new ArrayList<>();
        Map<String, Set<String>> first = new HashMap<>();
        Map<String,Set<String>> follow = new HashMap<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String s = null;
            // fill first and follow
            while ((s = reader.readLine()) != null) {
                String key = parseKey(s);
                Set<String> val = parseVal(s);
                if (s.startsWith("First")){
                    first.put(key,val);
                }else {
                    follow.put(key,val);
                }
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
        ans.add(first);
        ans.add(follow);
        return ans;
    }
}

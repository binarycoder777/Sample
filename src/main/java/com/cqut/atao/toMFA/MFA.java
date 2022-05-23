package com.cqut.atao.toMFA;

import com.cqut.atao.toMFA.entity.FA;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MFA.java
 * @Description 最小化状态机(DFA->MFA)
 * @createTime 2022年05月23日 09:00:00
 */
public class MFA {


    public static List<FA> parse(List<FA> dfas) {

        List<Integer> indexs = getAllIndex(dfas);

        List<Integer> starts = getStartIndex(indexs);

        List<Integer> ends = DFA.calculateEnd();

        List<String> letter = DFA.letter;
        // System.out.println(indexs);
        // System.out.println(starts);
        // System.out.println(ends);

        // 计算等价的开始符号
        for (int i = 0; i < starts.size(); i++) {
            boolean flag = true;
            for (int j = i + 1; j < starts.size(); j++) {
                int start = starts.get(i);
                int s = starts.get(j);
                for (int k = 0; k < letter.size(); k++) {
                    String ch = letter.get(k);

                    int startTo = -1;
                    for (FA dfa : dfas) {
                        if (dfa.getForm() == start && dfa.getVarch().equals(ch)) {
                            startTo = dfa.getTo();
                            break;
                        }
                    }
                    int sTo = -1;
                    for (FA dfa : dfas) {
                        if (dfa.getForm() == s && dfa.getVarch().equals(ch)) {
                            sTo = dfa.getTo();
                            break;
                        }
                    }
                    if (startTo != sTo) {
                        flag = false;
                    }
                }

                // 成立
                if (flag) {
//					starts.remove(j);
//					j--;
                    starts.remove(i);
                    break;
                }
            }
        }

        // System.out.println(starts);

        // 计算等价的结束符号
        for (int i = 0; i < ends.size(); i++) {
            boolean flag = true;
            for (int j = i + 1; j < ends.size(); j++) {
                int end = ends.get(i);
                int e = ends.get(j);
                for (int k = 0; k < letter.size(); k++) {
                    String ch = letter.get(k);

                    int endTo = -1;
                    for (FA dfa : dfas) {
                        if (dfa.getForm() == end && dfa.getVarch().equals(ch)) {
                            endTo = dfa.getTo();
                            break;
                        }
                    }
                    int eTo = -1;
                    for (FA dfa : dfas) {
                        if (dfa.getForm() == e && dfa.getVarch().equals(ch)) {
                            eTo = dfa.getTo();
                            break;
                        }
                    }
                    if (endTo != eTo
                            || (ends.contains(endTo) && !ends.contains(eTo))
                            || (ends.contains(eTo) && !ends.contains(endTo))) {
                        flag = false;
                    }
                }

                // 成立
                if (flag) {
                    ends.remove(j);
                    j--;
                }
            }
        }
        //System.out.println(ends);

        List<Integer> allIndex = new ArrayList<Integer>();
        allIndex.addAll(starts);
        allIndex.addAll(ends);

        for (int i = 0; i < dfas.size(); i++) {
            if(!allIndex.contains(dfas.get(i).getForm()) || !allIndex.contains(dfas.get(i).getTo())){
                dfas.remove(i);
                i--;
            }
        }

//		for (int i = 0; i < dfas.size(); i++) {
//			System.out.println(dfas.get(i));
//		}

        return dfas;
    }

    public static int calculateStart(List<FA> mfas){
        if(mfas.size() != 0){
            return mfas.get(0).getForm();
        }
        return -1;
    }


    // 获取所有的字符
    public static List<Integer> getAllIndex(List<FA> dfas) {

        List<Integer> indexs = new ArrayList<Integer>();
        for (FA dfa : dfas) {
            if (!indexs.contains(dfa.getForm())) {
                indexs.add(dfa.getForm());
            }
        }

        for (FA dfa : dfas) {
            if (!indexs.contains(dfa.getTo())) {
                indexs.add(dfa.getTo());
            }
        }

        return indexs;
    }

    // 获取开始的下标
    public static List<Integer> getStartIndex(List<Integer> indexs) {

        List<Integer> starts = new ArrayList<Integer>();

        List<Integer> ends = DFA.calculateEnd();

        for (Integer index : indexs) {
            if (!ends.contains(index)) {
                starts.add(index);
            }
        }
        return starts;
    }

    public static void main(String[] args) {

    }

}

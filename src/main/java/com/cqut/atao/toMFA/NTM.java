package com.cqut.atao.toMFA;

import com.cqut.atao.toMFA.entity.FA;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName NTM.java
 * @Description 正规集->NFA->DFA->MFA
 * @createTime 2022年05月23日 09:02:00
 */
public class NTM {

    private List<FA> nfa = new ArrayList<>();

    private List<FA> dfa = new ArrayList<>();

    private List<FA> mfa = new ArrayList<>();

    public void toNFA(String text){
        nfa = NFA.parse(text);
    }

    public void toDFA(){
        dfa = DFA.parse(nfa);
    }

    public void toMFA(){
        mfa = MFA.parse(dfa);
    }

    public String getNFA(String text){
        toNFA(text);
        int start = NFA.calculateStart(nfa);
        int end = NFA.calculateEnd(nfa);
        String res = "NFA" + "\n" +
                "开始:"+start+"\n" +
                "结束:"+end+"\n";
        for (FA fa: nfa){
            res += (fa.toString().substring(3)+"\n");
        }
        return res;
    }

    public String getDFA(){
        toDFA();

        int start = NFA.calculateStart(dfa);
        List<Integer> list = DFA.calculateEnd();
        String end = list.toString().substring(1);
        end = end.substring(0,end.length()-1);
        String res = "DFA" + "\n" +
                "开始:"+start+"\n" +
                "结束:"+end+"\n";

        for (FA fa: dfa){
            res += (fa.toString().substring(3)+"\n");
        }
        return res;
    }

    public String getMFA(){
        toMFA();
        int start = MFA.calculateStart(mfa);

        List<Integer> list = DFA.calculateEnd();
        String end = list.toString().substring(1);
        end = end.substring(0,end.length()-1);
        String res = "MFA" + "\n" +
                "开始:"+start+"\n" +
                "结束:"+end+"\n";
        for (FA fa: mfa){
            res += (fa.toString().substring(3)+"\n");
        }
        return res;
    }

}

package com.cqut.atao.compile;
import com.cqut.atao.compile.errorWord;
import com.cqut.atao.compile.wordlist;

import javax.swing.*;
import java.util.*;

public class Compile {
    private static void initDictionary() {
        dictionary = new HashMap<>();
        list = new ArrayList<>();
//        关键字
        dictionary.put("char", 101);
        dictionary.put("int", 102);
        dictionary.put("float", 103);
        dictionary.put("break", 104);
        dictionary.put("const", 105);
        dictionary.put("return", 106);
        dictionary.put("void", 107);
        dictionary.put("continue", 108);
        dictionary.put("do", 109);
        dictionary.put("while", 110);
        dictionary.put("if", 111);
        dictionary.put("else", 112);
        dictionary.put("for", 113);
        dictionary.put("string", 114);
        dictionary.put("main", 115);
        dictionary.put("bool", 116);
        dictionary.put("true", 117);
        dictionary.put("false", 118);
//        运算符
        dictionary.put("(", 201);
        dictionary.put(")", 202);
        dictionary.put("[", 203);
        dictionary.put("]", 204);
        dictionary.put("!", 205);
        dictionary.put("*", 206);
        dictionary.put("/", 207);
        dictionary.put("%", 208);
        dictionary.put("+", 209);
        dictionary.put("-", 210);
        dictionary.put("<", 211);
        dictionary.put("<=", 212);
        dictionary.put(">", 213);
        dictionary.put(">=", 214);
        dictionary.put("==", 215);
        dictionary.put("!=", 216);
        dictionary.put("&&", 217);
        dictionary.put("||", 218);
        dictionary.put("=", 219);
        dictionary.put(".", 220);
        dictionary.put("++", 221);
        dictionary.put("--", 222);
        dictionary.put("+=", 223);
        dictionary.put("-=", 224);
        dictionary.put("*=", 225);
        dictionary.put("/=", 226);
//        dictionary.put("&", 227);
//        dictionary.put("|", 228);
        dictionary.put("^", 229);
        dictionary.put("<<", 230);
        dictionary.put(">>", 231);
//        界符
        dictionary.put("{", 301);
        dictionary.put("}", 302);
        dictionary.put(";", 303);
        dictionary.put(",", 304);
        dictionary.put("'", 304);
        dictionary.put("\"", 304);
        dictionary.put(" ", 304);
    }

    private static char getNextChar() {
//        这里的回车不用count++,因为它并没有真正的取，真正的是在下面
//        if (str.charAt(++index) == '\n')
//            count++;
        return str.charAt(++index);
    }

    private static void error(String word) {
        errorList.add(new errorWord(count, word));
    }

    private static void getToken(String word) {
//        不够完善
//        不是字典有的 手动添加
        if (dictionary.get(word) == null) {
            if (word.length() == 1) {       //单个字符
                list.add(new wordlist(count,word, 700));
            } else {
                list.add(new wordlist(count,word, 700)); //标识符
            }
        }
//        有就直接添加
        else list.add(new wordlist(count,word, dictionary.get(word)));
    }

    private static void recognizeId(char ch) {

        if (ch == '\n') {
            count++;
            index++;
            return;
        } else if (ch == ' ' || ch == '\t') {
            index++;
            return;
        }

        String word = "";
        boolean flag = true;
        int state = 0;   //初始状态
        while (state != 2 && state != 9 && state != 10 && state != 11 &&
                state != 13 && state != 14 && state != 19 && state != 20 &&
                state != 22 && state != 23 && state != 24 && state != 27) {
            switch (state) {
//                最开始的一个分支  识别第一个字符
                case 0:
                    if (Character.isLetter(ch)) {
                        state = 1;
                        word += ch;
                    }     //是字母，转向状态1
                    else if (ch >= '1' && ch <= '9') {
                        state = 3;
                        word += ch;
                    }      //1-9的数字，不包含0,转向状态3
                    else if (ch == '0') {       //数字0
                        state = 12;                            //转12
                        word += ch;
                    } else if (dictionary.get(ch + "") != null && dictionary.get(ch + "") > 300 && dictionary.get(ch + "") < 400) {
                        state = 14;
                        word += ch;                              //识别一个界符
                        index++;
                    } else if (ch == '/') {        //识别到一个运算符’/‘
                        state = 15;
                        word += ch;
                    } else if (dictionary.get(ch + "") != null && dictionary.get(ch + "") > 200 && dictionary.get(ch + "") < 300) {       //除了’/‘的运算符
                        state = 21;
                        word += ch;

                    } else {
                        state = -1;
                        word += ch;
                    }
                    ;                             // 否则调用出错处理，识别其他的单词
                    break;
                case 1:
                    ch = getNextChar();                        //读取下一个字符
                    if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') {
                        state = 1;
                        word += ch;      //是字母或数字，状态不变
                    } else {
                        state = 2;                               //其他字符，转向状态2结束，识别其他单词
                    }
                    break;
                case 3:
                    ch = getNextChar();
                    if (Character.isDigit(ch)) {
                        state = 3;
                        word += ch;      //是数字，状态不变
                    } else if (ch == '.') {
                        state = 4;
                        word += ch;
                    } else if (ch == 'e' || ch == 'E') {
                        state = 6;
                        word += ch;
                    } else if (Character.isLetter(ch)) {
                        state = -1;   //-1表示错误 出现了数字开头后面是字母的情况
                        word += ch;
                    } else {
                        state = 11;
                        flag = false;
                        list.add(new wordlist(count,word, 400));//其他字符，转向状态11结束是  不是0的整数
                    }
                    break;
                case 4:
                    ch = getNextChar();
                    if (Character.isDigit(ch)) {
                        state = 5;
                        word += ch;
                    } else {
                        state = -1;
                        word += ch;
                    }
                    break;
                case 5:
                    ch = getNextChar();
                    if (Character.isDigit(ch)) {
                        state = 5;
                        word += ch;
                    } else if (ch == 'e' || ch == 'E') {
                        state = 6;
                        word += ch;
                    } else {
                        state = 10;
                        flag = false;
                        list.add(new wordlist(count,word, 800));//其他字符，转向状态10  是小数  算实数
                    }
                    break;
                case 6:
                    ch = getNextChar();
                    if (ch == '+' || ch == '-') {
                        state = 7;
                        word += ch;
                    } else if (Character.isDigit(ch)) {
                        state = 8;
                        word += ch;
                    } else {
                        state = -1;
                        word += ch;
                    }
                    break;
                case 7:
                    ch = getNextChar();
                    if (Character.isDigit(ch)) {
                        state = 8;
                        word += ch;
                    } else {
                        state = -1;
                        word += ch;
                    }
                    break;
                case 8:
                    ch = getNextChar();
                    if (Character.isDigit(ch)) {
                        state = 8;
                        word += ch;
                    } else {
                        state = 9;
                        flag = false;
                        list.add(new wordlist(count,word, 800));//其他字符，转向状态9  是指数  算实数
                    }
                    break;
                case 12:
                    ch = getNextChar();
                    if (ch == '.') {
                        state = 4;
                        word += ch;
                    } else if (ch == '0') {  //00.234e3;
                        state = -1;
                        word += ch;
                    } else if (ch >= '1' && ch <= '7') {
                        word += ch;
                        ;
                    } else if (ch == 'x' || ch == 'X') {
                        state = 25;
                        word += ch;
                    } else if (ch >= '8' && ch <= '9') {
                        state = -1;
                        word += ch;
                    } else {
                        state = 13;
                        flag = false;
                        list.add(new wordlist(count,word, 400));//整数0
                    }
                    break;
                case 15:
                    ch = getNextChar();
                    if (ch == '/') {
                        state = 16;  //单行注释
                        word = "";   //应该还要执行一个操作
                    } else if (ch == '*') {
                        state = 17;  //多行注释
                        word = "";
                    } else {
                        state = 20;  //结束 识别到单个除号
                    }
                    break;
                case 16:
                    ch = getNextChar();
                    if ((ch + "").equals("\n")) { //读到直至回车换行符  直接退出
                        return;
                    }
                    break;
                case 17:
                    ch = getNextChar();
                    if (ch == '*') {
                        state = 18;
                    }
                    break;
                case 18:
                    ch = getNextChar();
                    if (ch == '/') {
                        state = 19;
                        index++;  //读取下一个
                        return;
                    }
                    break;
                case 21:
                    ch = getNextChar();
                    if (word.equals(")") || word.equals("(")) {

                        state = 23;
                    } else if ((dictionary.get(ch + "") == null) || ch == '(' || ch == ')') {
                        state = 23;
                    } else if (dictionary.get(ch + "") > 200 && dictionary.get(ch + "") < 300) { //运算符
                        word += ch;
                        state = 22; //双目运算符
                        char three = str.charAt(index + 1);
                        if (three > 200 && three < 300 && three != '(' && three != ')') {
                            state = -1;
                            word += ch;
                        }
                        index++;
//                        char three = getNextChar();//判断是不是三个运算符的情况
//                        if((three+"").equals(" "))
//                            three = getNextChar();
//                        if (dictionary.get(three + "")!=null &&dictionary.get(three + "") > 200 && dictionary.get(three + "") < 300) {
//                            state = -1;
//                            word += three;
//                        }
                    } else {
                        state = 23; //单目运算符
                    }
                    break;
                case 25:
                    ch = getNextChar();
                    if (Character.isDigit(ch) || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F')) {
                        state = 26;
                        word += ch;
                    } else {
                        state = -1;
                        word += ch;
                    }
                    break;
                case 26:
                    ch = getNextChar();
                    if ((ch >= 'g' && ch <= 'z') || (ch >= 'G' && ch <= 'Z')) {
                        state = -1;
                    } else if (Character.isDigit(ch) || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F')) {
                        ;
                    } else {
                        state = 27;
                    }
                    word += ch;
                    break;

                case -1:
                    ch = getNextChar();
                    if (dictionary.get(ch + "") == null || ch == '.') {  //不是界符和运算符  但是是‘.’也继续读
                        if (ch == ' ' || ch == '\t' || ch == '\n')
                            state = -2;   //结束读取错误的字符串
                        else
                            word += ch;       //读取错误的字符串
                    } else
                        state = -2;
                    break;
                case -2:
                    error(word);
                    state = 0;   //归0
                    flag = false;
                    break;
            }
        }
        if (flag)
            getToken(word);        //存单词
    }


    public static int index = 0;  //文件字符串下标
    public static int count = 1;  //文件多少行 用于报错
    public static String str = "";   //文件内容
    public static HashMap<String, Integer> dictionary = null;  //字典
    public static ArrayList<wordlist> list = null;  //内容字典
    public static ArrayList<errorWord> errorList = null;  //报错的内容

    //    词法
    public static void phraseAnalysis(JTextArea textArea, JTextArea textArea1, JTextArea textArea2) {
        index = 0;
        count = 1;
        str = textArea.getText();
        System.out.println(str);
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        initDictionary();
        errorList = new ArrayList<>();
        while (index < str.length()) {
//            这里读取做的不好
            recognizeId(str.charAt(index));
        }
        System.out.println("go");
//        输出三元式
        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).toString());
            stringBuffer1.append(list.get(i).toString() + "\n");
        }
        System.out.println("-----------------------------");
        if (errorList.size() == 0) {
            stringBuffer2.append("词法分析没有错误");
        } else {
            for (int i = 0; i < errorList.size(); i++) {
//                System.out.println(errorList.get(i).toString());
                stringBuffer2.append(errorList.get(i).toString() + "\n");
            }
        }
        System.out.println("str:" + stringBuffer1.toString());
        textArea1.setText(stringBuffer1.toString());
        textArea2.setText(stringBuffer2.toString());
    }

    // 语法
    public static void SyntaxAnalysis(JTextArea textArea1, JTextArea textArea2) {
        com.compile.Grammar.main_body();
        StringBuffer stringBuffer1 = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer1.append(com.compile.Grammar.str);
        if(com.compile.Grammar.error_str.length() == 0)
            stringBuffer2.append("程序正确!");
        else
            stringBuffer2.append(com.compile.Grammar.error_str);
        textArea1.setText(stringBuffer1.toString());
        textArea2.setText(stringBuffer2.toString());
    }


    private static void deal_quotes() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals("'")) {
                i++;
                if (list.get(i).getKey().equals("'"))
                    continue;
                else {
                    i++;
                    if (list.get(i).getKey().equals("'")) { //为单个常量或单个变量 ‘a’
                        String str = "'" + list.get(i - 1).getKey() + "'";
                        list.set(i - 2, new wordlist(count,str, 500));
                        System.out.println(list.remove(i));
                        System.out.println(list.remove(i - 1));
                        i = i - 1;
                        System.out.println(i);
                    }
                }
            }
            if (list.get(i).getKey().equals("\"")) {

                System.out.println(list);
                System.out.println(i);
                String str = "";
                str += "\"";
                int j = i + 1;
                int z = i;
                for (; j < list.size(); j++) {
                    if (!list.get(j).getKey().equals("\"")) {
                        str += list.get(j).getKey();
                    } else {
                        str += "\"";
                        break;
                    }
                }
                System.out.println("要添加字符串" + str);
                if (j == list.size()) {     //证明没有对应的"
                    System.out.println("没有与之对应的”");
                    return;
                }
                i = j + 1;
                System.out.println("i=" + i);
                System.out.println("z=" + z);
                while (z < i) {
                    System.out.println("删除这个" + list.remove(z));
                }
                list.add(z, new wordlist(count,str, 600));
            }
        }
    }

    //整数 400
//    字符 500  常量
//    字符串600
//    标识符700
//    实数800
//    测试编译代码
    public static void main(String[] args) {
//        自己测试时str末尾一定是个结束符
        str =   "//if测试，输入两个数，将其中较大的数加100输出\n" +
                "int a = 1 ;\n" +
                "\n" +
                "main(){\n" +
                "\n" +
                "    int result ;\n" +
                "\tint N = read( ;\n" +
                "\tint M = read() ;\n" +
                "    \n" +
                "    if (M >= N)result = M ;\n" +
                "\telse result = N;\n" +
                "\ta = result + 100 ;\n" +
                "\twrite(a);\n" +
                "\n" +
                "}\n" +
                "\n";
        StringBuffer stringBuffer = new StringBuffer();
        initDictionary();
        errorList = new ArrayList<>();
        while (index < str.length()) {
            recognizeId(str.charAt(index));
        }
//        for(int i = 0 ; i<list.size();i++) {
//            System.out.println(list.get(i).toString());
//            stringBuffer.append(list.get(i).toString() + "\n");
//        }

        System.out.println("-----------------------------");
        for (int i = 0; i < errorList.size(); i++) {
            System.out.println("词法分析错误的有" + errorList.get(i).toString());
        }
        System.out.println("*****************************");
        System.out.println("词法分析结果:");
        for (int i = 0; i < list.size(); i++) {
            if (i % 5 == 0) {
                System.out.println();
                System.out.print("第" + i + "个\t");
            }

            System.out.print(list.get(i) + "\t");
        }
        System.out.println();
//        System.out.println(list);
//        deal_quotes();
        com.compile.Grammar.main_body();
        System.out.println(com.compile.Grammar.error_str);
    }


}


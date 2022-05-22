package com.cqut.atao.rebuild;

import com.cqut.atao.token.Char;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Lexer.java
 * @Description 词法分析
 * @createTime 2022年05月19日 13:14:00
 */
public class Lexer {

    // token类
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Token {
        private String word;
        private String code;
        private Integer row;
        private Integer col;
    }

    // 行数
    private int line = 1;

    // token键值对
    private Map<String,String> tokenMap = new HashMap<>();

    // token列表
    private List<Token> tokens = new ArrayList<>();

    // 异常列表
    private List<Exception> exceptions = new ArrayList<>();


    private int regid(String content,int index){
        int state = 0;
        String word = "";
        while (state != 2){
            if (state == 1 && (Character.isLetter(content.charAt(index)) || content.charAt(index) == '_')){
                word += content.charAt(index);
                state = 1;
            }else if (state ==  1){
                if ((Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_' )){
                    word += content.charAt(index);
                }else {
                    state = 2;
                }
            }
            if (state == 2) index--;
            index++;
            if (index == content.length()){
                System.out.println("out of");
                break;
            }
        }
        if (tokenMap.containsKey(word)){
            tokens.add(new Token(word,tokenMap.get(word),line,0));
        }else {
            tokens.add(new Token(word,"800",line,0));
        }
        return index;
    }

    private boolean containsOP(char[] op,char c){
        for (char o: op){
            if (o == c) return true;
        }
        return false;
    }

    private int regnum(String content,int index){
        int state = 0;
        String word = "";
        char[] op = {'+', '-', '*', '/', '&', '|', '!', '>', '<', '=', '[', ']', '(', ')', '%', '\n', '\t', ';', ','};
        while (state < 4 || state > 14){
            if (state == 0){
                if (content.charAt(index) == '0'){
                    state = 2;
                }else if (Character.isDigit(content.charAt(index))){
                    state = 1;
                }
                word += content.charAt(index);
            }else if (state == 1){
                if (content.charAt(index) == 'e' || content.charAt(index) == 'E'){
                    state = 10;
                    word += content.charAt(index);
                }else if (containsOP(op,content.charAt(index))){
                    state = 4;
                }else if (Character.isDigit(content.charAt(index))){
                    word += content.charAt(index);
                }else if (content.charAt(index) == '.'){
                    word += content.charAt(index);
                    state = 8;
                }else {
                    state = 14;
                    word += content.charAt(index);
                }
            }else if (state == 2){
                if (content.charAt(index) == '.'){
                    state = 8;
                    word += content.charAt(index);
                }else if (content.charAt(index) >= '0' && content.charAt(index) <= 7){
                    word += content.charAt(index);
                    state = 3;
                }else if (content.charAt(index) == 'x' || content.charAt(index) =='X'){
                    word += content.charAt(index);
                    state = 5;
                }else {
                    state = 4;
                }
            }else if (state == 3){
                if (content.charAt(index) >= '0' && content.charAt(index) <= '7'){
                    word += content.charAt(index);
                }else {
                    state = 4;
                }
            }else if (state == 5){
                if (Character.isLetterOrDigit(content.charAt(index))){
                    word += content.charAt(index);
                    state = 6;
                }
            }else if (state == 6){
                if (Character.isLetterOrDigit(content.charAt(index))){
                    word += content.charAt(index);
                }else {
                    state = 4;
                }
            }else if (state == 8){
                if (Character.isDigit(content.charAt(index))){
                    state = 9;
                }else {
                    state = 14;
                }
            }else if (state == 9){
                if (content.charAt(index) == 'e' || content.charAt(index) == 'E'){
                    word += content.charAt(index);
                    state = 10;
                }else if (Character.isDigit(content.charAt(index))){
                    word += content.charAt(index);
                }else if (containsOP(op,content.charAt(index))){
                    state = 4;
                }else {
                    word += content.charAt(index);
                    state = 14;
                }
            }else if (state == 10){
                if (content.charAt(index) == '+' || content.charAt(index) == '-'){
                    state = 11;
                }else if (Character.isDigit(content.charAt(index))){
                    state = 12;
                }else {
                    state = 14;
                }
                word += content.charAt(index);
            }else if (state == 11){
                word += content.charAt(index);
                if (Character.isDigit(content.charAt(index))){
                    state = 12;
                }else {
                    state = 14;
                }
            }else if (state == 12){
                if (Character.isDigit(content.charAt(index))){
                    word += content.charAt(index);
                }else if (containsOP(op,content.charAt(index))){
                    state = 4;
                }else {
                    word += content.charAt(index);;
                    state = 14;
                }
            }
            if (state == 4) --index;
            ++index;
            if (index == content.length()){
                System.out.println("out of");
                break;
            }
        }
        if (state == 4){
            tokens.add(new Token(word,"500",line,0));
        }else {
            exceptions.add(new Exception("line:"+line+" "+ word + "error"));
        }
        return index;
    }

    private void regnode(String content,int index){
        int line = 0, state = 0;
        String word = "";
        while (state < 4 && state > 6){
            if (state == 0 && content.charAt(index) == '/'){
                word += content.charAt(index);
                state = 1;
            }else if (state == 1 && content.charAt(index) == '/'){
                while (content.charAt(index) != '\n'){
                    index += 1;
                }
            }
        }
    }


}

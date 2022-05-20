package com.cqut.atao.syntax.strategy.statement;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.middle.MiddleCode;
import com.cqut.atao.middle.table.*;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Syntax.java
 * @Description 语法分析
 * @createTime 2022年05月13日 14:03:00
 */
@Data
@NoArgsConstructor
public class Syntax {
    
    private MyTree tree;
    
    private TokenList<Token> tokens;

    private List<Exception> exceptions;

    private MiddleCode middleCode = new MiddleCode();

    public void math(MyTree tree, TokenList<Token> tokens) {
        tree.addChild(new TreeNode(tokens.getCurToken().getVal().toString()));
        tokens.match();
        tree.traceBack();
    }

    public boolean judgeD(Token token) {
        return ">".equals(token.getType()) || "<".equals(token.getType()) || ">=".equals(token.getType()) || "<=".equals(token.getType()) || "==".equals(token.getType()) || "!=".equals(token.getType());
    }

    public boolean judgeE(Token token) {
        return "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()) || "void".equals(token.getType());
    }

    public boolean judgeConst(Token token) {
        return "字符串".equals(token.getType()) || "字符".equals(token.getType()) || "实数".equals(token.getType()) || "整数".equals(token.getType());
    }

    private void pass() {
        return;
    }

    public TempVariable expression() {
        TempVariable arg0 = new TempVariable();
        tree.addChild(new TreeNode("表达式"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())) {
            math(tree, tokens);
            arg0 = AR();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            arg0 = ar_A1(arg0);
            arg0 = AR1(arg0);
            arg0 = expression1(arg0);
        } else if (token != null && ("字符串".equals(token.getType()) || "字符".equals(token.getType()) || "整数".equals(token.getType()) || "实数".equals(token.getType()))) {
            arg0.setVal(token.getVal().toString());
            math(tree, tokens);
            arg0 = ar_A1(arg0);
            arg0 = AR1(arg0);
            arg0 = expression1(arg0);
        } else if (token != null && "标识符".equals(token.getType())) {
            arg0.setVal(token.getVal().toString());
            math(tree, tokens);
            arg0 = expression4(arg0);
        } else if (token != null && "!".equals(token.getType())) {
            math(tree, tokens);
            arg0 = BO();
            arg0 = bo_A1(arg0);
            arg0 = BO1(arg0);
        }
        tree.traceBack();
        return arg0;
    }

    private TempVariable expression4(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "=".equals(token.getType()))) {
             return expression3(arg0);
        } else if (token != null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()) || "+".equals(token.getType()) || "-".equals(token.getType()) || judgeD(token) || "&&".equals(token.getType()) || "||".equals(token.getType()))) {
            arg0 = ar_A1(arg0);
            arg0 = AR1(arg0);
            return expression1(arg0);
        }
        return arg0;
    }

    private TempVariable expression3(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())) {
            math(tree, tokens);
            ar_D(arg0);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            // 函数调用
            TempVariable res = middleCode.newtemp();
            middleCode.gencode(res.getId()+"","call",arg0.getVal(),null,res.getVal());
            res = ar_A1(res);
            res = AR1(res);
            return expression1(res);
        } else if (token != null && "=".equals(token.getType())) {
            String op = token.getType();
            math(tree, tokens);
            TempVariable res = expression();
            middleCode.gencode(res.getId()+"",op,res.getVal(),null,"变量入口:"+arg0.getVal());
            return res;
        }
        return arg0;
    }

    private TempVariable expression1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && judgeD(token)) {
            TempVariable res = middleCode.newtemp();
            String op = token.getVal().toString();
            math(tree, tokens);
            TempVariable arg1 = AR();
            middleCode.gencode(res.getId()+"",op,arg0.getVal(),arg1.getVal(),res.getVal());
            return expression2(res);
        } else if (token != null && ("&&".equals(token.getType()) || "||".equals(token.getType()))) {
            arg0 = bo_A1(arg0);
            arg0 = BO1(arg0);
        }
        return arg0;
    }

    private TempVariable expression2(TempVariable arg0) {
        arg0 = bo_A1(arg0);
        arg0 = BO1(arg0);
        return arg0;
    }


    public TempVariable AR() {
        tree.addChild(new TreeNode("算术表达式"));
        TempVariable arg0 = ar_A();
        TempVariable res1 = AR1(arg0);
        tree.traceBack();
        return res1;
    }


    private TempVariable AR1(TempVariable arg1) {
        Token token = tokens.getCurToken();
        if (token != null && ("+".equals(token.getType()) || "-".equals(token.getType()))) {
            TempVariable res = middleCode.newtemp();
            String op = token.getType();
            math(tree, tokens);
            TempVariable arg2 = AR();
//            System.out.println(res.getId());
            middleCode.gencode(res.getId()+"",op,arg1.getVal(),arg2.getVal(),res.getVal());
            return res;
        }
        return arg1;
    }

    private TempVariable ar_A() {
        TempVariable arg1 = ar_B();
        TempVariable res1 = ar_A1(arg1);
        return res1;
    }

    private TempVariable ar_A1(TempVariable arg1) {
        Token token = tokens.getCurToken();
        if (token != null && ("*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()))) {
            TempVariable res = middleCode.newtemp();
            String op = token.getType();
            math(tree, tokens);
            TempVariable arg2 = ar_A();
            middleCode.gencode(res.getId()+"",op,arg1.getVal(),arg2.getVal(),res.getVal());
            return res;
        }
        return arg1;
    }

    private TempVariable ar_B() {
        TempVariable var = new TempVariable();
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))) {
            math(tree, tokens);
            var = AR();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
        } else if (token != null && ("字符串".equals(token.getType()) || "字符".equals(token.getType()) || "整数".equals(token.getType()) || "实数".equals(token.getType()))) {
            var = new TempVariable(middleCode.getTempID(),token.getVal().toString());
            math(tree, tokens);
        } else if (token != null && ("标识符".equals(token.getType()))) {
            TempVariable arg0 = new TempVariable(middleCode.getTempID(),token.getVal().toString());
            math(tree, tokens);
            var = ar_B1(arg0);
        }
        return var;
    }

    private TempVariable ar_B1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))) {
            TempVariable res = middleCode.newtemp();
            middleCode.gencode(res.getId()+"","call",arg0.getVal(),"",res.getVal());
            math(tree, tokens);
            ar_D(arg0);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            return res;
        }
        return arg0;
    }

    private void ar_C() {
        tree.addChild(new TreeNode("函数调用"));
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))) {
            TempVariable arg = new TempVariable(middleCode.getTempID(),token.getVal().toString());
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            ar_D(arg);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
        }
        tree.traceBack();
    }

    private void ar_D(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "标识符".equals(token.getType()) || judgeConst(token) || "!".equals(token.getType()))) {
            ar_E(arg0);
        }
    }

    private void ar_E(TempVariable arg0) {
        TempVariable arg = expression();
        middleCode.gencode(arg0.getId()+"","para",arg.getVal(),null,arg0.getVal());
        ar_E1(arg0);
    }

    private void ar_E1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())) {
            math(tree, tokens);
            ar_E(arg0);
        }
    }

    private void RE() {
        tree.addChild(new TreeNode("关系表达式"));
        AR();
        Token token = tokens.getCurToken();
        if (token != null && judgeD(token)) {
            math(tree, tokens);
        } else {
            tokens.match();
            exceptions.add(new ParseException("缺少关系运算符", tokens.getCurToken()));
        }
        AR();
        tree.traceBack();
    }

    public TempVariable BO() {
        tree.addChild(new TreeNode("布尔表达式"));
        TempVariable arg0 = bo_A();
        TempVariable res = BO1(arg0);
        tree.traceBack();
        return res;
    }

    private TempVariable BO1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && "||".equals(token.getType())) {
            String op = token.getType();
            math(tree, tokens);
            TempVariable arg1 = BO();
            TempVariable res = middleCode.newtemp();
            middleCode.gencode(res.getId()+"",op,arg0.getVal(),arg1.getVal(),res.getVal());
            return res;
        }
        return arg0;
    }

    private TempVariable bo_A() {
        TempVariable arg0 = bo_B();
        TempVariable res = bo_A1(arg0);
//        middleCode.merge(arg0.getId()+"j",res.getId()+"j");
        return res;
    }

    private TempVariable bo_A1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && "&&".equals(token.getType())) {
            math(tree, tokens);
            middleCode.buckpatch(arg0.getId()+"jnz",middleCode.getNXQ());
            TempVariable arg1 = bo_A();
            middleCode.merge(arg1.getId()+"j",arg0.getId()+"j");
            return arg1;
        }
        return arg0;
    }

    private TempVariable bo_B() {
        Token token = tokens.getCurToken();
        TempVariable res = null;
        if (token != null && "！".equals(token.getType())) {
            math(tree, tokens);
            TempVariable arg0 = BO();
            res = middleCode.newtemp();
            res.setTC(arg0.getFC());
            res.setFC(arg0.getTC());
        } else if (token != null && ("(".equals(token.getType()) || judgeConst(token) || "标识符".equals(token.getType()))) {
            TempVariable arg0 = AR();
            res = bo_B1(arg0);
            if (arg0.equals(res)){
//                res.setTC(middleCode.getNXQ());
//                res.setFC(middleCode.getNXQ()+1);
                middleCode.gencode(res.getId()+"jnz","jnz","变量入口:"+arg0.getVal(),null,"0");
                middleCode.gencode(res.getId()+"j","j",null,null,"0");
            }
        }
        return res;
    }

    private TempVariable bo_B1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && judgeD(token)) {
            String op = token.getType();
            math(tree, tokens);
            TempVariable arg1 = AR();
            TempVariable res = middleCode.newtemp();
            res.setTC(middleCode.getNXQ());
            res.setFC(middleCode.getNXQ()+1);
            middleCode.gencode(res.getId()+"j"+op,"j"+op,arg0.getVal(),arg1.getVal(),"0");
            middleCode.gencode(res.getId()+"j","j",null,null,"0");
            return res;
        }
        return arg0;
    }

    private void AS() {
        tree.addChild(new TreeNode("赋值表达式"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "=".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少=", tokens.getCurToken()));
            }
            expression();
        }
        tree.traceBack();
    }

    private Const c;

    private Variable v;

    private Function f;

    public void DE() {
        tree.addChild(new TreeNode("声明语句"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())) {
            c = new Const();
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && judgeE(token)) {
                math(tree, tokens);
                c.setType(token.getType());
            } else {
                tokens.match();
                exceptions.add(new ParseException("声明类型", tokens.getCurToken()));
            }
            de_E();
        } else if (token != null && judgeE(token)) {
            v = new Variable();
            f = new Function();
            v.setType(token.getType());
            f.setReturnType(token.getType());
            math(tree, tokens);
            DE1();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    private void DE1() {
        // tree.addChild(new TreeNode("DE1"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())) {
            f.setFunctionName(token.getVal().toString());
            v.setName(token.getVal().toString());
            math(tree, tokens);
            DE2();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void DE2() {
        // tree.addChild(new TreeNode("DE2"));
        Token token = tokens.getCurToken();
        if (token != null && "(".equals(token.getType())) {
            tree.addChild(new TreeNode("函数声明"));
            math(tree, tokens);
            de_H();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
            tree.traceBack();
            middleCode.getTable().addFun(f);
            f = new Function();
        } else if (token != null && "=".equals(token.getType())) {
            tree.addChild(new TreeNode("变量声明"));
            math(tree, tokens);
            expression();
            de_F1();
            tree.traceBack();
        } else if (token != null && (";".equals(token.getType()) || ",".equals(token.getType()))) {
            de_F1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    private void de_A() {
        tree.addChild(new TreeNode("值声明"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()))) {
            de_C();
        } else if (token != null && (judgeE(token))) {
            de_D();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    public void de_C() {
        tree.addChild(new TreeNode("常量声明"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()))) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && judgeE(token)) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("声明类型", tokens.getCurToken()));
            }
            de_E();
        } else if (token != null && (judgeE(token))) {
            de_D();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    private Const getNewConst(Const c) {
        Const newC = new Const();
        newC.setType(c.getType());
        return newC;
    }

    private void de_E() {
        tree.addChild(new TreeNode("常量声明列表"));
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))) {
            c.setName(token.getVal().toString());
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "=".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("声明类型", tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            c.setVal(token.getVal().toString());
            middleCode.getTable().addConst(c);
            if (token != null && judgeConst(token)) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("声明类型", tokens.getCurToken()));
            }
            c = getNewConst(c);
            de_E1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void de_E1() {
        // tree.addChild(new TreeNode("de_E1"));
        Token token = tokens.getCurToken();
        if (token != null && (";".equals(token.getType()))) {
            math(tree, tokens);
        } else if (token != null && (",".equals(token.getType()))) {
            math(tree, tokens);
            de_E();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    public void de_D() {
        tree.addChild(new TreeNode("变量声明"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            math(tree, tokens);
            de_F();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }


    private void de_F() {
        // tree.addChild(new TreeNode("de_F"));
        de_G();
        de_F1();
        // tree.traceBack();
    }

    private void de_F1() {
        // tree.addChild(new TreeNode("de_F1"));
        Token token = tokens.getCurToken();
        if (token != null && (";".equals(token.getType()))) {
            math(tree, tokens);
        } else if (token != null && (",".equals(token.getType()))) {
            math(tree, tokens);
            de_F();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void de_G() {
        // tree.addChild(new TreeNode("de_G"));
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))) {
            math(tree, tokens);
            de_G1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void de_G1() {
        // tree.addChild(new TreeNode("de_G1"));
        Token token = tokens.getCurToken();
        if (token != null && ("=".equals(token.getType()))) {
            math(tree, tokens);
            expression();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    private void de_B() {
        // tree.addChild(new TreeNode("de_B"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符", tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            de_H();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void de_H() {
        // tree.addChild(new TreeNode("de_H"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token) || "标识符".equals(token.getType()))) {
            de_I();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void de_I() {
        // tree.addChild(new TreeNode("de_I"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            f.getParameterType().add(token.getVal().toString());
            math(tree, tokens);
            de_I1();
        } else if (token != null && "标识符".equals(token.getType())) {
            math(tree, tokens);
            de_I1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void de_I1() {
        // tree.addChild(new TreeNode("de_I1"));
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())) {
            math(tree, tokens);
            de_I();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    private void EX() {
        tree.addChild(new TreeNode("执行语句"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())) {
            ex_A();
        } else if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            ex_B();
        } else if (token != null && ("{".equals(token.getType()))) {
            math(tree, tokens);
            ex_I();
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少}", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    private void ex_A() {
        // tree.addChild(new TreeNode("ex_A"));
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())) {
            math(tree, tokens);
            ex_A1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_A1() {
        // tree.addChild(new TreeNode("ex_A1"));
        Token token = tokens.getCurToken();
        if (token != null && "=".equals(token.getType())) {
            math(tree, tokens);
            expression();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        } else if (token != null && "(".equals(token.getType())) {
            math(tree, tokens);
            ar_D(null);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_B() {
        // tree.addChild(new TreeNode("ex_B"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            ex_D();
        } else if (token != null && "for".equals(token.getType())) {
            ex_E();
        } else if (token != null && "while".equals(token.getType())) {
            ex_F();
        } else if (token != null && "do".equals(token.getType())) {
            ex_G();
        } else if (token != null && "return".equals(token.getType())) {
            ex_H();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_C() {
        // tree.addChild(new TreeNode("ex_C"));
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())) {
            math(tree, tokens);
            ex_I();
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少}", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_I() {
        // tree.addChild(new TreeNode("ex_I"));
        Statement();
        ex_I1();
        // tree.traceBack();
    }

    private void ex_I1() {
        // tree.addChild(new TreeNode("ex_I1"));
        Token token = tokens.getCurToken();
        if (token != null && ("{".equals(token.getType()) || "const".equals(token.getType()) || judgeE(token) || "标识符".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            ex_I();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_D() {
        tree.addChild(new TreeNode("<if 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            Statement();
            ex_D1();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }


    private void ex_D1() {
        // tree.addChild(new TreeNode("ex_D1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())) {
            math(tree, tokens);
            Statement();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_E() {
        tree.addChild(new TreeNode("<for 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "for".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            ex_K();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }


    private void ex_F() {
        tree.addChild(new TreeNode("<while 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "while".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            ex_K();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }


    private void ex_G() {
        tree.addChild(new TreeNode("<do while 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "do".equals(token.getType())) {
            math(tree, tokens);
            ex_L();
            token = tokens.getCurToken();
            if (token != null && "while".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少while", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    private void ex_H() {
        tree.addChild(new TreeNode("<return 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "return".equals(token.getType())) {
            math(tree, tokens);
            ex_H1();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    private void ex_H1() {
        // tree.addChild(new TreeNode("ex_H1"));
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())) {
            math(tree, tokens);
        } else if (token != null && ("(".equals(token.getType()) || "!".equals(token.getType()) || "标识符".equals(token.getType()) || judgeConst(token))) {
            expression();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_K() {
        // tree.addChild(new TreeNode("ex_K"));
        Token token = tokens.getCurToken();
        if (token != null && "const".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && (judgeE(token))) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少类型", tokens.getCurToken()));
            }
            de_E();
        } else if (token != null && (judgeE(token))) {
            math(tree, tokens);
            DE1();
        } else if (token != null && "标识符".equals(token.getType())) {
//            math(tree,tokens);
            DE1();
        } else if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()))) {
            ex_M();
        } else if (token != null && "{".equals(token.getType())) {
            ex_L();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    private void ex_L() {
        // tree.addChild(new TreeNode("ex_L"));
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())) {
            math(tree, tokens);
            ex_N();
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少}", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_N() {
        // tree.addChild(new TreeNode("ex_N"));
        ex_K();
        ex_N1();
        // tree.traceBack();
    }

    private void ex_N1() {
        // tree.addChild(new TreeNode("ex_N1"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || judgeE(token) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()) || "{".equals(token.getType()) || "标识符".equals(token.getType()))) {
            ex_N();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    private void ex_M() {
        // tree.addChild(new TreeNode("ex_M"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            ex_O();
        } else if (token != null && "for".equals(token.getType())) {
            ex_E();
        } else if (token != null && "while".equals(token.getType())) {
            ex_F();
        } else if (token != null && "do".equals(token.getType())) {
            ex_G();
        } else if (token != null && "return".equals(token.getType())) {
            ex_H();
        } else if (token != null && "break".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        } else if (token != null && "continue".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void ex_O() {
        // tree.addChild(new TreeNode("ex_O"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            ex_K();
            ex_O1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    private void ex_O1() {
        // tree.addChild(new TreeNode("ex_O1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())) {
            math(tree, tokens);
            ex_K();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    public void Statement() {
        tree.addChild(new TreeNode("语句"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || judgeE(token))) {
            DE();
        } else if (token != null && ("标识符".equals(token.getType()) || "{".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            EX();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }


    public void Function() {
        tree.addChild(new TreeNode("函数"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符", tokens.getCurToken()));
            }
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            fu_A();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            ex_C();
        } else if (token != null) {
            // error
        }
        tree.traceBack();
    }

    private void fu_A() {
        // tree.addChild(new TreeNode("fu_A"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            fu_B();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void fu_B() {
        // tree.addChild(new TreeNode("fu_B"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符", tokens.getCurToken()));
            }
            fu_B1();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }

    private void fu_B1() {
        // tree.addChild(new TreeNode("fu_B1"));
        Token token = tokens.getCurToken();
        if (token != null && (",".equals(token.getType()))) {
            math(tree, tokens);
            fu_B();
        } else if (token != null) {
            // error
        }
        // tree.traceBack();
    }


    public void Program() {
        tree.addChild(new TreeNode("程序"));
        DE();
        Token token = tokens.getCurToken();
        if (token != null && ("main".equals(token.getVal().toString()))) {
            math(tree, tokens);
        } else {
            tokens.match();
            exceptions.add(new ParseException("缺少main", tokens.getCurToken()));
        }
        token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))) {
            math(tree, tokens);
        } else {
            tokens.match();
            exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
        }
        token = tokens.getCurToken();
        if (token != null && (")".equals(token.getType()))) {
            math(tree, tokens);
        } else {
            tokens.match();
            exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
        }
        ex_C();
        po_A();
        tree.traceBack();
    }

    private void po_A() {
        // tree.addChild(new TreeNode("po_A"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            Function();
            po_A();
        } else if (token != null) {

        }
        // tree.traceBack();
    }


}

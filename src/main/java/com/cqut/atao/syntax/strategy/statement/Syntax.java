package com.cqut.atao.syntax.strategy.statement;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.middle.MiddleCode;
import com.cqut.atao.middle.table.Const;
import com.cqut.atao.middle.table.Function;
import com.cqut.atao.middle.table.TempVariable;
import com.cqut.atao.middle.table.Variable;
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

    // 变量的作用域
    private int socp = 0;

    // 函数名
    private String functionName;

    // 语法树
    private MyTree tree;

    // tokens信息
    private TokenList<Token> tokens;

    // 异常信息
    private List<Exception> exceptions;

    // 中间代码对象
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


    public TempVariable expression() {
        TempVariable arg0 = middleCode.newtemp();
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
        } else if (token != null && (judgeD(token) || "*".equals(token.getType()) || "/".equals(token.getType()) || "%".equals(token.getType()) || "+".equals(token.getType()) || "-".equals(token.getType()) || judgeD(token) || "&&".equals(token.getType()) || "||".equals(token.getType()))) {
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
            // 函数参数
            ar_D();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            // 函数调用
            TempVariable res = middleCode.newtemp();
            middleCode.gencode(functionName,"call",arg0.getVal(),"",res.getVal());
            // 增加返回值变量
            Variable variable = new Variable(res);
            variable.setType("int");
            middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);

            res = ar_A1(res);
            res = AR1(res);
            return expression1(res);
        } else if (token != null && "=".equals(token.getType())) {
            String op = token.getType();
            math(tree, tokens);
            TempVariable res = expression();
            middleCode.gencode(functionName,op,res.getVal(),"",arg0.getVal());
//            middleCode.fillVar(arg0.getVal(),res.getVal());
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
            res.setTC(middleCode.getNXQ());
            res.setFC(middleCode.getNXQ()+1);
            middleCode.gencode(functionName,"j"+op,arg0.getVal(),arg1.getVal(),"0");
            middleCode.gencode(functionName,"j","","","0");
            return expression2(res);
        } else if (token != null && ("&&".equals(token.getType()) || "||".equals(token.getType()))) {
            arg0.setTC(middleCode.getNXQ());
            arg0.setFC(middleCode.getNXQ()+1);
            middleCode.gencode(functionName,"jnz",arg0.getVal(),"","0");
            middleCode.gencode(functionName,"j","","","0");

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
            middleCode.gencode(functionName,op,arg1.getVal(),arg2.getVal(),res.getVal());
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
            middleCode.gencode(functionName,op,arg1.getVal(),arg2.getVal(),res.getVal());
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
            // 临时变量
//            Variable variable = new Variable(var);
//            middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
        } else if (token != null && ("字符串".equals(token.getType()) || "字符".equals(token.getType()) || "整数".equals(token.getType()) || "实数".equals(token.getType()))) {
            var = new TempVariable(token.getVal().toString());
            math(tree, tokens);
        } else if (token != null && ("标识符".equals(token.getType()))) {
            TempVariable arg0 = new TempVariable(token.getVal().toString());
            math(tree, tokens);
            var = ar_B1(arg0);
        }
        return var;
    }

    private TempVariable ar_B1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()))) {
            TempVariable res = middleCode.newtemp();
            math(tree, tokens);
            ar_D();
            middleCode.gencode(functionName,"call",arg0.getVal(),"",res.getVal());
            // 增加返回值变量
            Variable variable = new Variable(res);
            variable.setType("int");
            middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);

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
            TempVariable arg = new TempVariable(token.getVal().toString());
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            ar_D();
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

    private void ar_D() {
        Token token = tokens.getCurToken();
        if (token != null && ("(".equals(token.getType()) || "标识符".equals(token.getType()) || judgeConst(token) || "!".equals(token.getType()))) {
            ar_E();
        }
    }

    private void ar_E() {
        TempVariable arg1 = expression();
        // 增加临时变量
        Variable variable = new Variable(arg1);
        variable.setType("int");
        middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);
        if (arg1 != null){
            middleCode.gencode(functionName,"para",arg1.getVal(),"","");
        }
        ar_E1();
    }

    private void ar_E1() {
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())) {
            math(tree, tokens);
            ar_E();
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
//            res.setType(arg0.getType());
            middleCode.gencode(functionName,op,arg0.getVal(),arg1.getVal(),res.getVal());
            return res;
        }
        return arg0;
    }

    private TempVariable bo_A() {
        TempVariable arg0 = bo_B();
        TempVariable res = bo_A1(arg0);
        return res;
    }

    private TempVariable bo_A1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && "&&".equals(token.getType())) {
            math(tree, tokens);
            middleCode.buckpatch(arg0.getTC(),middleCode.getNXQ());
            TempVariable arg1 = bo_A();
            middleCode.merge(arg0.getFC(),arg1.getFC());
            arg1.setFC(arg0.getFC());
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
                res.setTC(middleCode.getNXQ());
                res.setFC(middleCode.getNXQ()+1);
                middleCode.gencode(functionName,"jnz",arg0.getVal(),"","0");
                middleCode.gencode(functionName,"j","","","0");
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
            middleCode.gencode(functionName,"j"+op,arg0.getVal(),arg1.getVal(),"0");
            middleCode.gencode(functionName,"j","","","0");
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
            v = new Variable(socp);
            f = new Function();
            v.setType(token.getType());
            f.setReturnType(token.getType());
            math(tree, tokens);
            DE1(null);
        }
        tree.traceBack();
    }

    private TempVariable DE1(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())) {
            arg = middleCode.newtemp();
            arg.setVal(token.getVal().toString());
            f.setFunctionName(token.getVal().toString());
            v.setName(token.getVal().toString());
            math(tree, tokens);
            arg = DE2(arg);
        }
        return arg;
    }

    private TempVariable DE2(TempVariable arg) {
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
            TempVariable arg1 = expression();
            // 添加变量
            v.setVal(arg1.getVal());
            de_F1();
            tree.traceBack();
            middleCode.gencode(functionName,"=",arg1.getVal(),"",arg.getVal());
//            middleCode.fillVar(arg.getVal(),arg1.getVal());
            return arg1;
        } else if (token != null && (";".equals(token.getType()) || ",".equals(token.getType()))) {
            de_F1();
        }
        return arg;
    }


    private void de_A() {
        tree.addChild(new TreeNode("值声明"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()))) {
            de_C();
        } else if (token != null && (judgeE(token))) {
            de_D();
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
        }
        tree.traceBack();
    }

    private Const getNewConst(Const c) {
        Const newC = new Const();
        newC.setType(c.getType());
        return newC;
    }

    private Variable getNewVar(Variable c) {
        Variable newV = new Variable(socp);
        newV.setType(c.getType());
        return newV;
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
        }
    }

    private void de_E1() {
        Token token = tokens.getCurToken();
        if (token != null && (";".equals(token.getType()))) {
            math(tree, tokens);
        } else if (token != null && (",".equals(token.getType()))) {
            math(tree, tokens);
            de_E();
        }
    }

    public void de_D() {
        tree.addChild(new TreeNode("变量声明"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            math(tree, tokens);
            de_F();
        }
        tree.traceBack();
    }


    private void de_F() {
        de_G();
        de_F1();
    }

    private void de_F1() {
        Token token = tokens.getCurToken();
        if (token != null && (";".equals(token.getType()))) {
            middleCode.getTable().addVar(functionName,v);
            v = new Variable(socp);
            math(tree, tokens);
        } else if (token != null && (",".equals(token.getType()))) {
            middleCode.getTable().addVar(functionName,v);
            v = getNewVar(v);
            math(tree, tokens);
            de_F();
        }
    }

    private void de_G() {
        Token token = tokens.getCurToken();
        if (token != null && ("标识符".equals(token.getType()))) {
            v.setName(token.getVal().toString());
            math(tree, tokens);
            de_G1();
        }
    }

    private void de_G1() {
        Token token = tokens.getCurToken();
        if (token != null && ("=".equals(token.getType()))) {
            math(tree, tokens);
            TempVariable tmp = expression();
            v.setVal(tmp.getVal());
        }
    }


    private void de_B() {
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
        }
    }

    private void de_H() {
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token) || "标识符".equals(token.getType()))) {
            de_I();
        }
    }

    private void de_I() {
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            f.getParameterType().add(token.getVal().toString());
            math(tree, tokens);
            de_I1();
        } else if (token != null && "标识符".equals(token.getType())) {
            math(tree, tokens);
            de_I1();
        }
    }

    private void de_I1() {
        Token token = tokens.getCurToken();
        if (token != null && ",".equals(token.getType())) {
            math(tree, tokens);
            de_I();
        }
    }


    public TempVariable EX() {
        tree.addChild(new TreeNode("执行语句"));
        Token token = tokens.getCurToken();
        TempVariable arg = middleCode.newtemp();
        if (token != null && "标识符".equals(token.getType())) {
            arg = ex_A(arg);
        } else if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            arg = ex_B(arg);
        } else if (token != null && ("{".equals(token.getType()))) {
            socp++;
            math(tree, tokens);
            arg = ex_I(arg);
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少}", tokens.getCurToken()));
            }
            socp--;
        }
        tree.traceBack();
        return arg;
    }

    private TempVariable ex_A(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && "标识符".equals(token.getType())) {
            arg = new TempVariable();
            arg.setVal(token.getVal().toString());
            math(tree, tokens);
            arg = ex_A1(arg);
        }
        return arg;
    }

    private TempVariable ex_A1(TempVariable arg0) {
        Token token = tokens.getCurToken();
        if (token != null && "=".equals(token.getType())) {
            math(tree, tokens);
            TempVariable arg1 = expression();
            // 添加临时变量
            Variable variable = new Variable(arg1);
            variable.setType("int");
            middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);

            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
            middleCode.gencode(functionName,"=",arg1.getVal(),"",arg0.getVal());
            return arg1;
        } else if (token != null && "(".equals(token.getType())) {
            math(tree, tokens);
            ar_D();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            TempVariable res = middleCode.newtemp();
            middleCode.gencode(functionName,"call",arg0.getVal(),"",res.getVal());
            // 增加返回值变量
            Variable variable = new Variable(res);
            variable.setType("int");
            middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);

            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
            return res;
        }
        return arg0;
    }

    private TempVariable ex_B(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            arg = ex_D(arg);
            return arg;
        } else if (token != null && "for".equals(token.getType())) {
            arg = ex_E(arg);
            return arg;
        } else if (token != null && "while".equals(token.getType())) {
            arg = ex_F(arg);
            return arg;
        } else if (token != null && "do".equals(token.getType())) {
            arg = ex_G(arg);
            return arg;
        } else if (token != null && "return".equals(token.getType())) {
            arg = ex_H(arg);
            return arg;
        }
        return arg;
    }

    private TempVariable ex_C(TempVariable arg) {
        socp++;
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())) {
            math(tree, tokens);
            arg = ex_I(arg);
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少}", tokens.getCurToken()));
            }
        }
        socp--;
        return arg;
    }

    private TempVariable ex_I(TempVariable arg) {
        TempVariable arg1 = Statement();
        TempVariable res = ex_I1(arg1);
        return res;
    }

    private TempVariable ex_I1(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && ("{".equals(token.getType()) || "const".equals(token.getType()) || judgeE(token) || "标识符".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            ex_I(arg);
        }
        return arg;
    }

    public TempVariable ex_D(TempVariable arg) {
        tree.addChild(new TreeNode("<if 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            TempVariable res = middleCode.newtemp();
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少(", tokens.getCurToken()));
            }
            TempVariable E = expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }

            middleCode.buckpatch(E.getTC(),middleCode.getNXQ());
            res = Statement();
            res.setFC(E.getFC());
            arg = ex_D1(res);
        }
        tree.traceBack();
        return arg;
    }


    private TempVariable ex_D1(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())) {
            math(tree, tokens);
            TempVariable arg1 = Statement();
            middleCode.merge(arg1.getFC(),arg.getFC());
            return arg1;
        }
        return arg;
    }

    TempVariable for_arg2 = null;

    TempVariable for_arg3 = null;

    private TempVariable ex_E(TempVariable arg) {
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
            int to = middleCode.getNXQ();
            for_arg2 = expression();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
            for_arg3 = expression();
            int from = middleCode.getNXQ();
            middleCode.gencode(functionName,"j","","","");
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            middleCode.buckpatch(for_arg2.getTC(),middleCode.getNXQ());
            ex_K(arg);
            middleCode.gencode(functionName,"j","","",(for_arg2.getFC()+1)+"");
            middleCode.buckpatch(for_arg2.getFC(),middleCode.getNXQ());
            middleCode.buckpatch(from,to);
        }
        tree.traceBack();
        return arg;
    }


    private TempVariable ex_F(TempVariable arg) {
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
            TempVariable condiation = expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            middleCode.buckpatch(condiation.getTC(),middleCode.getNXQ());
            TempVariable res = ex_K(arg);
            middleCode.gencode(functionName,"j","","",(condiation.getFC()-1)+"");
            middleCode.buckpatch(condiation.getFC(),middleCode.getNXQ());
        }
        tree.traceBack();
        return arg;
    }

    TempVariable do_while_arg = null;

    private TempVariable ex_G(TempVariable arg) {
        tree.addChild(new TreeNode("<do while 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "do".equals(token.getType())) {
            math(tree, tokens);
            int TC = middleCode.getNXQ();
            ex_L(arg);
            token = tokens.getCurToken();
            if (token != null && "while".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少while", tokens.getCurToken()));
            }
            do_while_arg = expression();
            middleCode.buckpatch(do_while_arg.getTC(),TC);
            middleCode.buckpatch(do_while_arg.getFC(),middleCode.getNXQ());
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        }
        tree.traceBack();
        return arg;
    }

    private TempVariable ex_H(TempVariable arg) {
        tree.addChild(new TreeNode("<return 语句>"));
        Token token = tokens.getCurToken();
        if (token != null && "return".equals(token.getType())) {
            math(tree, tokens);
            ex_H1();
        }
        tree.traceBack();
        return arg;
    }

    private void ex_H1() {
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())) {
            middleCode.gencode(functionName,"ret","","","");
            math(tree, tokens);
        } else if (token != null && ("(".equals(token.getType()) || "!".equals(token.getType()) || "标识符".equals(token.getType()) || judgeConst(token))) {
            TempVariable res = expression();
            // 增加临时变量
            Variable variable = new Variable(res);
            variable.setType("int");
            middleCode.getTable().getFunctionTable().get(functionName).addVar(variable);

            middleCode.gencode(functionName,"ret","","",res.getVal());
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少;", tokens.getCurToken()));
            }
        }
    }

    private TempVariable ex_K(TempVariable arg) {
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
            arg = DE1(arg);
        } else if (token != null && "标识符".equals(token.getType())) {
             arg = DE1(arg);
        } else if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()))) {
            ex_M(arg);
        } else if (token != null && "{".equals(token.getType())) {
            ex_L(arg);
        }
        return arg;
    }


    private TempVariable ex_L(TempVariable arg) {
        socp++;
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())) {
            math(tree, tokens);
            ex_N(arg);
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少}", tokens.getCurToken()));
            }
//            middleCode.gencode(functionName,"j","","",for_arg2.getTC());
        }
        socp--;
        return arg;
    }

    private void ex_N(TempVariable arg) {
        ex_K(arg);
        ex_N1(arg);
    }

    private void ex_N1(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || judgeE(token) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()) || "{".equals(token.getType()) || "标识符".equals(token.getType()))) {
            ex_N(arg);
        }
    }


    private void ex_M(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            ex_O(arg);
        } else if (token != null && "for".equals(token.getType())) {
            ex_E(arg);
        } else if (token != null && "while".equals(token.getType())) {
            ex_F(arg);
        } else if (token != null && "do".equals(token.getType())) {
            ex_G(arg);
        } else if (token != null && "return".equals(token.getType())) {
            ex_H(arg);
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
        }
    }

    private void ex_O(TempVariable arg) {
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
            TempVariable condition = expression();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            middleCode.buckpatch(condition.getTC(),middleCode.getNXQ());
            ex_K(arg);
            ex_O1(arg);
            middleCode.buckpatch(condition.getFC(),middleCode.getNXQ());
        }
    }


    private void ex_O1(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())) {
            math(tree, tokens);
            ex_K(arg);
        }
    }

    public TempVariable Statement() {
        tree.addChild(new TreeNode("语句"));
        Token token = tokens.getCurToken();
        TempVariable res = middleCode.newtemp();
        if (token != null && ("const".equals(token.getType()) || judgeE(token))) {
            DE();
        } else if (token != null && ("标识符".equals(token.getType()) || "{".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            res = EX();
        }
        tree.traceBack();
        return res;
    }


    public void Function() {
        TempVariable arg = middleCode.newtemp();
        tree.addChild(new TreeNode("函数"));
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            math(tree, tokens);
            token = tokens.getCurToken();
            // 函数定义
            middleCode.gencode(functionName,token.getVal().toString(),"","","");
            functionName = token.getVal().toString();
            // 定义函数入口四元式
            middleCode.getTable().getFunctionTable().get(functionName).setPosition(middleCode.getNXQ());

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
            // 函数的参数
            fu_A();
            token = tokens.getCurToken();
            if (token != null && ")".equals(token.getType())) {
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少)", tokens.getCurToken()));
            }
            ex_C(arg);
        }
        tree.traceBack();
    }

    private void fu_A() {
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            // 参数
            fu_B();
        }
    }

    private void fu_B() {
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            // 参数
            v = new Variable(socp);
            // 类型
            v.setType(token.getType());
            math(tree, tokens);
            token = tokens.getCurToken();
            if (token != null && "标识符".equals(token.getType())) {
                // 参数名
                v.setName(token.getVal().toString());
                // 加入变量表
//                middleCode.getTable().addVar(functionName,v);
                middleCode.getTable().addPara(functionName,v);
                math(tree, tokens);
            } else {
                tokens.match();
                exceptions.add(new ParseException("缺少标识符", tokens.getCurToken()));
            }
            fu_B1();
        }
    }

    private void fu_B1() {
        Token token = tokens.getCurToken();
        if (token != null && (",".equals(token.getType()))) {
            math(tree, tokens);
            fu_B();
        }
    }


    public void Program() {
        TempVariable arg = middleCode.newtemp();
        tree.addChild(new TreeNode("程序"));
        DE();
        Token token = tokens.getCurToken();
        if (token != null && ("main".equals(token.getVal().toString()))) {
            // 函数定义
            middleCode.gencode(functionName,"main","","","");
            functionName = "main";
            Function function = new Function();
            function.setFunctionName("main");
            middleCode.getTable().addFun(function);
            // 函数入口
            middleCode.getTable().getFunctionTable().get(functionName).setPosition(middleCode.getNXQ());

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
        ex_C(arg);
        middleCode.gencode(functionName,"sys","","","");
        po_A(arg);
        tree.traceBack();
    }

    private void po_A(TempVariable arg) {
        Token token = tokens.getCurToken();
        if (token != null && (judgeE(token))) {
            Function();
            po_A(arg);
        }
    }


}
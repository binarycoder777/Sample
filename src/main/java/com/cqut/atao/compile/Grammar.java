package com.compile;

import com.cqut.atao.compile.Compile;

public class Grammar {
    public static int index = -1;  //token列表下标
    public static int token = 0;  //token值
    public static int space = 0;  //空格数
    public static String str = "";  //语法树
    public static String error_str = "";  //语法错误的句子
    public static int current_line = 0;  //当前token的行数

    private static void output(String s) {
        String str1 = "";
        for (int i = 0; i < space; i++)
            str1 += "--";
        str1 += s;
        str += str1 + "\n";
    }

    private static void error(Integer line, String s) {
        error_str += "第" + line + "行, " + s + "\n";
    }

    //    取出token值
    private static int getNextToken() {
        current_line = Compile.list.get(++index).getLine();
        if ((index - 1) >= 0 && current_line != Compile.list.get(index - 1).getLine())
            current_line = Compile.list.get(index - 1).getLine();
        return Compile.list.get(index).getValue();
    }

    //    处理单个因子：括号，单目取负，单个常量变量,即F
    private static void factor() {
        token = Compile.list.get(index + 1).getValue();
//        是函数调用
        if (token == Compile.dictionary.get("(")) {
//            当前的
            token = Compile.list.get(index).getValue();
            if (token != 700) {
                error(current_line, "函数调用不是标识符");
                index--;
            }
            token = getNextToken();
            if (token != Compile.dictionary.get("(")) {
                error(current_line, "函数调用没有(");
                index--;
            }
            token = Compile.list.get(index + 1).getValue();
            if (token == Compile.dictionary.get(")")) {
                index++;
                return;
            } else {
//              实参列表
                argument();
                token = getNextToken();
                if (token != Compile.dictionary.get(")")) {
                    error(current_line, "实参列表缺少）");
                    index--;
                }
            }
            return;
        }
//        当前的index
        token = Compile.list.get(index).getValue();
//        是（算术表达式）
        if (token == Compile.dictionary.get("(")) {   //处理括号
            aexpr();    //处理中间的E
            token = getNextToken();
            if (token != Compile.dictionary.get(")")) {
                index--;
                error(current_line, "缺少)");
            }
        } else if (token == Compile.dictionary.get("-")) {//处理单目取负
            aexpr();
//            常量(数值型 字符型) 或者 标识符
        } else if (token == 700 || token == 400 || token == 500) {
            return;
        } else {
            error(current_line, "算术表达式语法错误");
            index--;  //-------------------应该要减
        }
    }


    //处理乘除，取负数和括号部分，即T
    private static void term() {
        factor();      //处理因子
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get("*") || token == Compile.dictionary.get("/")) {
                token = getNextToken();
                factor();
            } else {
                index--;    //退出当前取出的token
                break;
            }
        }
    }


    //    处理算术表达式,
    public static void aexpr() {
        space++;
        output("开始算术表达式");
        token = getNextToken();
        term();
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get("+") || token == Compile.dictionary.get("-")
                    || token == Compile.dictionary.get("%")) { //匹配处理加减号
                token = getNextToken();    //读取下一token
                term();
            } else {  //表达式处理完毕
                index--;    //退出当前取出的token
                break;
            }
        }
        output("结束算术表达式");
        space--;
    }

    //--------------------------------------------------------
//    处理关系表达式
    public static void relational_expression() {
        space++;
        output("开始关系表达式");
        aexpr();
        relational_operator();
        aexpr();
        output("结束关系表达式");
        space--;
    }

    private static void relational_operator() {
        token = getNextToken();
        if (!(token == Compile.dictionary.get(">") || token == Compile.dictionary.get("<")
                || token == Compile.dictionary.get(">=") || token == Compile.dictionary.get("<=")
                || token == Compile.dictionary.get("==") || token == Compile.dictionary.get("!="))) {
            error(current_line, "不是关系运算符");
        }
    }

    //--------------------------------------------------------
//    布尔因子
    private static void bool_factor() {
        token = getNextToken();
        if (token == Compile.dictionary.get("!")) {
            bool_expression();
        } else {
            index--;
            aexpr();
            index++;
            if ((token == Compile.dictionary.get(">") || token == Compile.dictionary.get("<")
                    || token == Compile.dictionary.get(">=") || token == Compile.dictionary.get("<=")
                    || token == Compile.dictionary.get("==") || token == Compile.dictionary.get("!="))) {
                aexpr();
            } else {
                index--;
            }
        }
    }

    //    布尔项
    private static void bool_item() {
        bool_factor();
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get("&&")) {
                bool_factor();  //处理布尔项
            } else {
                index--;
                break;
            }
        }
    }

    //    处理布尔表达式
    public static void bool_expression() {
        space++;
        output("开始布尔表达式");
        bool_item();  //处理布尔项
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get("||")) {
                bool_item();  //处理布尔项
            } else {
                index--;
                break;
            }
        }
        output("结束布尔表达式");
        space--;
    }

    //------------------------------------------------
    //处理赋值表达式
    public static void assignment_expression() {
        space++;
        output("开始赋值表达式");
        token = getNextToken();
        if (token != 700) {
            error(current_line, "不是标识符即不是赋值表达式");
            index--;
        }
        token = getNextToken();
        if (!(token == Compile.dictionary.get("=") || token == Compile.dictionary.get("+=") ||
                token == Compile.dictionary.get("-=") || token == Compile.dictionary.get("*=") ||
                token == Compile.dictionary.get("/="))) {
//                开始判断是否为表达式 算术|关系|布尔|赋值
            error(current_line, "不是等号");
            index--;
        }
        expression();


        output("结束赋值表达式");
        space--;
    }

    //表达式语句
    public static void expression() {
//        提前读判断第二个符号  如果是等号就是赋值，否则就是布尔
        token = Compile.list.get(index + 2).getValue();
//        赋值套赋值
        if (token == Compile.dictionary.get("=")){
            space++;output("开始赋值表达式");
            assignment_expression();
            output("结束赋值表达式");space--;
        }

        else
            bool_expression();
    }

    //---------------------------------------------------


    //    语句   ？？？？？？？？？？？？？？？
    private static void statement() {
        token = getNextToken();
//      声明语句  值声明的常量声明
        if (token == Compile.dictionary.get("const")) {
            token = getNextToken();
            if (!(token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") ||
                    token == Compile.dictionary.get("float"))) {
                error(current_line, "常量声明类型错误");
                index--;
            }
            token = getNextToken();
            if (token != 700) {
                error(current_line, "常量类型后面不是标识符");
                index--;
            }
            token = getNextToken();
            if (token != Compile.dictionary.get("=")) {
                error(current_line, "常量声明中标识符后不是等号");
                index--;
            }
            //  常量声明表
            constant_declaration_list();
//            变量声明和函数声明
        } else if (token == Compile.dictionary.get("void")) {
//            肯定是函数声明
            function_declaration();

        } else if (token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") ||
                token == Compile.dictionary.get("float")) {
//            可能是函数声明也可能是变量声明，需根据标识符后面是否是括号来进一步确认
            token = getNextToken();//这里可以判断是否是标识符，但我懒得判断了
            token = getNextToken();
//            有括号肯定是函数声明，
            if (token == Compile.dictionary.get("(")) {
                index -= 2;
                function_declaration();
//                不是括号那就是变量声明
            } else {
                index -= 2;
                variable_declarations();
            }

        } else {
//            执行语句  分为数据处理 控制 复合
////            提前读一个  但index值不会发生改变
//            token = Compile.list.get(index + 1).getValue();
//            是复合
            if (token == Compile.dictionary.get("{")) {
                compound_sentence();
//                是控制
            } else if (token == Compile.dictionary.get("if")) {
                if_statements();
            } else if (token == Compile.dictionary.get("while")) {
                while_statements();
            } else if (token == Compile.dictionary.get("do")) {
                do_while_statements();
            } else if (token == Compile.dictionary.get("for")) {
                for_statements();
            } else if (token == Compile.dictionary.get("return")) {
                return_statements();
//                是数据处理
            } else if (token == 700) {
                token = getNextToken();
                if (token == Compile.dictionary.get("=")) {
//                    这里可以写开始处理赋值表达式
                    expression();
                } else if (token == Compile.dictionary.get("(")) {
                    token = Compile.list.get(index + 1).getValue();
//                    函数调用且是无参只有（）
                    if (token == Compile.dictionary.get(")")) {
                        index++;
                        // 函数调用还要处理最后的;分号
                        token = getNextToken();
                        if (token != Compile.dictionary.get(";")) {
                            error(current_line, "末尾差分号");
                            index--;
                        }
                        return;
                    } else {
                        argument();
                        token = getNextToken();
                        if (token != Compile.dictionary.get(")")) {
                            error(current_line, "有参函数调用缺少）");
                            index--;
                        }
                    }

                } else {
                    error(current_line, "错误 数据处理中的标识符后面不是=或（");
                }
                // 数据处理语句 还要处理最后的;分号
                token = getNextToken();
                if (token != Compile.dictionary.get(";")) {
                    error(current_line, "末尾差分号");
                    index--;
                }
            } else if(token == Compile.dictionary.get(";")){
                return ; //只有一个分号直接跳过
            }
            else{
                error(current_line, "他不是语句");
//                index--;  这里不能减
            }
        }

    }

    //    实参
    private static void argument() {
        expression();
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get(",")) {
                argument();
            } else {
                index--;
                break;
            }
        }
    }

    // 变量声明去除了变量类型前面已经判断了的在分之前   即变量声明表
    private static void variable_declarations() {
        token = getNextToken();
        if (token != 700) {
            error(current_line, "变量声明左边不是变量");
            index--;
        }
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get("=")) {
                expression();
            } else {
                index--;
                break;
            }
        }
        token = getNextToken();
        if (token == Compile.dictionary.get(";")) {
            return;
        } else if (token == Compile.dictionary.get(",")) {
            variable_declarations();
        } else {
            error(current_line, "变量声明标识符后不是，或；错误");
            index--;
        }
    }

    //函数声明但去除了函数类型
    private static void function_declaration() {
        space++;
        output("开始函数声明");
        token = getNextToken();
        if (token != 700) {
            error(current_line, "函数声明中函数类型后面不是标识符");
            index--;
        }
        token = getNextToken();
        if (token != Compile.dictionary.get("(")) {
            error(current_line, "函数声明形参的最左边不是(");
            index--;
        }
        //    函数声明形参
        function_declaration_parameter();
        token = getNextToken();
        if (token != Compile.dictionary.get(")")) {
            error(current_line, "函数声明形参的最右边不是)");
            index--;
        }
        token = getNextToken();
        if (token != Compile.dictionary.get(";")) {
            error(current_line, "函数声明没有;");
            index--;
        }
        output("结束函数声明");
        space--;
    }

    //    函数声明形参
    private static void function_declaration_parameter() {
        token = getNextToken();
        if (!(token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") ||
                token == Compile.dictionary.get("float"))) {
            error(current_line, "函数声明形参前变量类型错误");
            index--;
        }
        while (true) {
            token = getNextToken();
            if (token == Compile.dictionary.get(",")) {
                function_declaration_parameter();
            } else {
                index--;
                break;
            }
        }
    }

    //    常量声明表
    private static void constant_declaration_list() {
        token = getNextToken();
        if (token != 500) {
            error(current_line, "常量声明右边不是常量");
            index--;
        }
        token = getNextToken();
        if (token == Compile.dictionary.get(";")) {
            System.out.println("声明语句结束");
        } else if (token == Compile.dictionary.get(",")) {
            constant_declaration_list();
        } else {
            error(current_line, "结尾不是;或,");
        }
    }

    //    语句表  只是中间的  左右括号已经在复合语句判断了
    private static void statement_table() {
        statement(); //语句
//        执行完第一条语句后 如何没有出现}，则后面继续是语句，直至出现}，若token完都没出现则缺少}
        while (true) {
            token = Compile.list.get(index + 1).getValue();
            if (token != Compile.dictionary.get("}"))
                statement();
            else
                break;
        }

    }


    // 复合语句
    private static void compound_sentence() {
        space++;
        output("开始复合语句");
        token = getNextToken();
        if (token != Compile.dictionary.get("{")) {
            error(current_line, "复合语句缺少'{'");
            index--;
        }
        statement_table(); //语句表
        token = getNextToken();
        if (token != Compile.dictionary.get("}")) {
            error(current_line, "复合语句缺少'}'");
            index--;
        }
        output("结束复合语句");
        space--;
    }

    //    if语句
    public static void if_statements() {
        space++;
        output("开始if语句");
        token = getNextToken();
        if (token != Compile.dictionary.get("(")) {
            error(current_line, "if语句缺少'('");
            index--;
        }
//        token = getNextToken();
        expression();  //调用分析表达式
        token = getNextToken();
        if (token != Compile.dictionary.get(")")) {
            error(current_line, "if语句缺少')'");
            index--;
        }
        //if可能只有一条语句可能有括号也可能没括号 所以需要提前判断后面一个是否为{
        token = Compile.list.get(index + 1).getValue();
//        后面是{，则进入复合语句
        if (token == Compile.dictionary.get("{")) {
            compound_sentence();  //处理复合语句
        }
//       否则进入语句。
        else
            statement();
        token = Compile.list.get(index + 1).getValue();
        if (token == Compile.dictionary.get("else")) {    //带有else部分时处理else部分
            token = getNextToken();//吃掉else
            token = Compile.list.get(index + 1).getValue();
//        后面是{，则进入复合语句
            if (token == Compile.dictionary.get("{")) {

                compound_sentence();  //处理复合语句
            }
//       否则进入语句。
            else
                statement();
        }
        output("结束if语句");
        space--;
    }

    //    for语句
    public static void for_statements() {
        space++;
        output("开始for语句");
        token = getNextToken();
        if (token != Compile.dictionary.get("(")) {
            error(current_line, "for后面不是(");
            index--;
        }
        expression();
        token = getNextToken();
        if (token != Compile.dictionary.get(";")) {
            error(current_line, "for的第一个参数后面不是;");
            index--;
        }
        expression();
        token = getNextToken();
        if (token != Compile.dictionary.get(";")) {
            error(current_line, "for的第二个参数后面不是;");
            index--;
        }
        expression();
//                    表达式后面没有分号
        token = getNextToken();
        if (token != Compile.dictionary.get(")")) {
            error(current_line, "for的第三个参数后面不是)");
            index--;
        }
        loop_statements();
        output("结束for语句");
        space--;
    }

    //    while语句
    public static void while_statements() {
        space++;
        output("开始while语句");
        token = getNextToken();
        if (token != Compile.dictionary.get("(")) {
            error(current_line, "while后面不是(");
            index--;
        }
        expression();
        token = getNextToken();
        if (token != Compile.dictionary.get(")")) {
            error(current_line, "while没有)");
            index--;
        }
        loop_statements();
        output("结束while语句");
        space--;
    }

    //    do_while语句
    public static void do_while_statements() {
        space++;
        output("开始do_while语句");
//        循环复用语句
        cyclic_statement();
        token = getNextToken();
        if (token != Compile.dictionary.get("while")) {
            error(current_line, "do_while语句没有while");
            index--;
        }
        while_statements();
//            最后有一个；别忘了
        token = getNextToken();
        if (token != Compile.dictionary.get(";")) {
            error(current_line, "do_while语句最后没有;");
            index--;
        }
        output("结束do_while语句");
        space--;
    }

    private static void cyclic_statement() {
        token = getNextToken();
        if (token != Compile.dictionary.get("{")) {
            error(current_line, "do后面不是{");
            index--;
        }
//            循环语句表
//            循环语句
        loop_statements();
        token = Compile.list.get(index + 1).getValue();
        if (token == Compile.dictionary.get("}")) {
//                说明循环语句结束
            index++;
            return;
//                否则继续循环语句
        } else {
            loop_statements();
        }
    }


    //    return语句
    public static void return_statements() {
        space++;
        output("开始return语句");
        token = getNextToken();
        if (token == Compile.dictionary.get(";"))
            return;
        else {
//            上面把；吃了
            index--;
            expression();
            token = getNextToken();
            if (token != Compile.dictionary.get(";")){
                error(current_line, "return语句最后差;");
                index--;
            }

        }
        output("结束return语句");
        space--;
    }

    //     循环语句
    public static void loop_statements() {
        space++;
        output("开始循环语句");
        token = Compile.list.get(index + 1).getValue();
//        循环用复合语句表
        if (token == Compile.dictionary.get("{")) {
//            里面while循环判断语句直至遇到}
            token = getNextToken();  //吃掉{
            token = Compile.list.get(index + 1).getValue();
            if (token == Compile.dictionary.get("}")) {
                token = getNextToken();
                return;
            }
            while (token != Compile.dictionary.get("}")) {
                statement();
                token = Compile.list.get(index + 1).getValue();
            }
            token = getNextToken();
//            只有一条语句
        } else {
            statement();
        }
        output("结束循环语句");
        space--;
    }

    // 声明语句
    private static boolean declaration_statements() {
        boolean flag = false;
        token = getNextToken();
        //      首先判断是否为声明语句
        if (token == Compile.dictionary.get("const")) {
            space++;
            output("开始常量声明");
            flag = true;
            token = getNextToken();
            if (!(token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") ||
                    token == Compile.dictionary.get("float"))) {
                error(current_line, "常量声明类型错误");
                index--;
            }
            token = getNextToken();
            if (token != 700) {
                error(current_line, "常量类型后面不是标识符");
                index--;
            }
            token = getNextToken();
            if (token != Compile.dictionary.get("=")) {
                error(current_line, "常量声明中标识符后不是等号");
                index--;
            }
            //  常量声明表
            constant_declaration_list();
            output("结束常量声明");
            space--;
//            变量声明和函数声明
        } else if (token == Compile.dictionary.get("void")) {
            flag = true;
//            肯定是函数声明
            function_declaration();

        } else if (token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") ||
                token == Compile.dictionary.get("float")) {
//            可能是函数声明也可能是变量声明，需根据标识符后面是否是括号来进一步确认
            flag = true;
            token = getNextToken();//这里可以判断是否是标识符，但我懒得判断了
            token = getNextToken();
//            有括号肯定是函数声明，
            if (token == Compile.dictionary.get("(")) {
                index -= 2;
                function_declaration();
//                不是括号那就是变量声明
            } else {
                index -= 2;
                variable_declarations();
            }

        }
        return flag;
    }

    //    函数定义
    private static void function() {
        if (index == Compile.list.size() - 1)
            return;
        token = getNextToken();
        if (!(token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") || token ==
                Compile.dictionary.get("float") || token == Compile.dictionary.get("void"))) {
            error(current_line, "不是函数定义错误");
            index--;
        }
        token = getNextToken();
        if (token != 700) {
            error(current_line, "函数定义不是标识符");
            index--;
        }
        token = getNextToken();
        if (token != Compile.dictionary.get("(")) {
            error(current_line, "函数定义没有(");
            index--;
        }
        function_parameters_list();
        token = getNextToken();
        if (token != Compile.dictionary.get(")")) {
            error(current_line, "函数定义没有)");
            index--;
        }
        compound_sentence();
    }

    //函数定义形参列表
    private static void function_parameters_list() {
        token = Compile.list.get(index + 1).getValue();
//        这是空的形参列表
        if (token == Compile.dictionary.get(")"))
            return;
//        函数定义形参
        function_parameters();
    }

    //        函数定义形参
    private static void function_parameters() {
        token = getNextToken();
        if (!(token == Compile.dictionary.get("int") || token == Compile.dictionary.get("char") || token ==
                Compile.dictionary.get("float"))) {
            error(current_line, "函数定义形参的变量类型错误");
            index--;
        }
        token = getNextToken();
        if (token != 700) {
            error(current_line, "函数定义形参的标识符错误");
            index--;
        }
        token = Compile.list.get(index + 1).getValue();
        if (token == Compile.dictionary.get(",")) {
//                    把，吃掉
            token = getNextToken();
            function_parameters();
        } else if (token == Compile.dictionary.get(")")) {
            return;
        } else {
            error(current_line, "函数定义形参列表不是,");
            index--; //--------------------------------------应该要减吧？？
        }
    }

    //    main函数入口
    public static void main_body() {
        index = -1;  //token列表下标
        token = 0;  //token值
        space = 0;  //空格数
        str = "";  //语法树
        error_str = "";  //语法错误的句子
        current_line = 0;  //当前token的行数
//        声明语句 可有可无  有的话也不只一个
        while (true) {
            if (!declaration_statements()) {
                index--;
            }

            if (Compile.list.get(index + 1).getValue() == Compile.dictionary.get("main")) {
                token = getNextToken();
                break;
            }
            if (index == Compile.list.size() - 1) {
                error(current_line, "没有main函数");
                return;
            }
        }

//        main函数主体
        space++;
        output("开始main函数");
        token = getNextToken();
        if (token != Compile.dictionary.get("(")) {
            error(current_line, "main函数没有(");
            index--;
        }
        token = getNextToken();
        if (token != Compile.dictionary.get(")")) {
            error(current_line, "main函数没有)");
            index--;
        }
        compound_sentence();
        output("结束main函数");
        space--;
        while (index != Compile.list.size() - 1) {
            space++;
            output("开始函数定义");
            function();
            output("结束函数定义");
            space--;
        }
    }

}


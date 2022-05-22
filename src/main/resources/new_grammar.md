#--------------------------------------语法规则----------------------------------------------------------------------
<表达式> -> <算术表达式> | <关系表达式> | <布尔表达式> | <赋值表达式>
<算术表达式> -> <项> + <算术表达式> | <项> - <算术表达式> | <项>
<项> -> <因子> * <项> | <因子> / <项> | <因子> % <项> | <因子>
<因子> -> (<算术表达式>) | <常量> | <变量> | <函数调用>
<常量> -> <数值型常量> | <字符型常量>
<变量> -> <标识符>
<函数调用> -> <标识符> (<实参列表>)
<实参列表> -> <实参> | 空
<实参> -> <表达式> | <表达式>,<实参>
<关系表达式> -> <算术表达式> <关系运算符> <算术表达式>
<关系运算符> -> > | < | >= | <= | == | !=
<布尔表达式> -> <布尔项> || <布尔表达式> | <布尔项>
<布尔项> -> <布尔因子> && <布尔项> | <布尔因子>
<布尔因子> -> <算术表达式> | <关系表达式> | !<布尔表达式>
<赋值表达式> -> <标识符> = <表达式>
<语句> -> <声明语句> | <执行语句>
<声明语句> -> <值声明> | <函数声明> | 空
<值声明> -> <常量声明> | <变量声明>
<常量声明> -> const <常量类型> <常量声明列表>
<常量类型> -> int | char | float
<常量声明列表> -> <标识符> = <常量>; | <标识符> = <常量>, <常量声明表>
<变量声明> -> <变量类型> <变量声明表>
<变量声明表> -> <单体变量声明>; | <单体变量声明>, <变量声明表>
<单体变量声明> -> <变量> | <变量> = <表达式>
<变量类型> -> int | float | char | void
<函数声明> -> <函数类型> <标识符> (<函数声明形参列表>)
<函数类型> -> int | float | char | void
<函数声明形参列表> -> <函数声明形参> | 空
<函数声明形参> -> <变量类型> | <变量类型> , <函数声明形参>

<执行语句> -> <数据处理语句> | <控制语句> | <复合语句>
<数据处理语句> -> <赋值语句> | <函数调用语句>
<赋值语句> -> <赋值表达式>;
<函数调用语句> -> <函数调用>;
<控制语句> -> <if 语句> | <for 语句> | <while 语句> | <do while 语句> | <return 语句>
<复合语句> -> {<语句表>}
<语句表> -> <语句> | <语句> <语句表>
<if 语句> -> if (<表达式>) <语句> | if (<表达式>) <语句> else <语句>
<for 语句> -> for (<表达式>; <表达式>; <表达式>) <循环语句>
<while 语句> -> while (表达式)<循环语句>
<do while语句> -> do <循环复合语句> while <表达式>;
<return语句> -> return;|return <表达式>;
<循环语句> -> <声明语句> | <循环执行语句> | <循环复合语句>
<循环复合语句> -> {<循环语句表>}
<循环语句表> -> <循环语句> | <循环语句> <循环语句表>
<循环执行语句> -> <循环if语句> | <for语句> | <while语句> | <do while语句> | <return语句> | <break语句> | <continue语句>
<循环if语句> -> if(<表达式>)<循环语句> | if(<表达式>)<循环语句>else<循环语句>
<break语句> -> break;
<continue语句> -> continue;

<函数定义> -> <函数类型> <标识符> (<函数定义参数列表>) <复合语句>
<函数定义参数列表> -> <函数定义形参> | 空
<函数定义形参> -> <变量类型> <标识符> | <变量类型> <标识符>,<函数定义形参>

<程序> -> <声明语句> main() <复合语句> <函数块>
<函数块> -> <函数定义> <函数块> | 空
#--------------------------------------符号化----------------------------------------------------------------------
### 终结符
数值型常量(实数)		a
字符型常量(字符)		b
标识符		        c
关系运算符			d
常量类型、变量类型、函数类型			    e
常量(字符串常量、字符常量、数值常量)			        f
变量			        g = c
<break语句>		    h
<continue语句>		i
### 非终结符
表达式               Expression
算术表达式		    AR
项		            ar_A
因子		            ar_B
函数调用		        ar_C
实参列表		        ar_D
实参  		        ar_E
关系表达式			RE
布尔表达式		    BO
布尔项		        bo_A
布尔因子		        bo_B
赋值表达式		    AS
语句                 Statement
声明语句			    DE
值声明			    de_A
函数声明			    de_B
常量声明			    de_C
变量声明			    de_D
常量声明列表			de_E
变量声明表			de_F
单体变量声明			de_G
函数声明形参列表		de_H
函数声明形参			de_I
执行语句		        EX
数据处理语句		    ex_A
控制语句		        ex_B
复合语句		        ex_C
<if 语句>		    ex_D
<for 语句>		    ex_E
<while 语句>		    ex_F
<do while 语句>		ex_G
<return 语句>		ex_H
语句表		        ex_I
语句		            ex_J
循环语句		        ex_K
循环复合语句		    ex_L
循环执行语句		    ex_M
循环语句表		    ex_N
循环if语句		    ex_O
函数定义			        Function
函数定义参数列表			fu_A
函数定义形参			    fu_B
程序		    Program
函数块		po_A
#--------------------------------------化简----------------------------------------------------------------------
Expression -> AR | RE | BO | AS
AR -> ar_A + AR | ar_A - AR | ar_A
ar_A -> ar_B * ar_A | ar_B / ar_A | ar_B % ar_A | ar_B
ar_B -> (AR) | f | g | ar_C
ar_C -> c (ar_D)
ar_D -> ar_E | 空
ar_E -> Expression | Expression,ar_E
RE -> AR d AR
BO -> bo_A || BO | bo_A
bo_A -> bo_B && bo_A | bo_A
bo_B -> AR | RE | !BO
AS -> c = Expression

Statement -> DE | EX
DE -> de_A | de_B | 空
de_A -> de_C | de_D
de_C -> const e de_E
de_E -> c = f; | c = f,de_E
de_D -> e de_F
de_F -> de_G; | de_G,de_F
de_G -> g | g = Expression
de_B -> e c (de_H)
de_H -> de_I | 空
de_I -> e | e,de_I
EX -> ex_A | ex_B | ex_C
ex_A -> AS | ar_C
ex_B -> ex_D | ex_E | ex_F | ex_G | ex_H
ex_C -> {ex_I}
ex_I -> Statemnet | Statement ex_I
ex_D -> if (Expression) Statement | if (Expression) Statement else Statement
ex_E -> for(Expression;Expression;Expression) ex_K
ex_F -> while (Expression) ex_K
ex_G -> do ex_I while Expression;
ex_H -> return;  | return Expression;
ex_K -> DE | ex_M | ex_I
ex_I ->{ex_N}
ex_N -> ex_K | ex_K ex_N
ex_M -> ex_O | ex_D | ex_E | ex_F | ex_G | ex_H | break; | continue;
ex_O -> if (Expression) ex_K | if (Expression) ex_K else ex_K
Function -> e c (fu_A) ex_C
fu_A -> fu_B | 空
fu_B -> e c | e c,fu_B
Program -> DE main() ex_C po_A
po_A -> Function po_A | 空
#--------------------------------------LL(1)----------------------------------------------------------------------
// expression -> AR | RE | BO | AS
// expression -> AR | AR d AR | AR bo_A' BO' | AR d AR bo_A' BO' | !BO bo_A' BO' | c = expression
// expression -> AR expression' | !BO bo_A' BO' | c = expression
// expression -> (AR) ar_A' AR' expression' | f ar_A' AR' expression' | g ar_A' AR' expression' | c (ar_D) ar_A' AR' expression' | !BO bo_A' BO' | c = expression
// expression -> (AR) ar_A' AR' expression' | f ar_A' AR' expression' | g ar_A' AR' expression' | !BO bo_A' BO' | c expression'''
expression -> (AR) ar_A' AR' expression' | f ar_A' AR' expression' | !BO bo_A' BO' | c expression''''
expression''''-> ar_A' AR' expression' | expression'''
expression'''-> (ar_D) ar_A' AR' expression' | = expression
// expression' -> d AR | bo_A' BO' | d AR bo_A' BO' | 空
expression' -> d AR expression''| bo_A' BO' | 空
expression''-> bo_A' BO' | 空 

AR -> ar_A AR'
AR' -> +AR | -AR | 空
ar_A -> ar_B ar_A'
ar_A' -> *ar_A | /ar_A | %ar_A | 空
// ar_B -> (AR) | f | c | c (ar_D)
ar_B -> (AR) | f | c ar_B'
ar_B' -> (ar_D) | 空
ar_C -> c (ar_D)
ar_D -> ar_E | 空
ar_E -> expression ar_E'
ar_E' -> ,ar_E | 空
RE -> AR d AR
BO -> bo_A BO'
BO' -> ||BO | 空
bo_A -> bo_B bo_A'
bo_A' -> &&bo_A | 空
// bo_B -> AR | RE | !BO
// bo_B -> AR | AR d AR | !BO
bo_B -> AR bo_B' | !BO
bo_B'-> d AR | 空
AS -> c = expression

First(expression)->{(,f,g,c,!}
First(expression''')->{(,=}
First(expression')->{d,&&,||，空}
Follow(expression')->{}
First(expression'')->{&&,||,空}
Follow(expression')->{}
First(AR)->{(,f,g,c}
First(AR')->{+,-空}
Follow(AR')->{Follow(AR)}
First(ar_A)->{(,f,g,c}
First(ar_A')->{*,/,%,空}
Follow(ar_A')->{Follow(ar_A}
First(ar_B)->{(,f,g,c}
First(ar_C)->{c}
First(ar_D)->{First(expression),空}
Follow(ar_D)->{)}
First(ar_E)->{First(expression}
First(ar_E')->{,,空}
Follow(ar_E')->{Follow(ar_E}
First(RE)->{(,f,g,c}
First(BO)->{(,f,g,c,!}
First(BO')->{||,空}
Follow(BO')->{Follow(BO)}
First(bo_A)->{(,f,g,c,!}
First(bo_A')->{&&,空}
Follow(bo_A')->{Follow(bo_A)}
First(bo_A)->{(,f,g,c,!}
First(AS)->{c}


Statement -> DE | EX
First(Statement)->{const,e,空,c,if,for,while,do,return,{,}
// DE -> de_A | de_B | 空
// DE -> de_C | de_D | de_B | 空
// DE -> const e de_E | e de_F | e c (de_H) | 空
DE -> const e de_E | e DE' | 空
// DE' -> de_F | c (de_H)
// DE' -> c de_G' de_F' | c (de_H)
DE' -> c DE''
// DE''-> de_G' de_F' | (de_H)
DE''-> = Expression de_F' | de_F' | (de_H)
// DE''-> = (AR) ar_A' AR' expression' de_F' | de_F' | (de_H) | = f ar_A' AR' expression' de_F' | = !BO bo_A' BO' de_F' | = c expression'''' de_F'
// DE''-> ( DE''' | f ar_A' AR' expression' de_F' | !BO bo_A' BO' de_F' | c expression'''' de_F' | de_F'
// DE'''-> AR) ar_A' AR' expression' de_F' | de_H)
de_A -> de_C | de_D
de_C -> const e de_E
// de_E -> c = f; | c = f,de_E
de_E -> c = f de_E'
de_E' -> ; | ,de_E
de_D -> e de_F
// de_F -> de_G; | de_G,de_F
de_F -> de_G de_F'
de_F' -> ; | ,de_F
// de_G -> g | g = Expression
de_G -> c de_G'
de_G' -> = Expression | 空
de_B -> e c (de_H)
de_H -> de_I | 空
// de_I -> e | e,de_I
de_I -> e de_I' | c de_I'
de_I' -> ,de_I | 空

// EX -> ex_A | ex_B | ex_C
EX -> ex_A | ex_B | ex_C
// ex_A -> AS | ar_C
// ex_A -> c = expression | c (ar_D)
ex_A -> c ex_A'
ex_A' -> = expression; | (ar_D)
ex_B -> ex_D | ex_E | ex_F | ex_G | ex_H
ex_C -> {ex_I}
// ex_I -> Statemnet | Statement ex_I
ex_I -> Statemnet ex_I'
ex_I' -> ex_I | 空
// ex_D -> if (Expression) Statement | if (Expression) Statement else Statement
ex_D -> if (Expression) Statement ex_D'
ex_D'-> else Statement | 空
ex_E -> for(Expression;Expression;Expression) ex_K
ex_F -> while (Expression) ex_K
ex_G -> do ex_L while Expression;
// ex_H -> return;  | return Expression;
ex_H -> return ex_H'
ex_H'-> ; | Expression;
// ex_K -> DE | ex_M | ex_L
ex_K -> const e de_E | e DE' | ex_M | ex_L | DE' | 空
ex_L ->{ex_N}
// ex_N -> ex_K | ex_K ex_N
ex_N -> ex_K ex_N'
ex_N' -> ex_N | 空
ex_M -> ex_O | ex_E | ex_F | ex_G | ex_H | break; | continue;
// ex_O -> if (Expression) ex_K | if (Expression) ex_K else ex_K
ex_O -> if (Expression) ex_K ex_O'
ex_O'-> else ex_K | 空

Function -> e c (fu_A) ex_C
fu_A -> fu_B | 空
// fu_B -> e c | e c,fu_B
fu_B -> e c fu_B'
fu_B'-> ,fu_B | 空

Program -> DE main() ex_C po_A
po_A -> Function po_A | 空
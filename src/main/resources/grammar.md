#--------------------------------------表达式----------------------------------------------------------------------
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
### 字符
表达式               E
算术表达式		    ar_S
项		            ar_A
因子		            ar_B
常量		            ar_C
变量 	            ar_D
函数调用		        ar_E
数值型常量(实数)		f
字符型常量(字符)		g
标识符		        h
实参列表		        ar_I
实参  		        ar_J
关系表达式			re_A
关系运算符			o
布尔表达式		    bo_A
布尔项		        bo_B
布尔因子		        bo_C
赋值表达式		    as_A
### 化简
E -> ar_S | re_A | bo_A | as_A
ar_S -> ar_A + ar_S | ar_A - ar_S | ar_A
ar_A -> ar_B * ar_A | ar_B / ar_A | ar_B % ar_A | ar_B
ar_B -> (ar_S) | ar_C | ar_D | ar_E
ar_C -> f | g 
ar_D -> h
ar_E -> h (ar_I)
ar_I -> ar_J | 空
ar_J -> E | E,J
re_A -> ar_S o ar_S
o -> > | < | >= | <= | == | !=
bo_A -> bo_B || bo_A | bo_B
bo_B -> bo_C && bo_B | bo_C
bo_C -> ar_S | re_A | !bo_A
as_A -> h=E
### LL(1)文法
// E -> ar_S | re_A | bo_A | as_A
// E -> ar_S | ar_S o ar_S | ar_S bo_B' bo_A' | ar_S o ar_S bo_B' bo_A' | !bo_A bo_B' bo_A'| h=E
// E -> ar_S E' | !bo_A bo_B' bo_A' | h=E
// E -> f ar_A' ar_S' E' | g ar_A' ar_S' E' | (ar_S) ar_A' ar_S' E' | h ar_B' ar_A' ar_S' E' | h=E | !bo_A bo_B' bo_A'
// E' -> 空 | o ar_S | bo_B' bo_A' | o ar_S bo_B' bo_A'


E -> f ar_A' ar_S' E' | g ar_A' ar_S' E' | (ar_S) ar_A' ar_S' E' | h E''' | !bo_A bo_B' bo_A'
E''' -> ar_B' ar_A' ar_S' E' | =E
E' -> o ar_S E'' | bo_B' bo_A'  | 空
E'' -> bo_B' bo_A' | 空

ar_S -> ar_A ar_S'
ar_S' -> +ar_S | -ar_S | 空
ar_A -> ar_B ar_A'
ar_A' -> * ar_A | / ar_A | % ar_A | 空
ar_B -> f | g | h ar_B' | (ar_S)
ar_B' -> (ar_I) | 空
ar_C -> f | g
ar_D -> h
ar_E -> h(ar_I) 
ar_I -> ar_J | 空

ar_J -> E ar_J'
ar_J' -> ,ar_J | 空

re_A -> ar_S o ar_S
o -> > | < | >= | <= | == | !=

bo_A -> bo_B bo_A'
bo_A'->|| bo_A | 空
bo_B -> bo_C bo_B'
bo_B'->&&bo_B | 空

// bo_C -> ar_S | re_A | !bo_A
// bo_C -> ar_S | ar_S o ar_S | !bo_A
bo_C -> ar_S bo_C' | !bo_A
bo_C'-> o ar_S | 空

as_A -> h=E

### First和Follow
First(E) -> {f,g,(,h,!}
Follow(E)->Follow(as_A)并{#}并First(ar_J')并Follow(ar_J)并Follow(E''')->{,,#,)}

First(E''')->{(,*,/,%,+,-,=,o,&&,||,空}
Follow(E''')->Follow(E)->{,,#,)}

First{E'}->{o,&&,||,空}
Follow(E')->Follow(E''')并Follow(E)->{,,#,)}

First(E'')->{&&,||,空}
Follow(E'')->Follow(E')->{,,#,)}

First(ar_S)->{f,g,h,(}
Follow(ar_S)->First(E'')并Follow(E')并{#}并{o}并Follow(ar_S')并Follow(re_A)并First(bo_C')并Follow(bo_C)->{&&,||,,,#,),o}

First(ar_S')->{+,-,空}
Follow(ar_S')->Follow(ar_S)并First(E')并Follow(E)并Follow(E''')->{,,#,),o,&&,||}

First(ar_A)->{f,g,h,(}
Follow(ar_A)->Follow(ar_A')并First(ar_S')并Follow(ar_S)->{+,-,&&,||,,,#,),o}

First(ar_A')->{*,/,%,空}
Follow(ar_A')->Follow(ar_A)并First(ar_S')并First(E')并Follow(E''')并Follow(E)->{,,#,),o,&&,||,+,-}

First(ar_B)->{f,g,h,(}
Follow(ar_B)-> First(ar_A')并Follow(ar_A)->{,,#,*,/,%,+,-,&&,||,,,#,),o}

First(ar_B')->{(,空}
Follow(ar_B')->Follow(ar_B)并First(ar_A')并First(ar_S')并First(E')并Follow(E''')->{,,#,),o,&&,||,+,-,*,/,%}

First(ar_C)->{f,g}

First(ar_D)->{h}

First(ar_E)->{h}

First(ar_I)->{f,g,(,h,!,空}
Follow(ar_I)->{)}

First(ar_J)->{f,g,(,h,!}
Follow(ar_J)->Follow(ar_I)并Follow(ar_J')->{)}

First(ar_J')->{,,空}
Follow(ar_J')->Follow(ar_J)->{)}

First(re_A)->{f,g,h,(}

First(bo_A)->{f,g,h,(,!}
Follow(bo_A)->First(bo_B')并First(bo_A')并Follow(E)并Follow(bo_A')并Follow(bo_C)->{,,#,),&&,||}

First(bo_A')->{||,空}
Follow(bo_A')->Follow(bo_A)并Follow(E'')并Follow(E')->{,,#,),&&,||}

First(bo_B)->{f,g,h,(,!}
Follow(bo_B)->Follow(bo_B')并First(bo_A')并Follow(bo_A)->{,,#,),&&,||}

First(bo_B')->{&&,空}
Follow(bo_B')->Follow(bo_B)并First(bo_A')并Follow(E'')并Follow(E')并Follow(E)->{,,#,),||,&&}

First(bo_C)->{f,g,h,(,!}
Follow(bo_C)-> First(bo_B')并Follow(bo_B)->{,,#,),&&,||}

First(bo_C')->{o,空}
Follow(bo_C')-> Follow(bo_C)->{,,#,),&&,||}

First(as_A)->{h}

#--------------------------------------声明----------------------------------------------------------------------
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
#--------------------------------------语句----------------------------------------------------------------------
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
#--------------------------------------函数----------------------------------------------------------------------
<函数定义> -> <函数类型> <标识符> (<函数定义参数列表>) <复合语句>
<函数定义参数列表> -> <函数定义形参> | 空
<函数定义形参> -> <变量类型> <标识符> | <变量类型> <标识符>,<函数定义形参>
#-----------------------------------------程序-------------------------------------------------------------------
<程序> -> <声明语句> main() <复合语句> <函数块>
<函数块> -> <函数定义> <函数块> | 空

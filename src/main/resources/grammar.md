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
#--------------------------------------语句----------------------------------------------------------------------
<语句> -> <声明语句> | <执行语句>
#--------------------------------------声明语句----------------------------------------------------------------------
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

### 字符
声明语句			    S
值声明			    A
函数声明			    B
常量声明			    C
变量声明			    D
常量类型			    e
常量声明列表			F
标识符			    g
常量			        h
变量类型			    e
变量声明表			J
单体变量声明			K
变量			        g
表达式			    m
函数类型			    e
函数声明形参列表		O
函数声明形参			P
### 产生式
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
### 化简
S -> A | B | 空
A -> C | D

C -> const e F
F -> g=h; | g=h,F

D -> e J
J -> K; | K,J
K -> g | g=m

B -> e g (O)
O -> P | 空
P -> e | e,P
### LL(1)文法
S -> e S' | C | 空
S' -> g S''   
S'' -> (O) | =KJ' | ,J | ;

C ->const e F
F->g=hF'
F'->,F|;

D -> e J
J -> KJ'
J'->,J|;
K -> gK'
K' ->=m|空

B -> e g (O)
O -> P|空
P -> eP'
P'->,P| 空
### First集和Follow集
First(S)->{const,e,空}
Follow(S)->{#}

First(S')->{g}

First(S'')->{,,(,=,;}

First(C)->{const}

First(F)->{g}

First(F')->{,,;}

First(D)->{e}

Fisrt(J)->{g}

First(J')->{,,;}

First(K)->{g}
Follow(K)->First(J')->{,,;}

First(K')->{=,空}
Follow(K')->Follow(K)->{,,;}

First(B)->{e}

Fisrt(O)->{e,空}
Follow(O)->{)}

First(P)->{e}
Follow(P)->Follow(O)->{)}

First(P')->{,,空}
Folow(P')->Follow(P)->{)}

#--------------------------------------执行语句----------------------------------------------------------------------
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

### 字符
执行语句		        Z
数据处理语句		    A
控制语句		        B
复合语句		        C
赋值表达式		    d(表达式)
函数调用		        d(表达式)
<if 语句>		    F
<for 语句>		    G
<while 语句>		    H
<do while 语句>		I
<return 语句>		J
语句表		        K
语句		            L
表达式		        d(表达式)
循环语句		        N
循环复合语句		    O
循环执行语句		    Q
循环语句表		    R
循环if语句		    S
<break语句>		    t
<continue语句>		u
<声明语句>            v(声明语句)
### 产生式
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
### 化简
Z -> A | B | C
A -> d;
B -> F | G | H | I | J
C -> {K}
K -> L | L K
F -> if (d) L | if (d) L else L
G -> for (d;d;d) N
H -> while(d) N
I -> do O while d;
J -> return; | return d;

N -> v | Q | O

O -> {R}
R -> N | N R
Q -> S | G | H | I | J | break; | continue;
S -> if (d) N | if (d) N else N

### LL(1)文法
Z -> d; | B | C

B -> F | G | H | I | J

C -> {K}
K -> LK'
K' -> K | 空
F -> if(d)L F'
F' -> else L | 空
G -> for (d;d;d) N
H -> while(d) N
I -> do O while d;
J -> return J'
J' -> ; | d;

N -> v | Q | O

O -> {R}
R -> NR'
R' -> R | 空

Q -> S | G | H | I | J | break; | continue;
S -> if (d) N S'
S' -> else N | 空
### First集和Follow集
// L -> Z | v
// L -> d; | B | v | LK'
L -> d; | B | v | L'K'
L' -> d; | B | v | L
// First(执行语句)->{const,e,空}
// d -> {实数、整数、字符、标识符、（、!}
// e -> {void,int,char,float}
// v -> {e,const,空}
Fisrt(Z) -> {d,if,for,while,do,return,{}
Follow(Z)->Follow(F')并First(F')并Follow(F)并Follow(K)并First(K')->{else,d,if,for,while,do,return,{，e,const,空,}}

First(B)->{if,for,while,do,return}
Follow(B)->Follow(Z)->{else,d,if,for,while,do,return,{，e,const,空,}}

First(C) -> {{}
Follow(C)->Follow(Z)->{else,d,if,for,while,do,return,{，e,const,空,}}

First(K) -> {d,if,for,while,do,return,{，e,const,空}
Follow(K)->Follow(K')并Follow(C)并{}}->{else,}}

First(K') -> {d,if,for,while,do,return,{，e,const,空}
Follow(K')->Follow(K)->{else,d,if,for,while,do,return,{，e,const,空,}}

First(F) -> {if}
Follow(F)->Follow(B)->{else,d,if,for,while,do,return,{，e,const,空,}}

First(F') -> {else,空}
Follow(F')->Follow(F)->{else,d,if,for,while,do,return,{，e,const,空,}}

First(G)->{for}
First(H)->{while}
First(I)->{do}
First(J)->{return}
First(J')->{;,d}

First(N)->{const,e,空,if,for,while,do,return,break,continue,{}
Follow(N)->Follow(R)并First(R')并Follow(S)并First(S')并Follow(H)并Follow(G)->{},const,e,空,if,for,while,do,return,break,continue,{,else,空}

First(O)->{{}
Follow(O)->Follow(N)并{while}->{while,},const,e,空,if,for,while,do,return,break,continue,{,else,空}

First(R)->{const,e,空,if,for,while,do,return,break,continue,{}
Follow(R)->Follow(R')并{}}->{}}

First(R')->{const,e,空,if,for,while,do,return,break,continue,{,空}
Follow(R')->Follow(R)->{}}

First(Q)->{if,for,while,do,return,break,continue}
Follow(Q)->Follow(N)->{},const,e,空,if,for,while,do,return,break,continue,{,else,空}

First(S)->{if}
Follow(S)->Follow(Q)->{},const,e,空,if,for,while,do,return,break,continue,{,else,空}

First(S')->{else,空}
Follow(S')->Follow(S)->{},const,e,空,if,for,while,do,return,break,continue,{,else,空}

#--------------------------------------函数----------------------------------------------------------------------
<函数定义> -> <函数类型> <标识符> (<函数定义参数列表>) <复合语句>
<函数定义参数列表> -> <函数定义形参> | 空
<函数定义形参> -> <变量类型> <标识符> | <变量类型> <标识符>,<函数定义形参>
## 符号
函数定义			        S
函数类型			        a
标识符			        b
函数定义参数列表			C
复合语句			        d
函数定义形参			    E
变量类型			        f
## 化简
// a -> {void,int,char,float}
// b -> 标识符
// f -> {int,char,float}
// d -> 复合语句
S -> a b (C) d
C -> E | 空
E -> f b | f b,E
## LL(1)文法
S -> a b (C) d
C -> E | 空
E -> f b E'
E' -> ,E | 空
## First集和Follow集
First(S) -> {void,int,char,float}
First(C) -> {int,char,float,空}
Follow(C) -> {)}
First(E)->{int,char,float}
Follow(E)->Follow(C)->{)}
First(E')->{,,空}
Follow(E')->Follow(E)->{)}
#-----------------------------------------程序-------------------------------------------------------------------
<程序> -> <声明语句> main() <复合语句> <函数块>
<函数块> -> <函数定义> <函数块> | 空


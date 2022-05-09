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
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
变量类型			    i
变量声明表			J
单体变量声明			K
变量			        l
表达式			    m
函数类型			    n
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
e -> int | char | float
F -> g=h; | g=h,F

D -> i J
J -> K; | K,J
K -> l | l=m
i -> int | float | char | void

B -> n g (O)
n -> int | float | char | void
O -> P | 空
P -> i | i,P
### LL(1)文法
S -> A | B | 空
A -> C | D

C ->const e F
F->g=hF'
F'->,g=hF''
F''->F'|;

D -> i J
J -> KJ'
J'->,KJ''
J''->J'|;
K -> lK'
K' ->=mK'|空

B -> n g (O)
O -> P|空
P -> iP'
P'->,iP'|空
### First集和Follow集
First(S)->{const,i,n,空}
Follow(S)->{#}
First(A)->{const,i}
First(C)->{const}
First(F)->{g}
First(F')->{,}
First(F'')->{,,;}
First(D)->{i}
Fisrt(J)->{l}
First(J')->{,}
Fisrt(J'')->{,,;}
First(K)->{l}
Follow(K)->First(J')并First(J'')->{,,;}
First(K')->{=,空}
Follow(K')->Follow(K)->{,,;}
First(B)->{n}
Fisrt(O)->{i,空}
Follow(O)->{)}
First(P)->{i}
Follow(P)->Follow(O)->{)}
First(P')->{,,空}
Folow(P')->Follow(P)->{)}
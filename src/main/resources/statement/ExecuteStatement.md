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
C -> K
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

C -> K
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
// First(C)->First(声明语句)并First(执行语句)
// First(执行语句)->{const,e,空}
// d -> {实数、整数、字符、标识符、（、!}
// e -> {void,int,char,float}
Fisrt(Z) -> {d,e,if,for,while,do,return,const,空}
Follow(Z)->Follow(F')并First(F')并Follow(F)并Follow(K)并First(K')->{else,const,e}

First(B)->{if,for,while,do,return}
Follow(B)->Follow(Z)->{else,const,e}

First(C) -> {const,e,空}
Follow(C)->Follow(Z)->{else,const,e}

First(K) -> {const,e,空}
Follow(K)->Follow(K')并Follow(C)->{else,const,e}

First(K') -> {const,e，空}
Follow(K')->Follow(K)->{else,const,e}


First(F) -> {if}
Follow(F)->Follow(B)->{else,const,e}

First(F') -> {else,空}
Follow(F')->Follow(F)->{else,const,e}

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

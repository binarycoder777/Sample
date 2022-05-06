### 字符
执行语句		        Z
数据处理语句		    A
控制语句		        B
复合语句		        C
赋值表达式		    d
函数调用		        e
<if 语句>		    F
<for 语句>		    G
<while 语句>		    H
<do while 语句>		I
<return 语句>		J
语句表		        K
语句		            L
表达式		        M
循环语句		        N
循环复合语句		    O
循环执行语句		    Q
循环语句表		    R
循环if语句		    S
<break语句>		    t
<continue语句>		u
<声明语句>            V
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
A -> d; | e;
B -> F | G | H | I | J
C -> K
K -> L | L K
F -> if (M) L | if (M) L else L
G -> for (M;M;M) N
H -> while(M) N
I -> do O while M;
J -> return; | return M;

N -> V | Q | O

O -> {R}
R -> N | N R
Q -> S | G | H | I | J | break; | continue;
S -> if (M) N | if (M) N else N

### LL(1)文法
Z -> A | B | C

A -> d; | e;

B -> F | G | H | I | J

C -> K
K -> LK'
K' -> K | 空
F -> if(M)L F'
F' -> else L | 空
G -> for (M;M;M) N
H -> while(M) N
I -> do O while M;
J -> return J'
J' -> ; | M;

N -> V | Q | O

O -> {R}
R -> NR'
R' -> R | 空

Q -> S | G | H | I | J | break; | continue;
S -> if (M) N S'
S' -> else N | 空
### First集和Follow集
Fisrt(Z) -> {d,e}
First(A) -> {d,e}
First(B)->{if,for,while,do,return}

First(C) -> {L}
First(K) -> {L}
First(K') -> {L，空}
Follow(K')->Follow(Z)->{#}
First(F) -> {if}
First(F') -> {else,空}
Follow(F')->Follow(Z)->{#}
First(G)->{for}
First(H)->{while}
First(I)->{do}
First(J)->{return}
First(J')->{;,M}

First(N)->{V,Q,{}

First(O)->{{}
First(R)->{N}
First(R')->{R,空}
Follow(R')->{}}

First(Q)->{if,for,while,do,return,break,continue}
First(S)->{if}
First(S')->{else,空}
Follow(S')->Follow(Z)->{#}
### 字符
算术表达式		S
项		        A
因子		        B
常量		        C
变量 	        D
函数调用		    E
数值型常量(实数)		f
字符型常量(字符)		g
标识符		    h
实参列表		    I
实参  		    J
表达式		    K
### 算术表达式的产生式
<算术表达式> -> <项> + <算术表达式> | <项> - <算术表达式> | <项>
<项> -> <因子> * <项> | <因子> / <项> | <因子> % <项> | <因子>
<因子> -> (<算术表达式>) | <常量> | <变量> | <函数调用>
<常量> -> <数值型常量> | <字符型常量>
<变量> -> <标识符>
<函数调用> -> <标识符> (<实参列表>)
<实参列表> -> <实参> | 空
<实参> -> <表达式> | <表达式>,<实参>
### 转化为符号（K待定）
S -> A + S | A - S | A
A -> B*A | B/A | B%A | B
B -> (S) | C | D | E
C -> f | g 
D -> h
E -> h (I)
I -> J | 空
J -> K | K,J
### 转化为LL（1）文法
S -> AS'
S' -> +AS' | -AS' | 空
A -> BA'
A' -> *BA' | /BA' | %BA' | 空
B -> f | g | hB' | (B''A'S')
B' -> (I) | 空
B'' -> (B''A'S') | f | g | hB'
C -> f | g
D -> h
E -> h(I) 
I -> J | 空
J -> KJ'
J' -> ,KJ' | 空
### First集和Follow集
First(S)->{f,g,h,(}
Follow(S)->{#,)}

First(S')->{+,-,空}
Follow(S')->Follow(S)->{#,)}

First(A)->{f,g,h,(}
Follow(A)->Follow(S')->{#,)}

First(A')->{*,/,%,空}
Follow(A')->Follow(A)并First(S')->{#,),+,-}

First(B)->{f,g,h,(}
Follow(B)->First(A')->{*,/,%}

First(B')->{(,空}
Follow(B')->Follow(B)并Follow(B'')->{*,/,%,}

First(B'')->{(,f,g,h}
Follow(B'')->First(A')->{*,/,%}

First(C)->{f,g}

First(D)->{h}

First(E)->{h}

- First(I)->{空,First(J)}
- Follow(I)->{)}

- First(J)->{First(K)}

- First(J')->{,,空}
- Follow(J')->Follow(J)->Follow(I)->{)}

- First(K)->{待定}
### 表达式的LL(1)文法
S -> AS'
S' -> +AS' | -AS' | 空
A -> BA'
A' -> *BA' | /BA' | %BA' | 空
B -> f | g | hB' | (B''A'S')
B' -> (I) | 空
B'' -> (B''A'S') | f | g | hB'
C -> f | g
D -> h
E -> h(I) 
I -> J | 空
J -> KJ'
J' -> ,KJ' | 空
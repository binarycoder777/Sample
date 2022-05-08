### 字符
布尔表达式		A
布尔项		    B
布尔因子		    C
算术表达式		D
关系表达式		E
数值型常量(实数)		f
字符型常量(字符)		g
标识符		        h
关系运算符            o      
### 算术表达式的产生式
<布尔表达式> -> <布尔项> || <布尔表达式> | <布尔项>
<布尔项> -> <布尔因子> && <布尔项> | <布尔因子>
<布尔因子> -> <算术表达式> | <关系表达式> | !<布尔表达式>
<关系表达式> -> <算术表达式> <关系运算符> <算术表达式>
### 转化为符号
A -> B || A | B
B -> C && B | C
C -> D | E | !A
E -> DoD
### 转化为LL（1）文法
A -> BA'
A'->|| A | 空
B -> CB'
B'->&&B | 空
C -> DC' | !A
C' -> oD | 空
### First集和Follow集
// First(D)->{f,g,h,(}
First(A) -> {!,f,g,h,(,}
Follow(A) -> Follow(C)并{#} -> {#,&&，||}

First(A')->{||,空}
Follow(A')->Follow(A)->{#，||，&&}

First(B)->{!,f,g,h,(,}
Follow(B) -> First(A')并Follow(A) -> {||,&&，#}

First(B') -> {&&,空}
Follow(B') -> Follow(B) -> {||,&&，#}

First(C)->{!,f,g,h,(,}
First(C->D)->{f,g,h,(}
First(C->!A)->{!}
Follow(C)->First(B')并Follow(B)->{&&,||,#}

First(C')->{o,空}
Follow(C')->Follow(C)->{&&,||,#}
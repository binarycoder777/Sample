### 字符
布尔表达式		A
布尔项		    B
布尔因子		    C
算术表达式		D
关系表达式		E
### 算术表达式的产生式
<布尔表达式> -> <布尔项> || <布尔表达式> | <布尔项>
<布尔项> -> <布尔因子> && <布尔项> | <布尔因子>
<布尔因子> -> <算术表达式> | <关系表达式> | !<布尔表达式>
### 转化为符号
A -> B || A | B
B -> C && B | C
C -> D | E | !A
### 转化为LL（1）文法
A -> BB'
B' -> ||BB' | 空
B -> CB''
B''->&&BB''|空
C -> D | E | !A

### First集和Follow集
Follow(A) -> Follow(C) -> {&}

Follow(B) -> First(B')并First(B'') -> {|,&}

First(B') -> {|,空}
Follow(B') -> Follow(A) -> {&}

First(B'')->{&，空}
Follow(B'') -> Follow(B) -> {|,&}

Follow(C) -> First(B'') -> {&，空}
### 表达式的LL(1)文法
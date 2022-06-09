# 项目介绍

本项目利用离散数学和形式语言的基本知识，对给定的简单语言设计其上下文无关文法和属性文法，对该语言的编译器进行分析，确定所设计的编译器的功能和应用环境，设计可行的设计方案。主要是通过Java语言设计一个Sample语言的小型解释器，并能够进行可视化展示效果，具体内容包括：
- 设计Sample语言的种别码，并编写词法分析器对Sample语言进行词法分析，对错误词法能进行报错处理。另外还实现了正规集->NFA->DFA->MFA的转换。
- 设计Sample语言的上下文无关文法，并对之进行改造为LL1文法，然后使用递归下降分析的方法，实现Sample语言的语法分析器，进行语法分析，并能够生成语法分析树，对错误语法进行报错处理。另外还选择了LL1预测分析法实现了其语法分析过程。
- 根据Sample语言的语义，设计其对应的属性文法，实现语义分析器，进行语义规则制导翻译，生成对应的符号表和四元式。
- 根据生成的四元式，实现对应的解释器，逐行进行解析翻译。实现对Sample语言编写的代码的解释执行。
- 整合、完善已完成的程序各阶段相关功能，并能可视化演示。

# 项目架构

  ![image](https://user-images.githubusercontent.com/72557529/172742080-3efbac17-4cf9-4c4b-812f-364366b5a4a1.png)



## 1、词法分析
程序的源代码最初只是一串长长的字符串。这样长的字符串较难处理，因此解释器的第一个组成部分是词法分析器。将字符串中的字符以单词为单位分组，切割成多个子字符串。词法分析器的构建着重点在于根据语言的种别码表设计对应词法的状态转换图，然后根据状态转换图进行相应程序编写。

 图1：种别码
 ![image](https://user-images.githubusercontent.com/72557529/170191627-d993db8d-6ebf-43fe-b17b-5d2f5d750146.png)
 
 图2：数值的状态转换图
  ![image](https://user-images.githubusercontent.com/72557529/170191892-73f78841-8d68-4673-afd1-c8d92e531426.png)


有了具体的状态转换图，则以下便需要根据状态转化图进行相应编程工作，以实现词法分析器。首先设计Token对象，用于存储词法分析过程中识别的单词。而在整个识别过程中，采用过滤链器和策略模式的方案进行单词识别工作,避免了使用大量的ifelse。简化了后续代码的维护，扩展工作。

图3：单词识别策略
 ![image](https://user-images.githubusercontent.com/72557529/170192002-83631dce-c743-4c1f-ab4e-08123fa32ad0.png)

## 2、语法分析

语法分析器的功能是判断读取的单词符号串是否能够构成一个语法正确的程序。其输入是词法分析器的输出token序列。输出则是一棵语法分析树。包含了整个语法分析的过程。在这个分析过程中，需要遵循语法规则，对于错误的语法需要进行报错处理。在进行具体的程序编写之前，需要写出语言对应的上下文无关文法，然后改造文法为LL1文法，然后进行递归下降分析或LL1预测分析法进行分析

--------------------------------------语法规则----------------------------------------------------------------------
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
--------------------------------------符号化----------------------------------------------------------------------
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


## 3 语义分析
在语法分析之后，静态语义分析、建立符号表和中间代码生成这三个部分的工作可能交叉进行。因此，在这里，我通过在语法分析过程中编织入语义制导规则函数进行相关处理，可以达到一边进行语法分析一边进行语义分析的效果，提高整个分析流程的效率，减少不必要的重复操作。

## 4解释器的设计
解释器的设计需要借助于前面生成的四元式中间代码。而在前面的中间代码生成过程中，我们已经完成了语义规则等错误的识别。因此只需针对四元式的类型并结合前面的符号表，模拟活动记录等，进行逐行翻译即可。其可分为运算类语句、条件类语句、函数调用类语句、参数压栈类语句和return。
然后便读取四元式，生成一个活动记录对象栈，根据函数类型将当前活动记录压入栈中。一开始是main函数，因此读取到(main,_,_,_)四元式时，便创建main函数的活动记录栈。main函数的变量压入栈。逐行执行即可。遇到运算类语句，生成临时变量存储中间结果。遇到条件类语句，则需要根据result存储的跳转地址，跳转到对应的四元式执行解释对应的四元式。而如果是遇到参数压栈类语句则将参数压入栈中。后续call调用函数的时候。将栈中参数弹出，替换对应的形参。然后将调用函数的参数压入变量栈。根据调用函数中存储的position定位执行器函数定义的四元式语句。当执行完函数调用。根据return类型。生成一个临时变量，存储return放入结果。活动栈弹栈。继续执行刚才的面函数。并将return的结果回填给对应的变量。继续后续的四元式执行。直到执行到(sys,_,_,_)的时候函数运行结束。

 ![image](https://user-images.githubusercontent.com/72557529/170192717-6252a41e-b7f6-4829-b68f-33435c223f72.png)
 
 ## 5 LL(1)预测分析法的实现
 
LL(1)预测分析法是一种自上而下的语法分析法，其要求文法是LL(1)文法,在实现的时候需要将文法改在为LL(1)文法，然后使用一个二维分析表和栈联合进行控制实现分析。其设计关键是构建预测分析表和构建相关数据结构进行预测分析。
而对于预测分析表的构造，首先必须计算文法每个终结符与非终结符。然后计算对应的First集，对于First集存在空的非终结符，求其Follow其，然后生成对应的预测分析表.具体步骤如下所示:
- 计算每个产生式的首终结符集First(α)
- 对于每一产生式 A→α,
- 对于终结符a∈First(α),将A→α填入 M[A,a]；
- 如果ε∈First(A→α)，则构造Follow(A)，对任何元素 b∈Follow(A)，将A→α填入M[A,b]；将所有无定义的 M[A,a] 标上错误标志。
 ![image](https://user-images.githubusercontent.com/72557529/172742409-35c8c996-0644-471b-aa58-a8d072093858.png)

在构建好预测分析表后，便开始进行预测分析，首先将‘#’压入栈中，将开始符号也压入对应栈中。读取第一个符号到串中,循环执行如下操作:
- 栈顶符号弹出放入X中；
- 如果X为终结符:如果X==a，表明成功匹配符号a，读取下一个符号到a，否则有语法错误，出错处理；
- 如果X==‘#’，如果a==‘#’，分析结束，分析成功。如果a!=‘#’，有语法错误，出错处理。
- 如果X为非终结符号, 则查分析表M:如果M[X,a]为空，有语法错，出错处理。如果M[X,a]=‘X1 X2…Xn ’, 则: 将右部Xn …X2 X1反序压入堆栈中。 
 ![image](https://user-images.githubusercontent.com/72557529/172742432-be9adcb7-0c53-4209-8b08-95622ef944f1.png)

## 6 正则式到最小化DFA算法的实现

将一个正则式转化为最小化的DFA需要经历多个过程，在这里可以采用责任链模式进行算法的实现，将职责分离。每个过程封装一个对象进行实现相应的转换工作。完成之后转交给下一个对象进行处理。在各个阶段可以进行一些对外拓展比如说结果显示等。最终完成对正则式到最小化DFA的转化实现，大致流程如下图所示。

![image](https://user-images.githubusercontent.com/72557529/172742494-2ea7c8a6-a875-4b37-9792-2c1b834b8c16.png)

在整个实现的过程中，将职责分离有利于整个算法的实现。我们可以先进行正规集到NFA这一步的实现，这一步的关键在于构造相应的表格。这一步需要判断参与的算符的优先级，因此在一开始便需要进行相应的算符优先级构造。构造好之后，接下来便是对整个输入正规集进行处理，这里采用逆波兰式+栈来处理。因此循环遍历正规集，然后针对于读入的符号，进行非空处理，判断是否是规定的正规集，获取符号优先级，栈顶的符号的优先级比当前低则运算符入栈，等待后面计算，反之则可以进行转换。
得到对应的NFA集之后，接下来便是根据对应的NFA去计算DFA。其大致过程便是先确定初态和终态，增加状态X,Y,使之成为新的唯一的初态和终态。从X引ε弧到原初态结点, 从原终态结点引ε弧到Y结点。原来的初态结点不在是初态结点，原来的终态结点不再是终态结点。然后构造表，计算对应的I的ε-闭包ε-closure(I)。将表构造好之后，接下来便是进行对应的化简操作。遍历表格,转为等价DFA
最后便是对DFA进行化简操作，这一步就是一个子集划分的算法，将具有相同入口的状态进行合并，消除多余状态(从开始状态出发，任何输入串都不能到达的状态；或者从这个状态没有通路到达终态)。然后留一个作为代表。



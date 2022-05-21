#--------------------------------------LL(1)----------------------------------------------------------------------
expression -> (AR) ar_A' AR' expression' | f ar_A' AR' expression' | !BO bo_A' BO' | c expression''''
expression''''-> ar_A' AR' expression' | expression'''
expression'''-> (ar_D) ar_A' AR' expression' | = expression
expression' -> d AR expression''| bo_A' BO' | 空
expression''-> bo_A' BO' | 空 

AR -> ar_A AR'
AR' -> +AR | -AR | 空
ar_A -> ar_B ar_A'
ar_A' -> *ar_A | /ar_A | %ar_A | 空
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
bo_B -> AR bo_B' | !BO
bo_B'-> d AR | 空

AS -> c = expression

Statement -> DE | EX
DE -> const e de_E | e DE' | 空
DE' -> c DE''
DE''-> = Expression de_F' | de_F' | (de_H)
de_A -> de_C | de_D
de_C -> const e de_E
de_E -> c = f de_E'
de_E' -> ; | ,de_E
de_D -> e de_F
de_F -> de_G de_F'
de_F' -> ; | ,de_F
de_G -> c de_G'
de_G' -> = Expression | 空
de_B -> e c (de_H)
de_H -> de_I | 空
de_I -> e de_I' | c de_I'
de_I' -> ,de_I | 空

EX -> ex_A | ex_B | ex_C
ex_A -> c ex_A'
ex_A' -> = expression; | (ar_D)
ex_B -> ex_D | ex_E | ex_F | ex_G | ex_H
ex_C -> {ex_I}
ex_I -> Statemnet ex_I'
ex_I' -> ex_I | 空
ex_D -> if (Expression) Statement ex_D'
ex_D'-> else Statement | 空
ex_E -> for(Expression;Expression;Expression) ex_K
ex_F -> while (Expression) ex_K
ex_G -> do ex_L while Expression;
ex_H -> return ex_H'
ex_H'-> ; | Expression;
ex_K -> const e de_E | e DE' | ex_M | ex_L | DE' | 空
ex_L ->{ex_N}
ex_N -> ex_K ex_N'
ex_N' -> ex_N | 空
ex_M -> ex_O | ex_E | ex_F | ex_G | ex_H | break; | continue;
ex_O -> if (Expression) ex_K ex_O'
ex_O'-> else ex_K | 空

Function -> e c (fu_A) ex_C
fu_A -> fu_B | 空
fu_B -> e c fu_B'
fu_B'-> ,fu_B | 空

Program -> DE main() ex_C po_A
po_A -> Function po_A | 空

#--------------------------------------化简----------------------------------------------------------------------
Expression -> AR | RE | BO | AS

AR -> ar_A + AR | ar_A - AR | ar_A
{
AR.PLACE = newtmp()
gencode(+|-,ar_A.PLACE,AR1.PLACE,AR.PLACE)
}

ar_A -> ar_B * ar_A | ar_B / ar_A | ar_B % ar_A | ar_B
{
ar_A.PLACE = newtmp()
gencode(*|/|%,ar_B.PLACE,ar_A1.PLACE,ar_A.PLACE)
}

ar_B -> (AR) | f | g | ar_C
{
ar_B.PLACE = newtmp()
gencode(ar_B.PLACE = AR1.PLACE | f | g | ar_C.PLACE)
}

ar_C -> c (ar_D)
{
ar_B.PLACE = newtmp()
gencode(ar_B.PLACE = AR1.PLACE | f | g | ar_C.PLACE)
}

ar_D -> ar_E | 空
{
ar_D.PLACE = newtmp()
gencode(ar_D.PLACE = ar_E.PLACE)
}

ar_E -> Expression | Expression,ar_E
{
ar_E.PLACE = newtmp()
gencode(ar_E.PLACE = Expression.PLACE)
}

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

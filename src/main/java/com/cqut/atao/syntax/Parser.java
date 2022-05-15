package com.cqut.atao.syntax;

import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.strategy.statement.Syntax;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Parser.java
 * @Description 语法分析
 * @createTime 2022年05月01日 13:49:00
 */
public class Parser {

    private Syntax syntax = new Syntax();

    public void syataxAnalysis(MyTree tree,TokenList<Token> tokens,List<Exception> exceptions){
        Token token = tokens.getCurToken();
        while (token != null && !"main".equals(token.getVal().toString())){
            syntax.DE(tree,tokens,exceptions);
            token = tokens.getCurToken();
        }
        syntax.Program(tree,tokens,exceptions);
    }

}

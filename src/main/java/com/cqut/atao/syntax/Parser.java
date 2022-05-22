package com.cqut.atao.syntax;

import com.cqut.atao.syntax.strategy.statement.Syntax;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.token.Token;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Parser.java
 * @Description 语法分析
 * @createTime 2022年05月01日 13:49:00
 */
@Data
@NoArgsConstructor
public class Parser {

    private Syntax syntax = new Syntax();

    public void syataxAnalysis(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions){
        setPar(tree,tokens,exceptions);
        Token token = tokens.getCurToken();
        while (token != null && !"main".equals(token.getVal().toString())){
            syntax.DE();
            token = tokens.getCurToken();
        }
        syntax.Program();
    }

    public void setPar(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions){
        syntax.setTree(tree);
        syntax.setTokens(tokens);
        syntax.setExceptions(exceptions);
    }
}

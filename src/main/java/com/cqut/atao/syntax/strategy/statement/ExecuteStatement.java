package com.cqut.atao.syntax.strategy.statement;

import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.exception.ParseException;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.expression.AssignmentExpression;
import com.cqut.atao.syntax.strategy.expression.Expression;
import com.cqut.atao.syntax.strategy.expression.ExpressionClient;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.syntax.tree.TreeNode;
import com.cqut.atao.token.Token;
import org.apache.commons.lang.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName ExecuteStatement.java
 * @Description TODO
 * @createTime 2022年05月06日 08:59:00
 */
public class ExecuteStatement implements Expression {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    DeclarativeStatement declarativeStatement = new DeclarativeStatement();

    ExpressionStatement expressionStatement = new ExpressionStatement();

    @Override
    public void recognition(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("执行语句"));
        Z(tree, tokens, exceptions);
        tree.traceBack();
    }

    private void pass() {
        return;
    }

    private void Z(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("Z"));
        Token token = tokens.getCurToken();
        if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            B(tree, tokens, exceptions);
        } else if (token != null && ("void".equals(token.getType()) || "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))) {
            C(tree, tokens, exceptions);
        } else if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()) || "标识符".equals(token.getType()) || "(".equals(token.getType()) || "!".equals(token.getType()))) {
            expressionStatement.recognition(tree, tokens, exceptions);
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            } else {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        } else if (token != null && ("else".equals(token.getType()) || "const".equals(token.getType()))) {
            pass();
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void C(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("C"));
        K(tree, tokens, exceptions);
        tree.traceBack();
    }

    private void K(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K"));
        L(tree, tokens, exceptions);
        K1(tree, tokens, exceptions);
        tree.traceBack();
    }

    // TOOD 存在公共前缀未解决
    private void L(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("L"));
        Token token = tokens.getCurToken();
        if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()) || "标识符".equals(token.getType()) || "(".equals(token.getType()) || "!".equals(token.getType()))) {
            expressionStatement.recognition(tree, tokens, exceptions);
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        } else if (token != null && ("const".equals(token.getType()) || "void".equals(token.getType()) || "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))) {
            declarativeStatement.recognition(tree, tokens, exceptions);
        } else if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()))) {
            B(tree, tokens, exceptions);
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void K1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("K1"));
        Token token = tokens.getCurToken();
        if (token != null && ("const".equals(token.getType()) || "void".equals(token.getType()) || "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))) {
            K(tree, tokens, exceptions);
        } else if (token != null && "else".equals(token.getType())) {
            pass();
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void B(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("B"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            F(tree, tokens, exceptions);
        } else if (token != null && "for".equals(token.getType())) {
            G(tree, tokens, exceptions);
        } else if (token != null && "while".equals(token.getType())) {
            H(tree, tokens, exceptions);
        } else if (token != null && "do".equals(token.getType())) {
            I(tree, tokens, exceptions);
        } else if (token != null && "return".equals(token.getType())) {
            J(tree, tokens, exceptions);
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J"));
        Token token = tokens.getCurToken();
        if (token != null && "return".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            J1(tree, tokens, exceptions);
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void J1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("J1"));
        Token token = tokens.getCurToken();
        if (token != null && ";".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
        } else if (token != null && ("实数".equals(token.getType()) || "整数".equals(token.getType()) || "字符".equals(token.getType()) || "标识符".equals(token.getType()) || "(".equals(token.getType()) || "!".equals(token.getType()))) {
            expressionStatement.recognition(tree, tokens, exceptions);
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void I(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("I"));
        Token token = tokens.getCurToken();
        if (token != null && "do".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            O(tree, tokens, exceptions);
            token = tokens.getCurToken();
            if (token != null && "while".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                expressionStatement.recognition(tree, tokens, exceptions);
                // 修复，关系表达对；的误判
                exceptions.remove(exceptions.size()-1);
                exceptions.remove(exceptions.size()-1);
                exceptions.remove(exceptions.size()-1);
                token = tokens.getCurToken();
                if (token != null && ";".equals(token.getType())) {
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                }else if (token != null) {
                    exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                }
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void O(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("O"));
        Token token = tokens.getCurToken();
        if (token != null && "{".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            R(tree, tokens, exceptions);
            token = tokens.getCurToken();
            if (token != null && "}".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        }else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void R(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("R"));
        N(tree, tokens, exceptions);
        R1(tree, tokens, exceptions);
        tree.traceBack();
    }

    private void N(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("N"));
        Token token = tokens.getCurToken();
        if (token != null && ("if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()))) {
            Q(tree, tokens, exceptions);
        } else if (token != null && "{".equals(token.getType())) {
            O(tree, tokens, exceptions);
        } else if (token != null && ("const".equals(token.getType()) || "void".equals(token.getType()) || "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))) {
            declarativeStatement.recognition(tree, tokens, exceptions);
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void Q(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("Q"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            S(tree, tokens, exceptions);
        } else if (token != null && "for".equals(token.getType())) {
            G(tree, tokens, exceptions);
        } else if (token != null && "while".equals(token.getType())) {
            H(tree, tokens, exceptions);
        } else if (token != null && "do".equals(token.getType())) {
            I(tree, tokens, exceptions);
        } else if (token != null && "return".equals(token.getType())) {
            J(tree, tokens, exceptions);
        } else if (token != null && ("break".equals(token.getType()) || "contniue".equals(token.getType()))) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && ";".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void S(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                expressionStatement.recognition(tree, tokens, exceptions);
                token = tokens.getCurToken();
                if (token != null && ")".equals(token.getType())) {
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    N(tree, tokens, exceptions);
                    S1(tree, tokens, exceptions);
                } else if (token != null){
                    exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                }
            } else if (token != null){
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        }else if (token != null){
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void S1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("S1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            N(tree, tokens, exceptions);
        } else if (token != null && ("}".equals(token.getType()) || "const".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()) || "{".equals(token.getType()) || "else".equals(token.getType()))) {
            pass();
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void R1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("R1"));
        Token token = tokens.getCurToken();
        if (token!=null && ("const".equals(token.getType()) || "if".equals(token.getType()) || "for".equals(token.getType()) || "while".equals(token.getType()) || "do".equals(token.getType()) || "return".equals(token.getType()) || "break".equals(token.getType()) || "continue".equals(token.getType()) || "{".equals(token.getType()))){
             R(tree,tokens,exceptions);
        } else if (token != null && "}".equals(token.getType())){
            pass();
        }else if (token != null){
           exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }


    private void H(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("H"));
        Token token = tokens.getCurToken();
        if (token != null && "while".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                expressionStatement.recognition(tree, tokens, exceptions);
                token = tokens.getCurToken();
                if (token != null && ")".equals(token.getType())) {
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    N(tree, tokens, exceptions);
                } else if (token != null) {
                    exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                }
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        }else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void G(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("G"));
        Token token = tokens.getCurToken();
        if (token != null && "for".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                expressionStatement.recognition(tree, tokens, exceptions);
                // 去除最后一个异常，因为这里出现了误判，针对赋值表达式，没有处理;
                exceptions.remove(exceptions.size()-1);
                exceptions.remove(exceptions.size()-1);
                exceptions.remove(exceptions.size()-1);
                token = tokens.getCurToken();
                if (token != null && ";".equals(token.getType())) {
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    expressionStatement.recognition(tree, tokens, exceptions);
                    // 去除最后一个异常，因为这里出现了误判，针对赋值表达式，没有处理;
                    exceptions.remove(exceptions.size()-1);
                    exceptions.remove(exceptions.size()-1);
                    exceptions.remove(exceptions.size()-1);
                    token = tokens.getCurToken();
                    if (token != null && ";".equals(token.getType())) {
                        tree.addChild(new TreeNode(token.getVal().toString()));
                        tokens.match();
                        tree.traceBack();
                        expressionStatement.recognition(tree, tokens, exceptions);
                        token = tokens.getCurToken();
                        if (token != null && ")".equals(token.getType())) {
                            tree.addChild(new TreeNode(token.getVal().toString()));
                            tokens.match();
                            tree.traceBack();
                            N(tree, tokens, exceptions);
                        } else if (token != null) {
                            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                        }
                    } else if (token != null) {
                        exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                    }
                } else if (token != null) {
                    exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                }
            } else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        } else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("F"));
        Token token = tokens.getCurToken();
        if (token != null && "if".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            token = tokens.getCurToken();
            if (token != null && "(".equals(token.getType())) {
                tree.addChild(new TreeNode(token.getVal().toString()));
                tokens.match();
                tree.traceBack();
                expressionStatement.recognition(tree, tokens, exceptions);
                token = tokens.getCurToken();
                if (token != null && ")".equals(token.getType())) {
                    tree.addChild(new TreeNode(token.getVal().toString()));
                    tokens.match();
                    tree.traceBack();
                    L(tree, tokens, exceptions);
                    F1(tree, tokens, exceptions);
                } else if (token != null) {
                    exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
                }
            }else if (token != null) {
                exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
            }
        }else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

    private void F1(MyTree tree, TokenList<Token> tokens, List<Exception> exceptions) {
        tree.addChild(new TreeNode("F1"));
        Token token = tokens.getCurToken();
        if (token != null && "else".equals(token.getType())) {
            tree.addChild(new TreeNode(token.getVal().toString()));
            tokens.match();
            tree.traceBack();
            L(tree, tokens, exceptions);
        } else if (token != null && ("const".equals(token.getType()) || "void".equals(token.getType()) || "int".equals(token.getType()) || "float".equals(token.getType()) || "char".equals(token.getType()))) {
            pass();
        }else if (token != null) {
            exceptions.add(new ParseException("Grammar mistakes", tokens.getPreToken()));
        }
        tree.traceBack();
    }

}

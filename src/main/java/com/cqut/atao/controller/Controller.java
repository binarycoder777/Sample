package com.cqut.atao.controller;

import com.cqut.atao.Interpreter.Interpreter;
import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.middle.MiddleCode;
import com.cqut.atao.LL1.LL1;
import com.cqut.atao.syntax.Parser;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.toMFA.NTM;
import com.cqut.atao.token.Token;
import com.cqut.atao.util.TokenUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private GridPane rootLayout;

    @FXML
    private TextArea coderArea;

    @FXML
    private TextArea tokenArea;

    @FXML
    private TextArea treeArea;

    // 词法分析
    private Lexer lexer = new Lexer();

    // 语法分析
    private Parser parser = new Parser();

    // tokens
    private List<Token> tokens;

    // 中间代码
    private MiddleCode middleCode;

    // 解释器
    private Interpreter interpreter;

    // LL1预测分析法
    private LL1 ll1 = new LL1();

    // LL(1)文法路径
    private String llFilePath;

    // 正规集->MFA
    private NTM ntm = new NTM();

    @FXML
    public void open(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) rootLayout.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
        // 记录LL1文法路径
        llFilePath = selectedFile.getPath();
        BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
        String text = "";
        String line = null;
        while ((line = reader.readLine()) != null){
            text += line+"\n";
        }
        coderArea.setText(text);
    }

    @FXML
    public void lexer(ActionEvent actionEvent) throws IOException {
        // 获取代码
        String coder = coderArea.getText();
        // 解析token
        lexer.lexicalAnalysis(coder);
        // 获取token
        tokens = lexer.getTokens();
        // 回显
        String res = TokenUtil.displayToken(tokens);
        tokenArea.setText(res);
        refresh();
    }

    @FXML
    public void syntax(ActionEvent actionEvent) throws IOException {
        // 构造语法树
        TokenList<Token> tokenList = new TokenList<>(tokens);
        MyTree tree = new MyTree();
        List<Exception> exceptions = new ArrayList<>();
        // 生成语法树
        parser.syataxAnalysis(tree, tokenList, exceptions);
        // 回显
       if (exceptions.size() != 0){
           treeArea.setText(TokenUtil.displayException(exceptions));
       }else {
           treeArea.setText(tree.toString());
       }
       middleCode = parser.getSyntax().getMiddleCode();
    }


    @FXML
    public void middle(ActionEvent actionEvent) throws IOException {
       treeArea.setText(middleCode.getFourTable().toString());
    }

    @FXML
    public void table(ActionEvent actionEvent) throws IOException {
        treeArea.setText(middleCode.getTable().getTable());
    }

    @FXML
    public void interpreter(ActionEvent actionEvent) throws IOException {
        this.interpreter = new Interpreter(treeArea);
        interpreter.interpreter(middleCode.getTable().getFunctionTable(),middleCode.getFourTable());
    }

    // First集
    @FXML
    public void first(ActionEvent actionEvent) throws IOException {
        ll1.readLAN(llFilePath);
        String first = ll1.getFirst();
        tokenArea.setText(first);
    }

    // Follow集
    @FXML
    public void follow(ActionEvent actionEvent) throws IOException {
        String follow = ll1.getFollow();
        tokenArea.setText(follow);
    }

    // 终结符
    @FXML
    public void vt(ActionEvent actionEvent) throws IOException {
        String vt = ll1.getVT();
        tokenArea.setText(vt);
    }

    // 分析表
    @FXML
    public void llTable(ActionEvent actionEvent) throws IOException {
        String table = ll1.generateTable();
        treeArea.setText(table);
        tokenArea.setText("");
    }

    // 过程分析
    @FXML
    public void process(ActionEvent actionEvent) throws IOException {
        String input = tokenArea.getText();
        System.out.println(input);
        String res = ll1.analyze(input);
        treeArea.setText(res);
    }


    // 正规集 -> NFA
    @FXML
    public void NFA(ActionEvent actionEvent) throws IOException {
        String text = coderArea.getText();
        String res = ntm.getNFA(text);
        treeArea.setText(res);
    }

    // NFA -> DFA
    @FXML
    public void DFA(ActionEvent actionEvent) throws IOException {
        treeArea.setText(ntm.getDFA());
    }

    // DFA -> MFA
    @FXML
    public void MFA(ActionEvent actionEvent) throws IOException {
        treeArea.setText(ntm.getMFA());
    }


    private void refresh(){
        treeArea.setText("");
    }



}

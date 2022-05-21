package com.cqut.atao.controller;

import com.cqut.atao.lexical.Lexer;
import com.cqut.atao.middle.MiddleCode;
import com.cqut.atao.middle.table.Four;
import com.cqut.atao.minic.miniC.MiniCV1;
import com.cqut.atao.syntax.Parser;
import com.cqut.atao.syntax.TokenList;
import com.cqut.atao.syntax.strategy.statement.Syntax;
import com.cqut.atao.syntax.tree.MyTree;
import com.cqut.atao.token.Token;
import com.cqut.atao.util.TokenUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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

    private Lexer lexer = new Lexer();

    private Parser parser = new Parser();

    private List<Token> tokens;

    private MiddleCode middleCode;

    @FXML
    public void open(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) rootLayout.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
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


    private void refresh(){
        treeArea.setText("");
    }



}

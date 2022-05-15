package com.cqut.atao.syntax.tree;

import com.cqut.atao.token.Token;
import com.cqut.atao.util.SerialClone;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang.text.StrBuilder;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MyTree.java
 * @Description 语法树
 * @createTime 2022年05月03日 10:34:00
 */
@Data
public class MyTree implements Serializable {

    // 根节点
    TreeNode root;

    // 当前节点
    TreeNode curNode;

    public MyTree() {
        root = new TreeNode("start");
        curNode = root;
    }

    public void addChild(TreeNode node){
        curNode.getChild().add(node);
        node.setParent(curNode);
        curNode = node;
    }

    public void traceBack(){
        // TODO
        // 可能存在空指针异常，需要处理
        this.curNode = this.curNode.getParent();
    }


    public  void print() {
        printHelper(root, "\t");
    }

    private  void printHelper(TreeNode root, String start) {
        if (root == null) {
            return;
        }
        String mid = start.substring(0, start.lastIndexOf("\t")) + "└---";
        System.out.println(mid + root.getVal());
        if (root.getChild() == null) {
            return;
        }
        for (TreeNode node : root.getChild()) {
            printHelper(node, start + "\t");
        }
    }

    private  void displayTree(StringBuilder s, TreeNode root, String start) {
        if (root == null) {
            return;
        }
        String mid = start.substring(0, start.lastIndexOf("\t")) + "└---";
        s.append(mid+root.getVal()+"\n");
        if (root.getChild() == null) {
            return;
        }
        for (TreeNode node : root.getChild()) {
            displayTree(s,node, start + "\t");
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        displayTree(s,root, "\t");
        return s.toString();
    }
}

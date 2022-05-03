package com.cqut.atao.syntax.tree;

import com.cqut.atao.token.Token;
import com.sun.xml.internal.bind.v2.TODO;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName MyTree.java
 * @Description TODO
 * @createTime 2022年05月03日 10:34:00
 */
public class MyTree {

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

    public void printMyTree(){
        int len = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int tmp = len;
            len = 0;
            while (tmp-- > 0){
                TreeNode root = queue.poll();
                System.out.print(root.getVal()+"\t");
                for (int i = 0; i < root.getChild().size(); i++) {
                    queue.add(root.getChild().get(i));
                }
                len += root.getChild().size();
            }
            System.out.println();
        }
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

}

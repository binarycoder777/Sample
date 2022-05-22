package com.cqut.atao.syntax.tree;

import com.cqut.atao.token.Token;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TreeNode.java
 * @Description 树节点
 * @createTime 2022年05月02日 16:53:00
 */
@Data
public class TreeNode implements Serializable {

    private String val;

    private Boolean isTerminator;

    private TreeNode parent;

    private List<TreeNode> child;

    public TreeNode(String val) {
        this.val = val;
        this.child = new ArrayList<>();
    }

    public TreeNode(String val, Boolean isTerminator) {
        this.val = val;
        this.isTerminator = isTerminator;
        this.child = new ArrayList<>();
    }
}

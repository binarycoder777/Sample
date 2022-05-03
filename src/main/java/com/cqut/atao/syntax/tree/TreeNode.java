package com.cqut.atao.syntax.tree;

import com.cqut.atao.token.Token;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TreeNode.java
 * @Description TODO
 * @createTime 2022年05月02日 16:53:00
 */
@Data
public class TreeNode {

    private String val;

    private TreeNode parent;

    private List<TreeNode> child;

    public TreeNode(String val) {
        this.val = val;
        this.child = new ArrayList<>();
    }

}

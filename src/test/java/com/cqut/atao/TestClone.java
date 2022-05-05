package com.cqut.atao;

import com.alibaba.fastjson.JSONObject;
import com.cqut.atao.syntax.tree.MyTree;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TestClone.java
 * @Description TODO
 * @createTime 2022年05月04日 14:52:00
 */
public class TestClone {

    Logger logger = LoggerFactory.getLogger(TestClone.class);

//    JSONObject.parseObject(JSONObject.toJSONBytes(account), Account.class)
    @Test
    public void testClone(){
        MyTree tree = new MyTree();
        MyTree target = (MyTree)JSONObject.parseObject(JSONObject.toJSONBytes(tree), MyTree.class);
        logger.warn(""+(tree==target));
    }

}

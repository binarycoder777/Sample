package com.cqut.atao;

import com.cqut.atao.LL1.LL1;
import org.junit.Test;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName TestLL1.java
 * @Description 测试LL1
 * @createTime 2022年05月22日 15:08:00
 */
public class TestLL1 {

    LL1 ll1 = new LL1();

    @Test
    public void testLL1(){
        String filePath = "/Users/weitao/Desktop/面试/项目/compler/src/main/resources/rebuild/";
        ll1.readLAN(filePath+"ll1.txt");
        ll1.getFirst();
        ll1.getFollow();
        ll1.getVT();
        ll1.generateTable();
        ll1.analyze("i+i*i");
    }

}

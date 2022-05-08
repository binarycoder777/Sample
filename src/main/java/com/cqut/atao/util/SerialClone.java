package com.cqut.atao.util;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName SerialClone.java
 * @Description TODO
 * @createTime 2022年05月04日 15:18:00
 */

import java.io.Serializable;

/**
 * 类名 SerialClone
 * 描述 序列化克隆类，只有继承该类，就可以实现深克隆
 *
 */
public class SerialClone implements Cloneable, Serializable {
    private static final long serialVersionUID = 5794148504376232369L;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return SerialCloneUtils.deepClone(this);
    }
}
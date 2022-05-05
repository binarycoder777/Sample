package com.cqut.atao.util;

/**
 * @author atao
 * @version 1.0.0
 * @ClassName Serutil.java
 * @Description TODO
 * @createTime 2022年05月04日 15:16:00
 */

import java.io.*;

/**
 * 类名 SerialCloneUtils
 * 描述 序列化克隆工具
 *
 * @author hedonglin
 * @version 1.0
 * @date 2019/11/15 16:36
 */
public class SerialCloneUtils{
    /**
     * 使用ObjectStream序列化实现深克隆
     * @return Object obj
     */
    public static <T extends Serializable> T deepClone(T t) throws CloneNotSupportedException {
        // 保存对象为字节数组
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            try(ObjectOutputStream out = new ObjectOutputStream(bout)) {
                out.writeObject(t);
            }

            // 从字节数组中读取克隆对象
            try(InputStream bin = new ByteArrayInputStream(bout.toByteArray())) {
                ObjectInputStream in = new ObjectInputStream(bin);
                return (T)(in.readObject());
            }
        }catch (IOException | ClassNotFoundException e){
            CloneNotSupportedException cloneNotSupportedException = new CloneNotSupportedException();
            e.initCause(cloneNotSupportedException);
            throw cloneNotSupportedException;
        }
    }
}

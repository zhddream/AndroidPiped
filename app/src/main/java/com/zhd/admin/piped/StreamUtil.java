package com.zhd.admin.piped;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * zhd  Streamutil类
 */
public class StreamUtil {

    /**
     * 将对象转换为byte数组
     *
     * @param objtct 输入对象
     * @return
     * @throws IOException
     */
    public static byte[] object2ByteArray(Object objtct) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;

        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(objtct);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    /**
     * 将byte转换为Object
     *
     * @param bytes
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object byteArray2Object(byte[] bytes) throws IOException, ClassNotFoundException {
        ObjectInputStream sIn = null;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        sIn = new ObjectInputStream(in);
        return sIn.readObject();
    }

    /**
     * 将流转换为String
     *
     * @param is
     * @return
     */
    public static String getStringFromInputStream(InputStream is) {
        String result = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = -1;
            byte buffer[] = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, buffer.length);
            }
            result = new String(out.toByteArray());
            is.close();
            out.close();
        } catch (Exception e) {

        }
        return result;
    }
}

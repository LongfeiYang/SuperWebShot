package com.ylf.imageloader.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 11208_000 on 2018/2/5.
 */

public class MD5Util {
    private static final String TAG = "MD5Util";

    private static MessageDigest digest;

    static{
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.d(TAG, "static initializer: MD5算法不支持");
        }
    }

    public static String toMD5(String key){
        if(digest == null){
            return String.valueOf(key.hashCode());
        }
        //更新字节
        digest.update(key.getBytes());
        //获取最终的摘要
        return convert2HexString(digest.digest());
    }

    /**
     * 转换为16进制字符串
     * @param bytes
     * @return
     */
    private static String convert2HexString(byte[] bytes){
        StringBuffer buffer = new StringBuffer();
        for (byte b:bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1){
                buffer.append('0');
            }
            buffer.append(hex);
        }
        return buffer.toString();
    }

}

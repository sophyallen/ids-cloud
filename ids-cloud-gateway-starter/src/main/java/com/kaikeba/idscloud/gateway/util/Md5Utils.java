package com.kaikeba.idscloud.gateway.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    public static String toMd5Str(String buffer) {
        char hexDigist[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(buffer.getBytes());
            byte[] datas = md.digest(); //16个字节的长整数
            char[] str = new char[2 * 16];
            int k = 0;

            for (int i = 0; i < 16; i++) {
                byte b = datas[i];
                str[k++] = hexDigist[b >>> 4 & 0xf];//高4位
                str[k++] = hexDigist[b & 0xf];//低4位
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        String sign = Md5Utils.toMd5Str("hky9148067821747544f1a1e06ee99e5ec02a109fc39e759");
        String password = "hky9148067821747:" + sign;
        System.out.println(password);
    }

}
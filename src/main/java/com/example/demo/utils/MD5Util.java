package com.example.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5工具类
 */

public class MD5Util {
    private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_CACHE = new ThreadLocal<MessageDigest>();
    private static char md5Chars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     * 获取一个字符串的md5码
     *
     * @param str 源串
     * @return md5码
     */
    public static String getMD5Str(String str) {
        try {
            MessageDigest messagedigest = getMessageDigest();
            messagedigest.update(str.getBytes("UTF-8"));
            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static MessageDigest getMessageDigest() throws NoSuchAlgorithmException {
        MessageDigest messagedigest = MESSAGE_DIGEST_CACHE.get();
        if (messagedigest == null) {
            messagedigest = MessageDigest.getInstance("MD5");
            MESSAGE_DIGEST_CACHE.set(messagedigest);
        }
        return messagedigest;
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuilder stringBuilder = new StringBuilder(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringBuilder);
        }
        return stringBuilder.toString();
    }

    private static void appendHexPair(byte bt, StringBuilder stringBuilder) {
        char c0 = md5Chars[(bt & 0xf0) >> 4];
        char c1 = md5Chars[bt & 0xf];
        stringBuilder.append(c0);
        stringBuilder.append(c1);
    }

    public static void main(String[] args) {
        getMD5Str("1BFE437D548915CCA8EC3284CFC2138C" + "20190116154233" + "{\"orderId\":\"\"," +
                "\"pageSize\":\"10\",\"startSettlementTime\":\"2019-04-22 00:00:00\",\"pageNum\":\"100\"," +
                "\"endSettlementTime\":\"2019-04-28 23:59:59\",\"employeeNumber\":\"\"}");

    }
}
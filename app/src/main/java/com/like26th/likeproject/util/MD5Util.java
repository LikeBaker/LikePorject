package com.like26th.likeproject.util;

import java.security.MessageDigest;

/**
 * Created by liuzhen000 on 2016/5/30
 */
public class MD5Util {
    public static String getMD5(String str) {
        str = str.toLowerCase();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            StringBuffer buf = new StringBuffer();
            int len = byteArray.length;
            for (int i = 0; i < len; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    buf.append(0).append(Integer.toHexString(0xff & byteArray[i]));
                } else {
                    buf.append(Integer.toHexString(0xff & byteArray[i]));
                }
            }

            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

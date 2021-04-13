/*
 * @(#)MD5Utils.java 2018年5月24日上午10:12:54 server Maven Webapp Copyright 2018
 * Thuisoft, Inc. All rights reserved. THUNISOFT PROPRIETARY/CONFIDENTIAL. Use
 * is subject to license terms.
 */
package com.wdd.myplatform.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MD5Utils
 * @author wdd
 * @version 1.0
 */
public class Md5Utils {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(Md5Utils.class);

    /**
     * 指定次数的加密
     * @param src 源串
     * @param count 加密次数
     */
    public static String encryptMultiple(String src, int count) {
        String des = encrypt(src);
        for (int i = 1; i < count; i++) {
            des = encrypt(des);
        }
        return des;
    }

    /**
     * 单次md5加密
     * @param str 待加密字符串
     * @return
     */
    public static String encrypt(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }
        try {
            md5.update(str.getBytes("utf-8"));
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        byte[] result = md5 == null ? null : md5.digest();
        return byteToHex(result);
    }

    /**
     * 字节转字符串
     * @param data 字节数组
     * @return
     */
    public static String byteToHex(byte[] data) {
        if (data == null) {
            return null;
        }
        return new String(Hex.encodeHex(data));
    }

    public static void main(String[] args) {
        String encrypt = encrypt("123456adminaaa");
        System.out.println(encrypt);
    }
}

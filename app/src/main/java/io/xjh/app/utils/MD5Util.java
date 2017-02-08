package io.xjh.app.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类
 * 
 * @author liuxihui
 */
public class MD5Util {

	public static String encode(String str) {
		return encode(str, "MD5");
	}

	public static String encode(String str, String type) {
		return encode(str, "UTF-8", type);
	}

	/*
	 * MD5加密
	 */
	private static String encode(String str, String encoding, String type) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance(type);
			messageDigest.reset();
			messageDigest.update(str.getBytes(encoding));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				builder.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				builder.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return builder.toString();
	}
}

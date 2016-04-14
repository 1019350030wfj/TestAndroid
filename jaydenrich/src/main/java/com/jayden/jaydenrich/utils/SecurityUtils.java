package com.jayden.jaydenrich.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 加密工具
 * 
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class SecurityUtils {
	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * MD5加密
	 * 
	 * @param str
	 *            加密字符串
	 * @return 加密结果
	 */
	public static String encodeMD5(String str) {
		try {
			byte[] strTemp = str.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char set[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				set[k++] = DIGITS[byte0 >>> 4 & 0xf];
				set[k++] = DIGITS[byte0 & 0xf];
			}
			return new String(set);
		} catch (Exception e) {
			return null;
		}
	}

	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
			'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };
	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,
			60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,
			-1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,
			38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,
			-1, -1 };

	/**
	 * Base64编码
	 * 
	 * @param str
	 *            字符串
	 * @return 编码结果
	 */
	public static String encodeBASE64(String str) {
		return encodeBASE64(str.getBytes());
	}

	/**
	 * Base64编码
	 * 
	 * @param str
	 *            字符串
	 * @param charset
	 *            字符串编码格式
	 * @return 编码结果
	 */
	public static String encodeBASE64(String str, String charset) {
		try {
			return encodeBASE64(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * Base64编码
	 * 
	 * @param data
	 *            字节数组
	 * @return 编码结果
	 */
	public static String encodeBASE64(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;
		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4)
						| ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4)
					| ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2)
					| ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}

	/**
	 * Base64解码
	 * 
	 * @param str
	 *            字符串
	 * @return 解码结果
	 */
	public static String decodeBASE64(String str) {
		return decodeBASE64(str.getBytes());
	}

	/**
	 * Base64解码
	 * 
	 * @param str
	 *            字符串
	 * @param charset
	 *            字符串编码格式
	 * @return 解码结果
	 */
	public static String decodeBASE64(String str, String charset) {
		try {
			return decodeBASE64(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * Base64解码
	 * 
	 * @param data
	 *            字节数组
	 * @return 编码结果
	 */
	public static String decodeBASE64(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3, b4;
		while (i < len) {

			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1)
				break;

			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1)
				break;
			sb.append((char) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			do {
				b3 = data[i++];
				if (b3 == 61)
					return sb.toString();
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1)
				break;
			sb.append((char) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			do {
				b4 = data[i++];
				if (b4 == 61)
					return sb.toString();
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1)
				break;
			sb.append((char) (((b3 & 0x03) << 6) | b4));
		}
		return sb.toString();
	}

}

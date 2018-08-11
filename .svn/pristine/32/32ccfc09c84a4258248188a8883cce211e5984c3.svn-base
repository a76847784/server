package com.pingan.pama.test.invoke.util;

public class FormatBytesUtil {
	/**
	 * 将byte数组转换为short类型
	 * 
	 * @param bytes
	 * @return short
	 */
	public static short getShortFromBytes(byte[] bytes) {
		return (short) ((0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)));
	}

	/**
	 * 将short类型的数据转为byte数组 short类型为两个字节将这个字节转为byte数组长度为二 eg:id和认证码长度的转�?
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] getByteOfShort(short data) {
		byte[] bytes = new byte[2]; // 创建�?个byte数组，长度设置为2
		bytes[0] = (byte) (data & 0xff);// 参数�?0xff做与运算
		bytes[1] = (byte) ((data & 0xff00) >> 8);// 做过‘与’运算之后右�?8�?
		return bytes;
	}

	/**
	 * int类型转换�?4字节长度的byte数组 eg:时间戳的生成 数据区长度的转换
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] getBytesOfInt(int data) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (data & 0xff);
		bytes[1] = (byte) ((data & 0xff00) >> 8);
		bytes[2] = (byte) ((data & 0xff0000) >> 16);
		bytes[3] = (byte) ((data & 0xff000000) >> 24);
		return bytes;
	}

	/**
	 * 时间�?
	 * 
	 * @return 4字节时间�?
	 */
	public static byte[] getTimestamp() {
		int i = (int) (System.currentTimeMillis() / 1000);
		byte[] time = getBytesOfInt(i);
		return time;
	}

	/**
	 * 把from中的内容拷贝到to�? eg：姓名拷贝到保留数据明文
	 * 
	 * @param to
	 * @param from
	 * @return
	 */
	public static byte[] copyByteToAnother(byte[] to, byte[] from) {
		for (int i = 0; i < from.length; i++) {
			to[i] = from[i];
		}
		return to;
	}

	/**
	 * String类型转为byte数组，单字节转换方式
	 * 
	 * @param bytes
	 * @param string
	 * @return
	 */
	public static byte[] getBytesFromString(byte[] bytes, String string) {
		for (int i = 0; i < string.length(); i++) {
			bytes[i] = (byte) string.charAt(i);
		}
		return bytes;
	}

	/**
	 * 将两个byte数组合并为一�?
	 * 
	 * @param byte_1
	 * @param byte_2
	 * @return byte_3
	 */
	public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
		byte[] byte3 = new byte[byte1.length + byte2.length];
		System.arraycopy(byte1, 0, byte3, 0, byte1.length);
		System.arraycopy(byte2, 0, byte3, byte1.length, byte2.length);
		return byte3;
	}

	/**
	 * 用可变参数将�?有的byte数组合并为一�?
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public static byte[] concatAll(byte[] first, byte[]... rest) {
		int totalLength = first.length;
		for (int i = 0; i < rest.length; i++) {
			if (rest[i] != null) {
				totalLength += rest[i].length;
			}
		}
		byte[] result = new byte[totalLength];
		System.arraycopy(first, 0, result, 0, first.length);
		int offset = first.length;
		for (int i = 0; i < rest.length; i++) {
			if (rest[i] != null) {
				System.arraycopy(rest[i], 0, result, offset, rest[i].length);
				offset += rest[i].length;
			}
		}
		return result;
	}

	/**
	 * 将byte数组转换成十六进制字符串 eg:流水号转�?
	 * 
	 * @author xia79
	 * @param ba
	 * @return
	 */
	public static String bytesToHexString(byte[] ba) {
		String s = "";
		for (int i = 0; i < ba.length; i++) {
			s += String.format("%02X", ba[i]);
		}
		return s;
	}

	/**
	 * 将十六进制字符串转换成byte数组 eg:流水号转�?
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}

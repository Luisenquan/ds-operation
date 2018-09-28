package com.dascom.operation.utils.tcp;

/**
 * 16进制转byte[]
 * 
 * @author Leisenquan
 * @time 2018年9月26日 下午1:50:47
 * @project_name ds-operation
 */
public class BytesHexStrTranslate {

	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			// 奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			// 偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			j++;
		}
		return result;
	}
	
	public static byte hexToByte(String inHex) {
		return (byte)Integer.parseInt(inHex,16);
	}
	
	 private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', 
	            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	 /**
	  *  数组转16进制字符串
	  */
	 public static String bytesToHex(byte[] bytes) {
	        char[] buf = new char[bytes.length * 2];
	        int index = 0;
	        for(byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
	            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
	            buf[index++] = HEX_CHAR[b & 0xf];
	        }
	        return new String(buf);
	    }


}

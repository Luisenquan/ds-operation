package com.dascom.operation.utils.tcp;

/**
 * 16进制转byte[]
 * 
 * @author Leisenquan
 * @time 2018年9月26日 下午1:50:47
 * @project_name ds-operation
 */
public class HexToByteArray {

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

}

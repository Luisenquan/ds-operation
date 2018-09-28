package com.dascom.operation.utils.tcp;

import com.dascom.operation.entity.tcp.WifiConfig;

/**
 * 解析数据的类
 * 
 * @author Leisenquan
 * @time 2018年9月26日 下午12:56:39
 * @project_name ds-operation
 */
public class AnalyseMessage {

	public static WifiConfig getWifiConfig(byte[] message, int index) {

		WifiConfig wifiConfig = new WifiConfig();
//		int index=20+4+8;
		StringBuffer sb = new StringBuffer();
		// 获取本机ip
		sb.append((message[index + 3] & 0xff) + ".");
		sb.append((message[index + 2] & 0xff) + ".");
		sb.append((message[index + 1] & 0xff) + ".");
		sb.append((message[index] & 0xff));
		wifiConfig.setLocalIpAddress(sb.toString());
		sb.setLength(0);
		index += 4;

		// 获取本机子网掩码
		sb.append((message[index + 3] & 0xff) + ".");
		sb.append((message[index + 2] & 0xff) + ".");
		sb.append((message[index + 1] & 0xff) + ".");
		sb.append((message[index] & 0xff));
		wifiConfig.setLocalSubnetMask(sb.toString());
		sb.setLength(0);
		index += 4;
		// 获取本机网关
		sb.append((message[index + 3] & 0xff) + ".");
		sb.append((message[index + 2] & 0xff) + ".");
		sb.append((message[index + 1] & 0xff) + ".");
		sb.append((message[index] & 0xff));
		wifiConfig.setLocalGateway(sb.toString());
		sb.setLength(0);
		index += 4;
		// 远程服务器地址
		sb.append((message[index + 3] & 0xff) + ".");
		sb.append((message[index + 2] & 0xff) + ".");
		sb.append((message[index + 1] & 0xff) + ".");
		sb.append((message[index] & 0xff));
		wifiConfig.setRemoteServerAddress(sb.toString());
		sb.setLength(0);
		index += 4;
		// 主DNS服务器地址
		sb.append((message[index + 3] & 0xff) + ".");
		sb.append((message[index + 2] & 0xff) + ".");
		sb.append((message[index + 1] & 0xff) + ".");
		sb.append((message[index] & 0xff));
		wifiConfig.setMainDNSServerAddress(sb.toString());
		sb.setLength(0);
		index += 4;
		// 备用DNS服务器地址
		sb.append((message[index + 3] & 0xff) + ".");
		sb.append((message[index + 2] & 0xff) + ".");
		sb.append((message[index + 1] & 0xff) + ".");
		sb.append((message[index] & 0xff));
		wifiConfig.setSlaveDNSServerAddress(sb.toString());
		sb.setLength(0);
		index += 4;
		// UDP服务端口号
		int udpPort = (message[index] & 0xff) + ((message[index + 1] & 0xff) << 8);
		wifiConfig.setUdpPort(udpPort);
		index += 2;
		// 控制端口号
		int controlPort = (message[index] & 0xff) + ((message[index + 1] & 0xff) << 8);
		wifiConfig.setControlPort(controlPort);
		index += 2;
		// 控制端口号
		int dataPort = (message[index] & 0xff) + ((message[index + 1] & 0xff) << 8);
		wifiConfig.setDataPort(dataPort);
		index += 2;
		// 远程路由的ssid 即wifi名称
		String remoteRouteSsid = new String(message, index, 33);
		wifiConfig.setRemoteRouteSsid(remoteRouteSsid.trim());
		index += 33;
		// 远程路由的password 即wifi密码
		String remoteRoutePassword = new String(message, index, 65);
		wifiConfig.setRemoteRoutePassword(remoteRoutePassword.trim());
		index += 65;
		// 使能DHCP功能 0x00:无效 , 0x01:有效
		byte dhcp = message[index];
		wifiConfig.setDhcp(dhcp);
		index += 1;
		// 重连服务器的间隔时间
		byte reconnectionInterval = message[index];
		wifiConfig.setReconnectionInterval(reconnectionInterval);
		index += 1;
		// 本机AP模式网络ssid 即wifi固件名称
		String apModelSsid = new String(message, index, 22);
		wifiConfig.setApModelSsid(apModelSsid.trim());
		index += 22;
		// 本机AP模式网络密码
		String apModelPassword = new String(message, index, 16);
		wifiConfig.setApModelPassword(apModelPassword.trim());
		index += 16;
		// 网络工作模式 0:station, 1:AP
		byte networkMode = message[index];
		wifiConfig.setNetworkMode(networkMode);
		index += 1;

		// 数据端口数据传输速率 0x02：中速； 0x03：高速； 其他:慢速；
		byte dataTransmissionSpeed = message[index];
		wifiConfig.setDataTransmissionSpeed(dataTransmissionSpeed);
		index += 1;
		// 网络通信通道
		byte networkChannel = message[index];
		wifiConfig.setNetworkChannel(networkChannel);
		index += 1;
		// dns功能 0x00:无效,0x01:有效
		byte dns = message[index];
		wifiConfig.setDns(dns);
		index += 1;
		// 设备型号
		byte deviceModel = message[index];
		wifiConfig.setDeviceModel(deviceModel);
		index += 1;
		// 预留
		index += 8;
		// 服务器域名
		String domainName = new String(message, index, 32);
		wifiConfig.setDomainName(domainName.trim());
		index += 32;
		return wifiConfig;
	}

	public static byte[] getWifiConfigtoByte(WifiConfig wifiConfig) {
		byte[] wifiConfigByte = new byte[228 + 2];
	    wifiConfigByte[0] = 0x10;
	    wifiConfigByte[1] = 0x18;
	    wifiConfigByte[2] = (byte) ((wifiConfigByte.length - 4) & 0xff);
		// 3
		wifiConfigByte[4] = 0x00;
		wifiConfigByte[5] = 0x00;
		wifiConfigByte[6] = (byte) 0xfe;
		wifiConfigByte[7] = 0x01;
		wifiConfigByte[8] = 0x00;
		wifiConfigByte[9] = (byte) 0xd8;
		wifiConfigByte[10] = (byte) 0x06;
		// 11
		wifiConfigByte[12] = (byte) 0xff;
		wifiConfigByte[13] = (byte) 0xff;
		int index = 14;
		String[] localIpAddress = wifiConfig.getLocalIpAddress().split("\\.");
		for (int i = localIpAddress.length - 1; i >= 0; i--) {
			int v = Integer.parseInt(localIpAddress[i]);
			wifiConfigByte[index] = (byte) v;
			index++;

		}
		String[] localSubnetMask = wifiConfig.getLocalSubnetMask().split("\\.");
		for (int i = localSubnetMask.length - 1; i >= 0; i--) {
			int v = Integer.parseInt(localSubnetMask[i]);
			wifiConfigByte[index] = (byte) v;
			index++;

		}
		String[] localGateway = wifiConfig.getLocalGateway().split("\\.");
		for (int i = localGateway.length - 1; i >= 0; i--) {
			int v = Integer.parseInt(localGateway[i]);
			wifiConfigByte[index] = (byte) v;
			index++;
		}
		String[] remoteServerAddress = wifiConfig.getRemoteServerAddress().split("\\.");
		for (int i = remoteServerAddress.length - 1; i >= 0; i--) {
			int v = Integer.parseInt(remoteServerAddress[i]);
			wifiConfigByte[index] = (byte) v;
			index++;
		}
		String[] mainDNSServerAddress = wifiConfig.getMainDNSServerAddress().split("\\.");
		for (int i = mainDNSServerAddress.length - 1; i >= 0; i--) {
			int v = Integer.parseInt(mainDNSServerAddress[i]);
			wifiConfigByte[index] = (byte) v;
			index++;
		}
		String[] slaveDNSServerAddress = wifiConfig.getSlaveDNSServerAddress().split("\\.");
		for (int i = slaveDNSServerAddress.length - 1; i >= 0; i--) {
			int v = Integer.parseInt(slaveDNSServerAddress[i]);
			wifiConfigByte[index] = (byte) v;
			index++;
		}
		byte[] udpPort = byte2Length(Integer.toHexString(wifiConfig.getUdpPort()));
		for (byte b : udpPort) {
			wifiConfigByte[index] = b;
			index++;
		}
		byte[] controlPort = byte2Length(Integer.toHexString(wifiConfig.getControlPort()));
		for (byte b : controlPort) {
			wifiConfigByte[index] = b;
			index++;
		}
		byte[] dataPort = byte2Length(Integer.toHexString(wifiConfig.getDataPort()));
		for (byte b : dataPort) {
			wifiConfigByte[index] = b;
			index++;
		}
		byte[] remoteRouteSsid = wifiConfig.getRemoteRouteSsid().getBytes();
		for (int i = 0; i < 33; i++) {
			if (i < remoteRouteSsid.length) {
				wifiConfigByte[index] = remoteRouteSsid[i];
			} else {
				wifiConfigByte[index] = 0;
			}
			index++;
		}
		byte[] remoteRoutePassword = wifiConfig.getRemoteRoutePassword().getBytes();
		for (int i = 0; i < 65; i++) {
			if (i < remoteRoutePassword.length) {
				wifiConfigByte[index] = remoteRoutePassword[i];
			} else {
				wifiConfigByte[index] = 0;
			}
			index++;
		}
		wifiConfigByte[index] = wifiConfig.getDhcp();
		index++;
		wifiConfigByte[index] = wifiConfig.getReconnectionInterval();
		index++;
		byte[] apModelSsid = wifiConfig.getApModelSsid().getBytes();
		for (int i = 0; i < 22; i++) {
			if (i < apModelSsid.length) {
				wifiConfigByte[index] = apModelSsid[i];
			} else {
				wifiConfigByte[index] = 0;
			}
			index++;
		}
		byte[] apModelPassword = wifiConfig.getApModelPassword().getBytes();
		for (int i = 0; i < 16; i++) {
			if (i < apModelPassword.length) {
				wifiConfigByte[index] = apModelPassword[i];
			} else {
				wifiConfigByte[index] = 0;
			}
			index++;
		}
		wifiConfigByte[index] = wifiConfig.getNetworkMode();
		index++;
		wifiConfigByte[index] = wifiConfig.getDataTransmissionSpeed();
		index++;
		wifiConfigByte[index] = wifiConfig.getNetworkChannel();
		index++;
		wifiConfigByte[index] = wifiConfig.getDns();
		index++;
		wifiConfigByte[index] = wifiConfig.getDeviceModel();
		index++;
		// 保留
		index += 8;
		byte[] domainName = wifiConfig.getDomainName().getBytes();
		for (int i = 0; i < 32; i++) {
			if (i < domainName.length) {
				wifiConfigByte[index] = domainName[i];
			} else {
				wifiConfigByte[index] = 0;
			}
			index++;
		}

		int crc = 0;
		for (int i = 14; i < wifiConfigByte.length; i++) {
			crc ^= wifiConfigByte[i];
			for (int j = 0; j < 8; j++) {
				if ((crc & 0x80) != 0) {
					crc = (crc << 1) ^ 0x31;
				} else {
					crc <<= 1;
				}
			}
		}
		wifiConfigByte[11] = (byte) (crc & 0xff);

		int sum = 0;
		for (int k = 4; k < wifiConfigByte.length; k++) {
			sum += (wifiConfigByte[k] & 0xff);
		}
		wifiConfigByte[3] = (byte) (sum & 0xff);
		return wifiConfigByte;

	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 16进制的字符串 最终转化为2字节的数组,遵循小端排序
	 *
	 * @param str
	 * @return
	 */
	public static byte[] byte2Length(String str) {
		String hexString = str.toUpperCase();
		while (hexString.length() < 4) {
			hexString = "0" + hexString;
		}
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[length - 1 - i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

}

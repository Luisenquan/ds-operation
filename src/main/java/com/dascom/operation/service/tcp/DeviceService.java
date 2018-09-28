package com.dascom.operation.service.tcp;

import com.dascom.operation.entity.tcp.WifiConfig;
import com.dascom.operation.vo.ResultVO;

public interface DeviceService {
	
	//17H  获取设备配置信息
	ResultVO readDevice(String number);
	
	//18H  设备配置
	ResultVO setDevice(String number,WifiConfig wifiConfig);
	
	//11H 设置设备重启
	ResultVO RestartDevice(String number);
	

}

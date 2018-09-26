package com.dascom.operation.service.tcp;

import java.io.UnsupportedEncodingException;

import com.dascom.operation.vo.ResultVO;

public interface DeviceService {
	
	//17H  获取设备配置信息
	ResultVO readDevice(String number);
	
	//18H  设备配置
	void setDevice(String number);
	

}

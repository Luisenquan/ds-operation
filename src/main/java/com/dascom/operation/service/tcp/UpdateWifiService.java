package com.dascom.operation.service.tcp;

import com.dascom.operation.vo.ResultVO;

public interface UpdateWifiService {
	
	//16H进入固件升级模式
	ResultVO goIntoWifiMode(String number);
	
	
	
	//60H 固件更新开始
	ResultVO startUpdate(String number);
	//61H 发送固件数据
	ResultVO sendData(String number,String base64Data);
	//62H 固件更新完成
	ResultVO finshUpdate(String number);
	//16H 退出固件升级模式
	ResultVO ExitWifiMode(String number);
	//11H 设置设备重启
	ResultVO RestartDevice(String number);

}

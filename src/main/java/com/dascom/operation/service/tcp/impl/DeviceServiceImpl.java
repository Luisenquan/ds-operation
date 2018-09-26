package com.dascom.operation.service.tcp.impl;


import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dascom.operation.entity.tcp.ControlEntity;
import com.dascom.operation.entity.tcp.WifiConfig;
import com.dascom.operation.service.tcp.DeviceService;
import com.dascom.operation.utils.ResultVOUtil;
import com.dascom.operation.utils.tcp.AnalyseMessage;
import com.dascom.operation.utils.tcp.HexToByteArray;
import com.dascom.operation.utils.tcp.SubStringResult;
import com.dascom.operation.utils.tcp.TcpHttpUtils;
import com.dascom.operation.vo.ResultVO;

@Component("deviceService")
public class DeviceServiceImpl implements DeviceService{
	
	private static final Logger logger = LogManager.getLogger(DeviceServiceImpl.class);
	
	@Value("${tcpControlUrl}")
	private String tcpControlUrl;
	
	@Autowired
	private TcpHttpUtils tcpHttp;
	@Autowired
	private SubStringResult subStringResult;

	@Override
	public ResultVO readDevice(String number){
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl, new ControlEntity(UUID.randomUUID().toString(), number, "10170000", "00", null));
		String data = obj.getString("data");
		byte[] message = HexToByteArray.hexToByteArray(data);
		if(message.length <= 20+4+8) {
			return ResultVOUtil.error(1312, "读取wifi配置失败");
		}else {
			WifiConfig wifiConfig = AnalyseMessage.getWifiConfig(message, 20+4+8);
			logger.info("----读取wifi配置成功----"+wifiConfig.toString());
			return ResultVOUtil.success(wifiConfig);
		}
		
	}

	@Override
	public void setDevice(String number) {
		// TODO Auto-generated method stub
		
	}

}

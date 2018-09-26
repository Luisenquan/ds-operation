package com.dascom.operation.service.tcp.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dascom.operation.entity.tcp.ControlEntity;
import com.dascom.operation.service.tcp.UpdateWifiService;
import com.dascom.operation.utils.ResultVOUtil;
import com.dascom.operation.utils.tcp.SubStringResult;
import com.dascom.operation.utils.tcp.TcpHttpUtils;
import com.dascom.operation.vo.ResultVO;



@Component("updateWifiService")
public class UpdateWifeServiceImpl implements UpdateWifiService{
	
	private static final Logger logger = LogManager.getLogger(UpdateWifeServiceImpl.class);
	
	@Value("${tcpControlUrl}")
	private String tcpControlUrl;
	
	@Autowired
	private TcpHttpUtils tcpHttp;
	@Autowired
	private SubStringResult subStringResult;
	
	

	//16H进入固件升级模式
	@Override
	public ResultVO goIntoWifiMode(String number) {
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl,new ControlEntity(UUID.randomUUID().toString(), number, "10160000", "00", null));
		String data = obj.get("data").toString();
		List<String> resultList = subStringResult.resultList(data);
		if(resultList.get(2).equals("03")) {
			logger.info("进入wifi固件更新模式");
			if(resultList.get(4).equals("03")) {
				//return ResultVOUtil.success();
			}else {
				return ResultVOUtil.error(1302,null);
			}
		}else {
			logger.error("进入wifi固件更新模式失败");
			return ResultVOUtil.error(1302,null);//进入更新模式失败
		}
		return null;

	}

	@Override
	public ResultVO startUpdate(String number) {
		// 60H固件更新开始
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl, new ControlEntity(UUID.randomUUID().toString(), number, "10600000", "00", null));
		String data = obj.getString("data");
		List<String> result = subStringResult.resultList(data);
		if(result.get(3).equals("00")) {
			logger.info("----设备开始进入版本更新状态，等待版本数据----");
			//return ResultVOUtil.success();
		}else {
			return ResultVOUtil.error(1302,"设备进入版本更新状态失败");
		}
		return null;
	}

	@Override
	public ResultVO sendData(String number, String base64Data) {
		// 61H 发送固件数据
		//int dataSize = Base64.decodeBase64(base64Data).length;

		String instruct = "10610000";
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl, new ControlEntity(UUID.randomUUID().toString(), number, instruct, "00", base64Data));
		String data = obj.getString("data");
		List<String> result = subStringResult.resultList(data);
		if(result.get(3).equals("00")) {
			logger.info("----设备已接收到的版本数据正确----");
		}else {
			return ResultVOUtil.error(1302,"设备接收版本数据错误");
		}
		return null;
	}

	@Override
	public ResultVO finshUpdate(String number) {
		// 62H 固件更新完成
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl, new ControlEntity(UUID.randomUUID().toString(), number, "10620000", "00", null));
		String data = obj.getString("data");
		List<String> result = subStringResult.resultList(data);
		if(result.get(3).equals("00")) {
			logger.info("----设备已接收完版本数据----");
		}else {
			ResultVOUtil.error(1302, "设备接收版本数据失败");
		}
		return null;
	}

	@Override
	public ResultVO ExitWifiMode(String number) {
		// 16H 退出固件升级模式
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl, new ControlEntity(UUID.randomUUID().toString(), number, "1016010101", "00", null));
		String data = obj.getString("data");
		List<String> result = subStringResult.resultList(data);
		if(result.get(2).equals("03")&&!result.get(4).equals("03")) {
			logger.info("----退出固件更新模式成功----");
		}else {
			return ResultVOUtil.error(1302, "退出固件更新模式失败");
		}
		return null;
	}

	@Override
	public ResultVO RestartDevice(String number) {
		// 11H  设置设备重启
		JSONObject obj = tcpHttp.connectControl(tcpControlUrl, new ControlEntity(UUID.randomUUID().toString(), number, "10110000", "00", null));
		String data = obj.getString("data");
		List<String> result = subStringResult.resultList(data);
		if(result.get(2).equals("00")&&result.get(3).equals("00")) {
			logger.info("----设备重启成功----");
		}else {
			ResultVOUtil.error(1302, "设备重启失败");
		}
		return null;
	}

	
}

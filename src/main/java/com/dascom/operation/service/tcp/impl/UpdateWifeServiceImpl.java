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
public class UpdateWifeServiceImpl implements UpdateWifiService {

	private static final Logger logger = LogManager.getLogger(UpdateWifeServiceImpl.class);

	@Value("${tcpControlUrl}")
	private String tcpControlUrl;

	@Autowired
	private TcpHttpUtils tcpHttp;
	@Autowired
	private SubStringResult subStringResult;

	@Override
	public ResultVO updateWifi(String number, String base64Data) {
		String uuid = UUID.randomUUID().toString();
		// 16H进入固件升级模式
		JSONObject gointoObj = tcpHttp.connectControl(tcpControlUrl,
				new ControlEntity(uuid, number, "10160000", null, null));
		String gointoData = gointoObj.getString("data");
		List<String> gointoList = subStringResult.resultList(gointoData);
		if (!gointoList.get(2).equals("03")) {
			logger.error("tcp设备操作失败,返回LL码：{}", gointoList.get(2));
			return ResultVOUtil.error(1321, "tcp设备操作失败");
		}
		if (!gointoList.get(4).equals("03")) {
			logger.error("进入固件升级模式失败,当前模式D0值:{}", gointoList.get(4));
			return ResultVOUtil.error(1322, "模式更新失败");
		}
		logger.info("----进入wifi固件更新模式----");
		// 60H 固件更新开始
		JSONObject startObj = tcpHttp.connectControl(tcpControlUrl,
				new ControlEntity(uuid, number, "10600000", null, null));
		String startData = startObj.getString("data");
		List<String> startList = subStringResult.resultList(startData);
		if (!startList.get(3).equals("00")) {
			logger.error("进入固件更新状态失败,错误码:{}", startList.get(3));
			return ResultVOUtil.error(1322, "模式更新失败");
		}
		logger.info("----设备进入版本更新状态，等待版本数据----");
		// 61H 发送固件数据
		JSONObject sendObj = tcpHttp.connectControl(tcpControlUrl,
				new ControlEntity(uuid, number, "10610000", null, base64Data));
		String sendData = sendObj.getString("data");
		List<String> sendList = subStringResult.resultList(sendData);
		if (!sendList.get(3).equals("00")) {
			logger.error("设备已接收到的版本数据错误,错误码:{}", sendList.get(3));
			return ResultVOUtil.error(1321, "tcp设备操作失败");
		}
		logger.info("----设备已接收到的版本数据正确----");
		// 62H 固件更新完成
		JSONObject finshObj = tcpHttp.connectControl(tcpControlUrl,
				new ControlEntity(uuid, number, "10620000", null, null));
		String finshData = finshObj.getString("data");
		List<String> finshList = subStringResult.resultList(finshData);
		if (!finshList.get(3).equals("00")) {
			logger.error("接收版本数据失败,SS码:{}", finshList.get(3));
			return ResultVOUtil.error(1321, "tcp设备操作失败");
		}
		logger.info("----设备已接收完版本数据，将停止接受版本数据----");
		// 16H 退出固件升级模式
		JSONObject exitObj = tcpHttp.connectControl(tcpControlUrl,
				new ControlEntity(uuid, number, "1016010101", null, null));
		/*String exitData = exitObj.getString("data");
		List<String> exitList = subStringResult.resultList(exitData);
		if (!exitList.get(2).equals("03")) {
			logger.error("tcp设备操作失败,返回LL码：{}", gointoList.get(2));
			return ResultVOUtil.error(1321, "tcp设备操作失败");
		}
		if (exitList.get(4).equals("03")) {
			logger.error("退出更新模式失败,当前D0值:{}", exitList.get(4));
			return ResultVOUtil.error(1322, "模式更新失败");
		}*/
		logger.info("----退出固件更新模式成功----");
		return ResultVOUtil.success();
	}

}

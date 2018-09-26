package com.dascom.operation.controller.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.dascom.operation.service.tcp.UpdateWifiService;
import com.dascom.operation.utils.tcp.SubStringResult;
import com.dascom.operation.vo.ResultVO;


@RestController
public class WifiUpdateController {
	
	@Autowired
	private UpdateWifiService updateWifiService;
	
	@Autowired
	private SubStringResult sub;
	
	@RequestMapping("update")
	public ResultVO update() {
		ResultVO result = new ResultVO();
		String base64Data = sub.getBase64();
		String number = "0036BE7350025400";
		result = updateWifiService.goIntoWifiMode(number);
		result = updateWifiService.startUpdate(number);
		result = updateWifiService.sendData(number, base64Data);
		result = updateWifiService.finshUpdate(number);
		result = updateWifiService.ExitWifiMode(number);
		result = updateWifiService.RestartDevice(number);
		return result;
	}
	
	
	
	
	@RequestMapping("goInto")
	public ResultVO goInto() {
		String number = "0036BE7350025400";
		return updateWifiService.goIntoWifiMode(number);
	}
	
	@RequestMapping("startUpdate")
	public ResultVO startUpdate() {
		String number = "0036BE7350025400";
		return updateWifiService.startUpdate(number);
	}
	
	@RequestMapping("sendData")
	public ResultVO sendData() {
		String number = "0036BE7350025400";
		String base64Data = sub.getBase64();
		return updateWifiService.sendData(number, base64Data);
	}

}

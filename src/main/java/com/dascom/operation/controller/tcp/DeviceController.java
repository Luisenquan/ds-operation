package com.dascom.operation.controller.tcp;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dascom.operation.entity.CollectionPrinters;
import com.dascom.operation.service.CollectionPrintersService;
import com.dascom.operation.service.tcp.DeviceService;
import com.dascom.operation.utils.ResultVOUtil;
import com.dascom.operation.vo.ResultVO;

@RestController
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private CollectionPrintersService printersService;
	
	@RequestMapping("readDevice/{number}")
	public ResultVO read(@PathVariable(value = "number") String number){
		//判断设备是否存在
		CollectionPrinters printer = printersService.fetchByNumber(number);
		return ResultVOUtil.success(printer);
		//判断设备是否被占用
		//读取设备信息
		//return deviceService.readDevice(number);
	}

}

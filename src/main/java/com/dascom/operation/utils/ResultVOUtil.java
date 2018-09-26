package com.dascom.operation.utils;

import com.dascom.operation.vo.ResultVO;

public class ResultVOUtil {
	
	public static ResultVO success(Object object) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(0);
		resultVO.setData(object);
		return resultVO;
	}

	public static ResultVO success() {
		return success(null);
	}
	
	public static ResultVO error(Integer code,String data) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(code);
		resultVO.setData(data);
		return resultVO;
	}
}

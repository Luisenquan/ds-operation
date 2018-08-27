package com.dascom.operation.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dascom.operation.entity.ActiveState;
import com.dascom.operation.service.ActiveStateService;


@Component("activeStateService")
public class ActiveStateServiceImpl implements ActiveStateService{

	@Override
	public List<ActiveState> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActiveState> getByNowDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActiveState> fecthByDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

}

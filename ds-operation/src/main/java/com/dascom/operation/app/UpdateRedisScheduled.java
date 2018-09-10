package com.dascom.operation.app;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dascom.operation.entity.CollectionInterface;
import com.dascom.operation.service.InterfaceService;
import com.dascom.operation.service.RedisService;

/**
 * 定时刷新redis，保持与mongodb数据一致
 * 
 * @author Leisenquan
 * @time 2018年8月24日 上午9:23:38
 * @project_name ds-operation
 */

@Component
public class UpdateRedisScheduled {

	private static final Logger logger = LogManager.getLogger(UpdateRedisScheduled.class);

	@Autowired
	private InterfaceService interfaceService;

	@Autowired
	private RedisService redisService;

	@Scheduled(cron="0 0 1 * * ?") //每天0点更新
	public void updateDate() {
		// 查询mongo获取数据
		List<CollectionInterface> interfaceList = interfaceService.getAllInterface();
		try {
			for (CollectionInterface inter : interfaceList) {
				// 刷新redis
				redisService.copyData(inter);
			}
			logger.info("------定时刷新成功！------");
		} catch (Exception e) {
			logger.info("------定时刷新失败！查看错误日志------");
			logger.error(e.getStackTrace().toString());
		}
	}

}

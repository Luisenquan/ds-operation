package com.dascom.operation.config;

import java.util.concurrent.Executors;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 多线程执行定时任务
 * @author Leisenquan
 * @time 2018年9月12日 上午10:01:33
 * @project_name ds-operation
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer{

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		// 设置一个长度10的定时任务线程池
		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
	}
}

package com.dascom.operation.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Object转Map
 * @author Leisenquan
 * @time 2018年8月22日 上午11:47:12
 * @project_name ds-operation
 */
public class ObjectToMap {

	private static final Logger logger = LogManager.getLogger(ObjectToMap.class);

	public static Map<String, Object> objToMap(Object obj) throws Exception {
		if (obj == null) {
			logger.info("未传入对象");
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] properDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : properDescriptors) {
			String key = property.getName();
			// 默认PropertyDescriptor会有一个class对象，剔除之
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}
		return map;
	}
	
	

}

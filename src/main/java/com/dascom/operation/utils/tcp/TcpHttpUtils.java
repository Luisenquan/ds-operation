package com.dascom.operation.utils.tcp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.SynchronousQueue;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dascom.operation.entity.tcp.ControlEntity;

@Component
public class TcpHttpUtils {

	private static final Logger logger = LogManager.getLogger(TcpHttpUtils.class);

	// 设置连接超时时间
	/*private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000)
			.setConnectTimeout(5000).setSocketTimeout(5000).build();*/

	// 连接控制通道
	public JSONObject connectControl(String controlUrl, ControlEntity control) {
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		String resultLine = null;
		try {
			client = HttpClients.createDefault();
			HttpPost tcpPost = new HttpPost(controlUrl);
			//tcpPost.setConfig(requestConfig);
			tcpPost.setHeader("Content-Type", "application/json");
			String entity = JSON.toJSONString(control);
			tcpPost.setEntity(new StringEntity(entity));
			//获取返回信息
			response = client.execute(tcpPost);
			int code = response.getStatusLine().getStatusCode();
			
			HttpEntity resultEntity = response.getEntity();

			resultLine = EntityUtils.toString(resultEntity, "UTF-8");
			if(code>=400) {
				logger.info("----连接数据通道出错，查看错误日志----");
				logger.error("----"+resultLine+"----");
				return null;
			}
		} catch (UnsupportedEncodingException e) {
			logger.error("----字符编码错误----"+e.getStackTrace().toString());
		} catch (ClientProtocolException e) {
			logger.error("----调用client错误----" + e.getStackTrace().toString());
		} catch (IOException e) {
			logger.error("----调用IO错误----" + e.getStackTrace().toString());
		}
		
		return JSON.parseObject(resultLine);
	}
	
	
	public static void main(String[] args) {
		TcpHttpUtils utils = new TcpHttpUtils();
		
		String controlUrl = "http://192.168.11.143:20003/v1.0/device/control";
		String number = "0036BE7350025400";
		ControlEntity control = new ControlEntity(UUID.randomUUID().toString(), number, "10160000", "00", null);
		JSONObject result = utils.connectControl(controlUrl, control);

		String data = result.get("data").toString();
		System.out.println(data);
		data = data.substring(40, data.length());
		System.out.println(data);
		String[] strs = new String[data.length()-1];
		for(int i=0;i<data.length()-1;i+=2) {
			strs[i] = data.substring(i,i+2);
			System.out.println(strs[i]);
		}
	}

}

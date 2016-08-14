/*
 * @(#)HttpRequestHandler.java	V0.0.1 2015年8月10日, 下午3:00:43
 *
 * Copyright 2015 www.ifood517.com. All rights reserved.
 * www.ifood517.com PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.joken.notice.message.util;

import java.nio.charset.Charset;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * HTTP请求工具类
 * <p>。
 * @version V0.0.1, Aug 10, 2015
 * @author  Hanzibin
 * @since   V0.0.1
 */
public class HttpRequestHandler {
	private final static Logger log = Logger.getLogger(HttpRequestHandler.class);
	
	private String url;
	private String postData;
	
	public static RequestConfig requestConfig = null;
	
	/**
	 * 发送post请求
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return String 请求返回结果
	 */
	public String sendPostRequest(){
		CloseableHttpResponse httpResponse = null;
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(300);
		connManager.setDefaultMaxPerRoute(20);
		if (requestConfig == null) {
			requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(10000)
					.setStaleConnectionCheckEnabled(true)
					.setConnectTimeout(10000).setSocketTimeout(10000).build();
		}
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig).build();

		HttpPost hp = new HttpPost(url);
		try {
			StringEntity entity = new StringEntity(postData, Charset.forName("UTF-8"));//解决中文乱码问题    
            entity.setContentEncoding("UTF-8");    
            entity.setContentType("application/json");    
            hp.setEntity(entity);    
			httpResponse = httpClient.execute(hp);
			return EntityUtils.toString(httpResponse.getEntity());  
		}catch(Throwable t){
			t.printStackTrace();
			log.info("发送Http请求："+postData.toString()+"失败");
		}finally {
			try {
				hp.releaseConnection();
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	/**
	 * 发送get请求
	 * @Auther Hanzibin
	 * @date 3:18:48 PM,Mar 11, 2016
	 * @return String 请求返回结果
	 */
	public String sendGetRequest() {
		CloseableHttpResponse httpResponse = null;
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(300);
		connManager.setDefaultMaxPerRoute(20);
		
		if (requestConfig == null) {
			requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(10000)
					.setStaleConnectionCheckEnabled(true)
					.setConnectTimeout(10000).setSocketTimeout(10000).build();
		}
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(requestConfig).build();
		HttpGet hp = new HttpGet(url);
		try {
			httpResponse = httpClient.execute(hp);
			if(httpResponse.getStatusLine().getStatusCode() == 200)  
				return EntityUtils.toString(httpResponse.getEntity());  
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				hp.releaseConnection();
			} catch (Exception e) {
			}
		}
		return null;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

	
}

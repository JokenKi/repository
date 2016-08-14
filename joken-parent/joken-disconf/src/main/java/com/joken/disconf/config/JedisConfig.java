package com.joken.disconf.config;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * jedis 配置模型类
 * 
 * @author wangby
 * @date 2016年7月5日上午9:47:21
 */
@Component
@Scope("singleton")
@DisconfFile(filename = "redis.properties")
public class JedisConfig {

	private String host;
	private int port;
	private int maxActive;
	private int maxIdle;

	@DisconfFileItem(name = "redis.maxActive", associateField = "maxActive")
	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	@DisconfFileItem(name = "redis.maxIdle", associateField = "maxIdle")
	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	@DisconfFileItem(name = "redis.host", associateField = "host")
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@DisconfFileItem(name = "redis.port", associateField = "port")
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

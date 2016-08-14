package com.joken.disconf.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import com.joken.common.cache.RedisTemplate;
import com.joken.common.utils.StringUtils;
import com.joken.disconf.config.JedisConfig;

@Service
public class JedisFactory implements InitializingBean, DisposableBean {

	@Autowired
	private JedisConfig jedisConfig;

	@Autowired
	private RedisTemplate redisTemplate;

	private ShardedJedisPool jedisPool;

	private JedisPoolConfig jedisPoolConfig;

	private JedisShardInfo jedisShardInfo;

	/**
	 * 初始化jedis连接池
	 * 
	 * @return void
	 * @date 2016年7月5日上午11:33:43
	 */
	public void init() {
		synchronized (this) {
			if (jedisPool != null && !jedisPool.isClosed()) {
				return;
			}

			if (StringUtils.isEmpty(jedisConfig.getHost())
					|| jedisConfig.getPort() < 1) {
				return;
			}
			jedisShardInfo = new JedisShardInfo(jedisConfig.getHost(),
					jedisConfig.getPort());
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			shards.add(jedisShardInfo);
			jedisPoolConfig = new JedisPoolConfig();
			jedisPoolConfig.setTestOnBorrow(true);
			jedisPoolConfig.setMaxIdle(jedisConfig.getMaxIdle());
			jedisPoolConfig.setMaxTotal(jedisConfig.getMaxActive());
			jedisPool = new ShardedJedisPool(jedisPoolConfig, shards);

			// 连接池放入redis操作模板
			redisTemplate.setJedisPool(jedisPool);
			// redisTemplate.setValue("test", "111111");

		}
	}

	public ShardedJedisPool getJedisPool() {
		return jedisPool;
	}

	/**
	 * 
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	/**
	 * 释放资源
	 */
	@Override
	public void destroy() throws Exception {
		synchronized (this) {
			if (jedisPool == null) {
				return;
			}
			if (jedisPool.isClosed()) {
				return;
			}
			// System.out.println(redisTemplate.getValue("test"));
			jedisPool.close();
			jedisPool = null;
			jedisPoolConfig = null;
			jedisShardInfo = null;
		}
	}
}

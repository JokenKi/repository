package com.joken.disconf.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfUpdateService;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.joken.disconf.config.JedisConfig;
import com.joken.disconf.factory.JedisFactory;

@Service
@DisconfUpdateService(classes = { JedisConfig.class })
public class JedisConfigUpdateCallback implements IDisconfUpdate {

	@Autowired
	private JedisFactory jedisFactory;

	@Override
	public void reload() throws Exception {
		jedisFactory.destroy();
		jedisFactory.init();
	}
}

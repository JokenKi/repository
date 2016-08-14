package com.joken.common.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.joken.common.logger.Logger;
import com.joken.common.logger.LoggerFactory;

/**
 * Redis缓存操作模板类
 * 
 * @author inkcar
 * @version 0.0.0.1 下午06:34:07
 */
public class RedisTemplate {

	private static Logger logger = LoggerFactory.getLogger("redis");
	/**
	 * jedis连接池
	 */
	private ShardedJedisPool jedisPool;

	/**
	 * 设置jedis连接池
	 * 
	 * @param jedisPool
	 *            jedis池
	 */
	public void setJedisPool(ShardedJedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	/**
	 * 获取指定键的缓存值
	 * 
	 * @param key
	 *            键名
	 * @return String
	 */
	public String getValue(String key) {
		String value = null;
		ShardedJedis jedis = this.getResource();
		try {
			value = jedis.get(key);
			return value;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取指定键的缓存值,将清除该缓存
	 * 
	 * @param key
	 *            键名
	 * @return String
	 */
	public String shift(String key) {
		String value = null;
		ShardedJedis jedis = this.getResource();
		try {
			value = jedis.get(key);
			if (value != null) {
				jedis.del(key);
			}
			return value;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置指定键缓存
	 * 
	 * @param key
	 *            缓存名
	 * @param value
	 *            缓存值
	 * @return boolean
	 */
	public boolean setValue(String key, String value) {
		return this.setValue(key, value, 0);
	}

	/**
	 * 设置指定键缓存
	 * 
	 * @param key
	 *            缓存名
	 * @param value
	 *            缓存值
	 * @param second
	 *            过期时间
	 * @return boolean
	 */
	public boolean setValue(String key, String value, int second) {
		ShardedJedis jedis = this.getResource();
		try {
			if (second > 0) {
				jedis.setex(key, second, value);
			} else {
				jedis.set(key, value);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置指定键缓存
	 * 
	 * @param keys
	 *            缓存名
	 * @param value
	 *            缓存值
	 * @param second
	 *            过期时间
	 * @return boolean
	 */
	public boolean setValue(String[] keys, String value, int second) {
		ShardedJedis jedis = this.getResource();
		try {
			for (String key : keys) {
				if (second > -1) {
					jedis.setex(key, second, value);
					continue;
				}
				jedis.set(key, value);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 删除指定键缓存数据
	 * 
	 * @param key
	 *            需要删除的键
	 * @param keys
	 *            其他需要删除的键
	 * @return boolean
	 */
	public boolean del(String key, String... keys) {
		ShardedJedis jedis = this.getResource();
		try {
			jedis.del(key);
			for (String key2 : keys) {
				jedis.del(key2);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键名
	 * @param map
	 *            map对象值
	 * @return boolean
	 */
	public boolean setObjectMap(String key, Map<String, Object> map) {
		return this.setObjectMap(key, 0, map);
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键名
	 * @param map
	 *            map对象值
	 * @param expire
	 *            过期时间，单位:秒
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean setObjectMap(String key, int expire, Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return false;
		}
		Map<String, String> tmp = new HashMap<String, String>();
		String mapKey;
		Object tmpMap;
		// 将Map<String, Object>转为Map<String, String>
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			mapKey = it.next();
			tmpMap = map.get(mapKey);
			if (tmpMap == null) {
				continue;
			}
			if (tmpMap instanceof Map) {
				this.setObjectMap(key + ":" + mapKey, expire,
						(Map<String, Object>) tmpMap);
			} else {
				tmp.put(mapKey, map.get(mapKey).toString());
			}

		}
		if (tmp.size() == 0) {
			return true;
		}
		if (key.indexOf("::") != -1) {
			key = key.replace("::", ":");
		}
		return this.setMap(key, tmp, expire);
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键名
	 * @param map
	 *            map对象值
	 * @return boolean
	 */
	public boolean setMap(String key, Map<String, String> map) {
		return this.setMap(key, map, 0);
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            键名
	 * @param map
	 *            map对象值
	 * @param expire
	 *            过期时间，单位:秒
	 * @return boolean
	 */
	public boolean setMap(String key, Map<String, String> map, int expire) {
		ShardedJedis jedis = this.getResource();
		try {
			jedis.hmset(key, map);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置Map缓存
	 * 
	 * @param key
	 *            缓存键名
	 * @param mapKey
	 *            map键名
	 * @param mapValue
	 *            map键对应值
	 * @return boolean
	 */
	public boolean setMap(String key, String mapKey, String mapValue) {
		ShardedJedis jedis = this.getResource();
		try {
			jedis.hset(key, mapKey, mapValue);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取map缓存值
	 * 
	 * @param key
	 *            需要获取的键名
	 * @return Map<String, String>
	 */
	public Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		ShardedJedis jedis = this.getResource();
		try {
			value = jedis.hgetAll(key);
			return value;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取map缓存值
	 * 
	 * @param key
	 *            需要获取的缓存键名
	 * @param mapKey
	 *            map缓存键名
	 * @return String
	 */
	public String getMap(String key, String mapKey) {
		String value = null;
		ShardedJedis jedis = this.getResource();
		try {
			value = jedis.hget(key, mapKey);
			return value;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 删除map缓存值指定键值
	 * 
	 * @param key
	 *            需要获取的缓存键名
	 * @param mapKey
	 *            map缓存键名
	 * @return long
	 */
	public long delMap(String key, String mapKey) {
		long value = 0;
		ShardedJedis jedis = this.getResource();
		try {
			value = jedis.hdel(key, mapKey);
		} catch (Exception e) {
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
		return value;
	}

	/**
	 * 检查指定键名的缓存是否存在
	 * 
	 * @param key
	 *            需要检查的键名
	 * @return boolean
	 */
	public boolean isExist(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			boolean result = jedis.exists(key);
			return result;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 返回key值+1，redis-server端是单线程来处理client端的请求的，可以用此来生成id
	 * 
	 * @param key
	 *            需要获取增长序号的键名
	 * @return long
	 */
	public long incr(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			long value = jedis.incr(key);
			return value;
		} catch (Exception e) {
			return 0l;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 返回key值+k
	 * 
	 * @param key
	 *            键名
	 * @param value
	 *            需要累加的值
	 * @return long
	 */
	public synchronized long incrBy(String key, long value) {
		ShardedJedis jedis = this.getResource();
		try {
			return jedis.incrBy(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 返回key值+k
	 * 
	 * @param key
	 *            键名
	 * @param value
	 *            需要累加的值
	 * @return long
	 */
	public synchronized long incrBy(String key, long value, int expire) {
		ShardedJedis jedis = this.getResource();
		try {
			long val = jedis.incrBy(key, value);
			jedis.expire(key, expire);
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 向List头部插入数据
	 * 
	 * @param key
	 *            list键名
	 * @param values
	 *            需要push的值
	 * @return boolean
	 */
	public boolean lpush(String key, String values) {
		return this.lpush(key, 0, values);
	}

	/**
	 * 向List头部插入数据
	 * 
	 * @param key
	 *            list键名
	 * @param expire
	 *            过期时间，单位:秒
	 * @param values
	 *            需要push的值
	 * @return boolean
	 */
	public boolean lpush(String key, int expire, String... values) {
		ShardedJedis jedis = this.getResource();
		try {
			jedis.lpush(key, values);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 添加set缓存对象
	 * 
	 * @param keyValue
	 *            需要设置缓存的键值对
	 * @param expire
	 *            过期时间，单位:秒
	 * @return boolean
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:49:40
	 */
	public boolean lpush(Map<String, ? extends Collection<String>> keyValue,
			int expire) {
		if (keyValue == null || keyValue.size() == 0) {
			return false;
		}
		ShardedJedis jedis = this.getResource();
		try {
			String key;
			for (Iterator<String> it = keyValue.keySet().iterator(); it
					.hasNext();) {
				key = it.next();
				jedis.del(key);
				jedis.lpush(key, keyValue.get(key).toArray(new String[0]));
				if (expire > 0) {
					jedis.expire(key, expire);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 弹出交获取List头数据
	 * 
	 * @param key
	 *            list键名
	 * @return String
	 */
	public String lpop(String key) {
		String val = null;
		ShardedJedis jedis = this.getResource();
		try {
			val = jedis.lpop(key);
		} catch (Exception e) {
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
		return val;
	}

	/**
	 * 向List尾部部插入数据
	 * 
	 * @param key
	 *            list键名
	 * @param value
	 *            需要push的值
	 * @return boolean
	 */
	public boolean rpush(String key, String... value) {
		return this.rpush(key, 0, value);
	}

	/**
	 * 向List尾部部插入数据
	 * 
	 * @param key
	 *            list键名
	 * @param expire
	 *            过期时间，单位:秒
	 * @param value
	 *            需要push的值
	 * @return boolean
	 */
	public boolean rpush(String key, int expire, String... value) {
		ShardedJedis jedis = this.getResource();
		try {
			jedis.rpush(key, value);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 弹出交获取List尾部数据
	 * 
	 * @param key
	 *            list键名
	 * @return String
	 */
	public String rpop(String key) {
		String val = null;
		ShardedJedis jedis = this.getResource();
		try {
			val = jedis.rpop(key);
		} catch (Exception e) {
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
		return val;
	}

	/**
	 * 获取list大小
	 * 
	 * @param key
	 *            需要获取大小的键名
	 * @return long
	 * @author 欧阳增高
	 * @date 2015-9-7 上午6:35:18
	 */
	public long llen(String key) {
		long val = 0l;
		ShardedJedis jedis = this.getResource();
		try {
			val = jedis.llen(key);
		} catch (Exception e) {
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
		return val;
	}

	/**
	 * 从list中取值
	 * 
	 * @param key
	 *            键名
	 * @return List<String>
	 */
	public List<String> lrange(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			List<String> list = jedis.lrange(key, 0, -1);
			return list;
		} catch (Exception e) {
			return Collections.emptyList();
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 从list中取所有值,并清除所有缓存
	 * 
	 * @param key
	 *            键名
	 * @return List<String>
	 */
	public List<String> fullPop(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			List<String> list = jedis.lrange(key, 0, -1);
			jedis.del(key);
			return list;
		} catch (Exception e) {
			return Collections.emptyList();
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 添加set缓存对象
	 * 
	 * @param key
	 *            set对象键名
	 * @param values
	 *            需要添加的set值
	 * @return boolean
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:49:40
	 */
	public boolean sadd(String key, String... values) {
		return this.sadd(key, 0, values);
	}

	/**
	 * 添加set缓存对象
	 * 
	 * @param keyValue
	 *            需要设置缓存的键值对
	 * @param expire
	 *            过期时间，单位:秒
	 * @return boolean
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:49:40
	 */
	public boolean sadd(Map<String, ? extends Collection<String>> keyValue,
			int expire) {
		if (keyValue == null || keyValue.size() == 0) {
			return false;
		}
		ShardedJedis jedis = this.getResource();
		try {
			String key;
			for (Iterator<String> it = keyValue.keySet().iterator(); it
					.hasNext();) {
				key = it.next();
				jedis.sadd(key, keyValue.get(key).toArray(new String[0]));
				if (expire > 0) {
					jedis.expire(key, expire);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 添加set缓存对象
	 * 
	 * @param key
	 *            set对象键名
	 * @param expire
	 *            过期时间，单位:秒
	 * @param values
	 *            需要添加的set值
	 * @return boolean
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:49:40
	 */
	public boolean sadd(String key, int expire, String... values) {
		ShardedJedis jedis = this.getResource();
		try {
			jedis.sadd(key, values);
			if (expire > 0) {
				jedis.expire(key, expire);
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 添加set缓存对象
	 * 
	 * @param key
	 *            set对象键名
	 * @param expires
	 *            过期时间，单位:秒
	 * @param values
	 *            需要添加的set值
	 * @return boolean
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:49:40
	 */
	public boolean sadd(String[] key, Integer[] expires, String... values) {
		ShardedJedis jedis = this.getResource();
		try {
			int expire = 0;
			boolean isdelete = (values == null || values.length == 0);
			if (isdelete) {
				this.del(key[0], key);
				return true;
			}
			for (int i = 0, len = key.length; i < len; i++) {
				jedis.sadd(key[i], values);
				if (expires == null || expires.length == 0) {
					continue;
				}
				expire = i < expires.length ? expires[i] : expires[0];
				if (expire < 0 && expire != -1) {
					expire = 0;
				}
				jedis.expire(key[i], expire);
			}

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			this.returnResource(jedis);
		}
	}

	/**
	 * 移除并返回set集合中的一个随机元素
	 * 
	 * @param key
	 *            set集合键名
	 * @return String
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:56:28
	 */
	public String spop(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			String result = jedis.spop(key);
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 移除并set集合中的多个值
	 * 
	 * @param keys
	 *            set集合键名
	 * @return Long
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:56:28
	 */
	public Long srem(String[] keys, String... members) {
		ShardedJedis jedis = this.getResource();
		try {
			Long result = 0l;
			for (String key : keys) {
				result += jedis.srem(key, members);
			}
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 移除并set集合中的多个值
	 * 
	 * @param key
	 *            set集合键名
	 * @return Long
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:56:28
	 */
	public Long srem(String key, String... members) {
		ShardedJedis jedis = this.getResource();
		try {
			Long result = jedis.srem(key, members);
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取Set的中的一个随机元素
	 * 
	 * @param key
	 *            set集合键名
	 * @return Set<String>
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:56:28
	 */
	public String srandmember(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			return jedis.srandmember(key);
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取Set的全部数据
	 * 
	 * @param key
	 *            set集合键名
	 * @return Set<String>
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:56:28
	 */
	public Set<String> sinter(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			Set<String> result = jedis.smembers(key);
			return result;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取Set的全部数据
	 * 
	 * @param keys
	 *            set集合键名
	 * @return List<Set<String>>
	 * @author 欧阳增高
	 * @date 2015-8-26 上午10:56:28
	 */
	public Map<String, Set<String>> getSet(String... keys) {
		ShardedJedis jedis = this.getResource();
		try {
			Map<String, Set<String>> list = new HashMap<String, Set<String>>();
			Set<String> result;
			for (String key : keys) {
				result = jedis.smembers(key);
				if (result != null && result.size() > 0) {
					list.put(key, result);
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 设置指定键名的过期时间
	 * 
	 * @param key
	 *            需要设置过期时间缓存的键名
	 * @param expire
	 *            过期时间，单位:秒
	 * @return Long
	 * @author 欧阳增高
	 * @date 2015-8-31 下午3:08:47
	 */
	public Long expire(String key, int expire) {
		ShardedJedis jedis = this.getResource();
		try {
			Long amount = jedis.expire(key, expire);
			return amount;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取指定键的失效时间,单位：秒
	 * 
	 * @param key
	 *            缓存键名
	 * @return Long
	 * @author 欧阳增高
	 * @date 2015-11-24 下午1:23:58
	 */
	public Long ttl(String key) {
		ShardedJedis jedis = this.getResource();
		try {
			Long amount = jedis.ttl(key);
			return amount;
		} catch (Exception e) {
			return null;
		} finally {
			if (jedis != null) {
				this.returnResource(jedis);
			}
		}
	}

	/**
	 * 获取redis缓存源
	 * 
	 * @return ShardedJedis
	 */
	public ShardedJedis getResource() {
		// logger.info("获取redis操作连接池");
		// 断言 ，当前锁是否已经锁住，如果锁住了，就啥也不干，没锁的话就执行下面步骤
		// assert !lockJedis.isHeldByCurrentThread();
		ShardedJedis jedis = null;
		try {
			if (jedisPool != null) {
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			logger.error("获取 redis连接池错误 ", e);
		}
		if (jedis == null) {
			logger.info("无可用redis操作连接池");
		}
		return jedis;
	}

	/**
	 * 返回缓存源
	 * 
	 * @param jedis
	 *            需要设置的jedis
	 */
	@SuppressWarnings("deprecation")
	public void returnResource(ShardedJedis jedis) {
		jedisPool.returnResource(jedis);
	}
}

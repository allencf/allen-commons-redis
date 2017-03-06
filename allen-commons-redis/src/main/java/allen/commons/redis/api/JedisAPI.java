package allen.commons.redis.api;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 该类提供jedis相关操作 API
 * All rights Reserved, Designed By HQYG
 * Copyright:   Copyright(C) 2016
 * Company:     HQYG.
 * author:      cf
 * Createdate:  2017年3月6日下午3:19:21
 */
public class JedisAPI implements Serializable{

	private static final long serialVersionUID = 7140599341737537733L;

	private final static Logger logger = LoggerFactory.getLogger(Jedis.class);
	
	//最大连接数
	private static Integer maxActive = 1000;
	
	//最大空闲数
	private static Integer maxIdle = 20;
	
	//超时时间
	private static Integer maxWait = 3000;
	
	//主机IP
	private static String hostIp = "10.33.3.225";
	
	//端口
	private static Integer port = 6379;
	
	//池化管理jedis连接池
	private static JedisPool jedisPool;
	

	static {
		 JedisPoolConfig config = new JedisPoolConfig();
		 //设置最大连接数
		 config.setMaxTotal(maxActive);
		 //设置最大空闲数
		 config.setMaxIdle(maxIdle);
		 //设置超时时间
		 config.setMaxWaitMillis(maxWait);
		 //初始化连接池
		 jedisPool = new JedisPool(config, hostIp, port);
	}
	
	
	/**
	 * @description  向缓存中set字符串
	 * @param   key  key 
	 * @param value  value
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean setString(String key,String value){
		if(StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
			return false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("jedis操作异常",e);
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	
	/**
	 * @description  向缓存中set对象 
	 * @param   key  key
	 * @param   obj  需要set的对象
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean setObj(String key,Object obj){
		if(StringUtils.isEmpty(key) || obj == null)
			return false;
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			//JSON.toJSONString(obj).getBytes()
			//SerializeUtil.serialize(obj)
			jedis.set(key.getBytes(), JSON.toJSONString(obj).getBytes());
			return true;
		} catch (Exception e) {
			logger.error("jedis操作redis异常",e);
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	
	
	/**
	 * @description 从缓存中获取信息
	 * @param   key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public  static <T> T getObj(String key, Class<T> classType){
		if(StringUtils.isEmpty(key))
			return null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] value = jedis.get(key.getBytes());
			String jsonStr = new String(value);
			//Object jsonStr = SerializeUtil.unserialize(value);
			T t = JSON.parseObject(jsonStr.toString(), classType);
			return t;
		} catch (Exception e) {
			logger.error("jedis操作redis异常",e);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	
	/**
	 * @description 从缓存中获取字符串信息
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getString(String key){
		if(StringUtils.isEmpty(key))
			return null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} catch (Exception e) {
			logger.error("jedis操作异常",e);
			return null;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	
	/**
	 * @description 删除缓存信息
	 * @param key
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean del(String key){
		if(StringUtils.isEmpty(key))
			return false;
		
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			logger.error("jedis操作redis异常",e);
			return false;
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	
	
	public static Integer getMaxActive() {
		return maxActive;
	}

	public static void setMaxActive(Integer maxActive) {
		JedisAPI.maxActive = maxActive;
	}

	public static Integer getMaxIdle() {
		return maxIdle;
	}

	public static void setMaxIdle(Integer maxIdle) {
		JedisAPI.maxIdle = maxIdle;
	}

	public static Integer getMaxWait() {
		return maxWait;
	}

	public static void setMaxWait(Integer maxWait) {
		JedisAPI.maxWait = maxWait;
	}

	public static String getHostIp() {
		return hostIp;
	}

	public static void setHostIp(String hostIp) {
		JedisAPI.hostIp = hostIp;
	}

	public static Integer getPort() {
		return port;
	}

	public static void setPort(Integer port) {
		JedisAPI.port = port;
	}

	public static Logger getLogger() {
		return logger;
	}



	public static void main(String[] args) {
		

	}

}

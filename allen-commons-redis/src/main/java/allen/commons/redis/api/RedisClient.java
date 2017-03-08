package allen.commons.redis.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;


/**
 * RedisClient  操作 
 * All rights   Reserved, Designed By HQYG
 * Copyright:   Copyright(C) 2016
 * Company:     HQYG.
 * author:      cf
 * Createdate:  2017年3月8日上午10:40:28
 */
public class RedisClient implements Serializable {

	//url http://www.tuicool.com/articles/vYVvQby
	
	private static final long serialVersionUID = 1357749867950858521L;
	
	private Jedis            jedis;            //非切片客户端连接
	private JedisPool        jedisPool;        //非切片连接池
	private ShardedJedis     shardedJedis;     //切片客户端连接
	private ShardedJedisPool shardedJedisPool; //切片连接池
	
	
	//最大连接数
	private static Integer  maxActive = 20;
	//最大空闲数
	private static Integer  maxIdle = 5;
	//最大等待时间
	private static Long     maxWait = 1000l;
	//主机IP
	private static String   host = "10.33.3.225";
	//端口号
	private static Integer  port = 6379;
	
	public RedisClient() {
		initPool();
		initShardedPool();
	}


	/**
	 * 初始化非切片连接池
	 */
	private void initPool(){
	    //池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(false); 
        jedisPool = new JedisPool(config , host , port);
	}

	
	/**
	 * 初始化切片连接池
	 */
	private void initShardedPool(){
		//池基本配置 
        JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxTotal(maxActive);
        config.setMaxIdle(5); 
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(false); 
        //slave链接 
        List<JedisShardInfo> shards = new ArrayList<>(); 
        shards.add(new JedisShardInfo("127.0.0.1", 6379, "master")); 
        //构造池 
        shardedJedisPool = new ShardedJedisPool(config, shards); 
	}
	
	

	public Jedis getJedis() {
		return jedis;
	}


	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}


	public JedisPool getJedisPool() {
		return jedisPool;
	}


	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}


	public ShardedJedis getShardedJedis() {
		return shardedJedis;
	}


	public void setShardedJedis(ShardedJedis shardedJedis) {
		this.shardedJedis = shardedJedis;
	}


	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}


	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}


	public static Integer getMaxActive() {
		return maxActive;
	}


	public static void setMaxActive(Integer maxActive) {
		RedisClient.maxActive = maxActive;
	}


	public static Integer getMaxIdle() {
		return maxIdle;
	}


	public static void setMaxIdle(Integer maxIdle) {
		RedisClient.maxIdle = maxIdle;
	}


	public static Long getMaxWait() {
		return maxWait;
	}


	public static void setMaxWait(Long maxWait) {
		RedisClient.maxWait = maxWait;
	}


	public static String getHost() {
		return host;
	}


	public static void setHost(String host) {
		RedisClient.host = host;
	}


	public static Integer getPort() {
		return port;
	}


	public static void setPort(Integer port) {
		RedisClient.port = port;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

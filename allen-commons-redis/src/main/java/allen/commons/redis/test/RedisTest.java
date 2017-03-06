package allen.commons.redis.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {


		private static final Logger logger = LoggerFactory.getLogger(RedisTest.class);
		
		private static JedisPoolConfig jedisPoolConfig;
		
		/**
		 * jedis对象
		 */
		private static Jedis jedis;
		
		/**
		 * 最大连接数
		 */
		private static Integer maxActive = 1000;
		
		/**
		 * 最大空闲数
		 */
		private static Integer maxIdle = 20;
		
		/**
		 * 超时时间
		 */
		private static Integer maxWait = 3000;
		
		/**
		 * 主机IP
		 */
		private static String hostIp = "10.33.3.225";
		
		/**
		 * 端口
		 */
		private static Integer port = 6379;
		
		
		private RedisTemplate redisTemplate;
		
		
		/**
		 * 获取jedis对象
		 * @return
		 */
		public static Jedis getJedis(){
			if(jedisPoolConfig == null){
				jedisPoolConfig = new JedisPoolConfig();
				jedisPoolConfig.setMaxActive(maxActive);
				jedisPoolConfig.setMaxIdle(maxIdle);
				jedisPoolConfig.setMaxWait(maxWait);
			}
			JedisPool jedisPool= new JedisPool(jedisPoolConfig, hostIp, port);
			jedis = jedisPool.getResource();
			return jedis;
		}
		
		
		public static void set(String key,Object value){
			try{
				Jedis jedis = getJedis();
				jedis.set(key.getBytes(), serialize(value));
				logger.info("操作成功!!!");
			}
			catch (Exception e) {
				logger.error("操作失败,异常信息:"+e.getMessage(),e);
			}
			finally{
				//jedisPool.returnResource(jedis);
			}
		} 
		
		
		@SuppressWarnings("unchecked")
		public String saveTestBean(TestBean1 test){
			
			return (String) redisTemplate.execute(new RedisCallback<String>(){
			
				@Override
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] key = getKey(String.valueOf(test.getId()));
					byte[] value = com.alibaba.fastjson.JSON.toJSONString(test).getBytes();
					connection.set(key, value);
					return null;
				}
		   });
			
		}
		
		
		public static void set(String key,String value){
			try{
				Jedis jedis = getJedis();
				jedis.set(key, value);
				logger.info("操作成功!!!");
			}
			catch (Exception e) {
				logger.error("操作异常,异常信息:"+e.getMessage(),e);
			}
		}

		
		public static Object get(String key){
			Jedis jedis = getJedis();
			
			return null;
		}
		
		/*104      * 根据key 获取内容
		105      *
		106      * @param key
		107      * @return
		108      
		109     public static Object get(String key) {
		110 
		111         Jedis jedis = null;
		112         try {
		113             jedis = jedisPool.getResource();
		114             byte[] value = jedis.get(key.getBytes());
		115             return SerializeUtil.unserialize(value);
		116         } catch (Exception e) {
		117             e.printStackTrace();
		118             return false;
		119         } finally {
		120             jedisPool.returnResource(jedis);
		121         }
		122     }
		*/
		
		
		/**
		 * 序列化
		 * @param object
		 * @return
		 */
	    public static byte[] serialize(Object object) {
	        ObjectOutputStream oos = null;
	        ByteArrayOutputStream baos = null;
	        try {
	         // 序列化
	             baos = new ByteArrayOutputStream();
	             oos = new ObjectOutputStream(baos);
	             oos.writeObject(object);
	             byte[] bytes = baos.toByteArray();
	              return bytes;
	          } catch (Exception e) {
	  
	         }
	          return null;
	    }
			  
	    /**
	     * 反序列化
	     * @param bytes
	     * @return
	     */
	    public static Object unserialize(byte[] bytes) {
	        ByteArrayInputStream bais = null;
	        try {
	          // 反序列化
	              bais = new ByteArrayInputStream(bytes);
	              ObjectInputStream ois = new ObjectInputStream(bais);
	              return ois.readObject();
	          } catch (Exception e) {
	  
	          }
	          return null;
	    }
		
		public TestBean1 getTestBean(Integer id,String name){
			return TestBean1.getInstance(id, name);
		}
		
		
		public static byte[] getKey(String id){
			return ("HQYG:Allen:test:" + id).getBytes();
		}
	    
	    
		public static void main(String[] args) {
			Jedis jedis = getJedis();
			//jedis.set("allen", "aaa");
			
			TestBean1 test = TestBean1.getInstance(12, "allen");
			byte[] key   = getKey(String.valueOf(test.getId()));
			byte[] value = JSON.toJSONString(test).getBytes();
			
			jedis.set(key, value);
			logger.info("操作成功!!!");
		}
		

	


}

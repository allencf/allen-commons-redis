package allen.commons.redis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import allen.commons.redis.api.JedisAPI;

public class Test1 {

	private final static Logger logger = LoggerFactory.getLogger(Test1.class);
	
	public static void main(String[] args) {
		TestBean1 bean1 = TestBean1.getInstance(111, "allenOne");
		/*JedisAPI.setObj("HQYG:ALLEN:TEST:allen", bean1);
		
		JedisAPI.setString("HQYG:ALLEN:TEST:allenone", "allen");*/
		TestBean1 test = JedisAPI.getObj("HQYG:ALLEN:TEST:allen" , TestBean1.class);
		
		System.out.println(JSON.toJSONString(test));
		
		logger.info("操作成功!!!");
	}

}

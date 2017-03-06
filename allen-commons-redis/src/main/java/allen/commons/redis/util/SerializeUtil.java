package allen.commons.redis.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeUtil implements Serializable{

	private static final long serialVersionUID = -8580531504951321703L;

	private static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);
	
	
	/**
	 * @description   序列化对象
	 * @param object  需要序列化的对象
	 * @return
	 */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
           //序列化
           baos = new ByteArrayOutputStream();
           oos  = new ObjectOutputStream(baos);
           oos.writeObject(object);
           byte[] bytes = baos.toByteArray();
           return bytes;
        } catch (Exception e) {
           logger.error("序列化异常",e);
           return null;
        }
    }
		  
    /**
     * @description 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            //反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
        	logger.error("反序列化异常",e);
        	return null;
        }
    }
	
	

	public static void main(String[] args) {

	}

}

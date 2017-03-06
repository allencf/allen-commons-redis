package allen.commons.redis.test;

import java.io.Serializable;

public class TestBean1 implements Serializable{
	

	private static final long serialVersionUID = -2730211713088606559L;
	
	private Integer id;
	private String  name;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public static TestBean1 getInstance(Integer id,String name){
		TestBean1 bean = new TestBean1();
		bean.setId(id);
		bean.setName(name);
		return bean;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

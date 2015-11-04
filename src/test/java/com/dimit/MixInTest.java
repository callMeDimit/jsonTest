package com.dimit;

import java.io.IOException;

import org.junit.Test;

import com.dimit.bean.Bird;
import com.dimit.bean.BirdMixIn;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用于将第三方类(我们没有源码的类)进行json转换
 * 
 * @author dimit
 */
public class MixInTest {

	@Test
	public void test() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixIn(Bird.class, BirdMixIn.class);
		Bird bird = new Bird("scarlet Ibis");
		bird.setSound("eee");
		bird.setHabitat("water");
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(System.out, bird);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

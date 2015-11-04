package com.dimit;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dimit.bean.Animal;
import com.dimit.bean.Elephant;
import com.dimit.bean.Horse;
import com.dimit.bean.Lion;
import com.dimit.bean.Zoo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * List转换测试
 * 
 * @author dimit
 *
 */
public class ListJsonTest {
	TypeFactory typeFactory = TypeFactory.defaultInstance();

	/**
	 * 序列化
	 */
	@Test
	public void listToJsontest() {
		Zoo zoo = new Zoo("London Zoo", "London");
		Lion lion = new Lion("Simba");
		Elephant elephant = new Elephant("Manny");
		zoo.add(elephant).add(lion);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, zoo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(writer.toString());
	}

	/**
	 * 反序列化
	 */
	@Test
	public void jsonToList() {
		ObjectMapper mapper = new ObjectMapper();
		String json = "{\"name\":\"London Zoo\",\"city\":\"London\","
				+ "\"animals\":[{\"name\":\"Manny\"},{\"name\":\"Simba\"}]}";
		try {
			Zoo z = mapper.readValue(json, Zoo.class);
			System.out.println(z);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接序列化集合
	 */
	@Test
	public void listDecritToJsonTest() {
		List<Horse> animals = new ArrayList<Horse>();
		Horse b1 = new Horse("h1");
		animals.add(b1);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, animals);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(writer.toString());
		// 反序列化
		JavaType type = typeFactory.constructCollectionType(ArrayList.class, Horse.class);
		try {
			ArrayList<Animal> list = mapper.readValue("{\"name\":\"h1\"}", type);
			System.out.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)作用
	 * 将单个对象强制装换为集合
	 */
	@Test
	public void listDecrit2JsonTest() {
		ObjectMapper mapper = new ObjectMapper();
//		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		// 反序列化
		JavaType type = typeFactory.constructCollectionType(ArrayList.class, Horse.class);
		try {
			ArrayList<Animal> list = mapper.readValue("{\"name\":\"h1\"}", type);
			System.out.println(list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

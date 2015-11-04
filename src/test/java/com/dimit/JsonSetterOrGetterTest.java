package com.dimit;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dimit.bean.DatasetDynamic;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSetterOrGetterTest {
	private static DatasetDynamic dd;

	@BeforeClass
	public static void beforeClz() {
		dd = new DatasetDynamic("id", "title");
		dd.set("name", "i'm name");
		dd.set("age", 1);
	}
	
	/**
	 * 测试@JsonAnyGetter和@JsonAnySetter用法
	 */
	@Test
	public void writeTest() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, dd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//reader...
		try {
			dd = mapper.readValue(writer.toString(), DatasetDynamic.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(dd);
	}

}

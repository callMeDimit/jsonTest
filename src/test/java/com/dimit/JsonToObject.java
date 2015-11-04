package com.dimit;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dimit.bean.Album;
import com.dimit.bean.Artist;
import com.dimit.bean.Cat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class JsonToObject {
	private static String json;

	@BeforeClass
	public static void beforClz() throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		Album album = new Album("Kind Of Blue");
		// album.setLinks(new String[] { "link1", "link2" });
		List<String> songs = new ArrayList<String>();
		songs.add("So What");
		songs.add("Flamenco Sketches");
		songs.add("Freddie Freeloader");
		album.setSongs(songs);
		Artist artist = new Artist();
		artist.name = "Miles Davis";
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		artist.birthDate = format.parse("26-05-1926");
		album.setArtist(artist);
		album.addMusician("Miles Davis", "Trumpet, Band leader");
		album.addMusician("Julian Adderley", "Alto Saxophone");
		album.addMusician("Paul Chambers", "double bass");

		ObjectMapper mapper = new ObjectMapper();
		// 使Map的按key顺序序列化
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		// 设置日期格式
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
		mapper.setDateFormat(outputFormat);

		// 回调方式修改生成json字符串中的属性名称
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy() {
			@Override
			public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
				if (field.getFullName().equals("com.dimit.bean.Artist#name"))
					return "Artist-Name";
				return super.nameForField(config, field, defaultName);
			}

			@Override
			public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
				if (method.getAnnotated().getDeclaringClass().equals(Album.class) && defaultName.equals("title"))
					return "Album-Title";
				return super.nameForGetterMethod(config, method, defaultName);
			}
		});
		// 忽略为null的值
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		StringWriter writer = new StringWriter();
		mapper.writeValue(writer, album);
		json = writer.toString();
	}

	/**
	 * dataBind方式将json数据转换为POJO对象
	 * 
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@Test
	public void dataBindjsonToObjectTest() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		Album album = mapper.readValue(json, Album.class);
		System.out.println(album);
	}

	
	@Test
	public void catTest() throws ParseException {
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		Cat cat = new Cat("hello kit", outputFormat.parse("2015-02-01 12:30:04"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 设置日期格式
		mapper.setDateFormat(outputFormat);
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, cat);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(writer.toString());
		
		try {
			Cat c = mapper.readValue(writer.toString(), Cat.class);
			System.out.println(c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

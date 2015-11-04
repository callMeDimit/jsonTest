package com.dimit;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dimit.bean.Horse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class Date2JsonTest {

	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String json;
	private static ObjectMapper mapper;

	private static Pattern pattern = Pattern.compile("^\\d{4}[-]\\d{2}[-]\\d{2} \\d{2}[:]\\d{2}[:]\\d{2}$");
	private static Pattern pattern1 = Pattern.compile("^\\d{4}[-]\\d{2}[-]\\d{2}$");

	@BeforeClass
	public static void beforeClz() {
		mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		JsonSerializer<Date> dateSerializable = new JsonSerializer<Date>() {
			@Override
			public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
					throws IOException, JsonProcessingException {
				String str = df.format(value);
				gen.writeString(str);
			}

		};
		JsonSerializer<LocalDate> localDateSerializable = new JsonSerializer<LocalDate>() {

			@Override
			public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
					throws IOException, JsonProcessingException {
				gen.writeString(value.toString());
			}
		};
		JsonDeserializer<Date> dateDeSerializable = new JsonDeserializer<Date>() {
			@Override
			public Date deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				String text = p.getValueAsString();
				if (pattern.matcher(text).matches()) {
					try {
						return df.parse(text);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				return null;
			}

		};
		JsonDeserializer<LocalDate> localdateDeSerializable = new JsonDeserializer<LocalDate>() {
			@Override
			public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
					throws IOException, JsonProcessingException {
				String text = p.getValueAsString();
				if (pattern1.matcher(text).matches()) {
					return LocalDate.parse(text);
				}
				return null;
			}

		};
		module.addSerializer(Date.class, dateSerializable);
		module.addSerializer(LocalDate.class, localDateSerializable);
		module.addDeserializer(Date.class, dateDeSerializable);
		module.addDeserializer(LocalDate.class, localdateDeSerializable);
		mapper.registerModule(module);

		StringWriter writer = new StringWriter();
		Horse horse = new Horse("h1");
		try {
			mapper.writeValue(writer, horse);
		} catch (IOException e) {
			e.printStackTrace();
		}
		json = writer.toString();
		System.out.println(json);
	}

	@Test
	public void Date2JsonTest1() {
		try {
			Horse horse = mapper.readValue(json, Horse.class);
			System.out.println(horse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

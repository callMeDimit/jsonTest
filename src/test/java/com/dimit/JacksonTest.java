package com.dimit;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.dimit.bean.Album;
import com.dimit.bean.Artist;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonTest {
	private static Album album;

	@BeforeClass
	public static void beforClz() throws ParseException {
		album = new Album("Kind Of Blue");
		album.setLinks(new String[] { "link1", "link2" });
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
	}

	@Test
	@SuppressWarnings("serial")
	public void serilizeTest() throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// 使生产的json字符串以格式化的方式输出(在开发中通最好不要打开这个设置项因为很消耗性能)
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
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
		mapper.writeValue(System.out, album);
	}

	/**
	 * dom树方式生成json
	 * 
	 * @throws IOException
	 */
	@Test
	public void treeModelTest() throws IOException {
		// Create the node factory that gives us nodes.
		JsonNodeFactory factory = new JsonNodeFactory(false);
		// create a json factory to write the treenode as json. for the example
		// we just write to console
		JsonFactory jsonFactory = new JsonFactory();
		JsonGenerator generator = jsonFactory.createGenerator(System.out);
		ObjectMapper mapper = new ObjectMapper();
		// the root node - album
		ObjectNode album = factory.objectNode();
		album.put("Album-Title", "Kind Of Blue");
		ArrayNode links = factory.arrayNode();
		links.add("link1").add("link2");
		album.set("links", links);

		ObjectNode musicians = factory.objectNode();
		musicians.put("Julian Adderley", "Alto Saxophone");
		musicians.put("Miles Davis", "Trumpet, Band leader");
		album.set("musicians", musicians);
		mapper.writeTree(generator, album);
	}

	/**
	 * stream方式序列化
	 * 
	 * @throws IOException
	 * @throws JsonParseException
	 */
	@Test
	public void streamingTest() throws JsonParseException, IOException {
		// Get a list of albums from free music archive. limit the results to 5
		String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
		// get an instance of the json parser from the json factory
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createParser(new URL(url));

		// continue parsing the token till the end of input is reached
		while (!parser.isClosed()) {
			// get the token
			JsonToken token = parser.nextToken();
			// if its the last token then we are done
			if (token == null)
				break;
			// we want to look for a field that says dataset

			if (JsonToken.FIELD_NAME.equals(token) && "dataset".equals(parser.getCurrentName())) {
				// we are entering the datasets now. The first token should be
				// start of array
				token = parser.nextToken();
				if (!JsonToken.START_ARRAY.equals(token)) {
					// bail out
					break;
				}
				// each element of the array is an album so the next token
				// should be {
				token = parser.nextToken();
				if (!JsonToken.START_OBJECT.equals(token)) {
					break;
				}
				// we are now looking for a field that says "album_title". We
				// continue looking till we find all such fields. This is
				// probably not a best way to parse this json, but this will
				// suffice for this example.
				while (true) {
					token = parser.nextToken();
					if (token == null)
						break;
					if (JsonToken.FIELD_NAME.equals(token) && "album_title".equals(parser.getCurrentName())) {
						token = parser.nextToken();
						System.out.println(parser.getText());
					}

				}

			}

		}

	}
	
	
}

package com.dimit;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import com.dimit.bean.AlbumsDynamic;
import com.dimit.bean.DatasetDynamic;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonSetterOrGetterTest {

	@Test
	public void test() {
		String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=2";
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		AlbumsDynamic albums = null;
		try {
			albums = mapper.readValue(new URL(url), AlbumsDynamic.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		DatasetDynamic[] datasets = albums.getDataset();
		for (DatasetDynamic dataset : datasets) {
			System.out.println(dataset.get("album_type"));

		}

	}

}

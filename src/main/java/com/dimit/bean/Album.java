package com.dimit.bean;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties({ "title", "links" })//序列化合反序列化时忽略指定属性名称
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE)
public class Album {
	// @JsonIgnore //序列化和反序列化时忽略指定属性
	private String title;
	private String[] links;
	private List<String> songs;
	private Artist artist;
	@JsonProperty("music")
	private Map<String, String> musicians = new HashMap<String, String>();

	/**
	 * 在进行反序列化时必须存在默认的构造方法
	 */
	public Album() {
	}

	public Album(String title) {
		this.title = title;
	}

	/**
	 * 增加music
	 * 
	 * @param key
	 * @param value
	 */
	public void addMusician(String key, String value) {
		musicians.put(key, value);
	}

	// getter and setter ...
	String getTitle() {
		return title;
	}

	public String[] getLinks() {
		return links;
	}

	public void setLinks(String[] links) {
		this.links = links;
	}

	public List<String> getSongs() {
		return songs;
	}

	public void setSongs(List<String> songs) {
		this.songs = songs;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Map<String, String> getMusicians() {
		return Collections.unmodifiableMap(musicians);
	}

	public void setMusicians(Map<String, String> musicians) {
		this.musicians = musicians;
	}

	@Override
	public String toString() {
		return "Album [title=" + title + ", links=" + Arrays.toString(links) + ", songs=" + songs + ", artist=" + artist
				+ ", musicians=" + musicians + "]";
	}
}
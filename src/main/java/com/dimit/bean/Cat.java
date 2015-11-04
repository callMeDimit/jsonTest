package com.dimit.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 注意:</br>
 *1、 @JsonAutoDetect使用fieldVisibility可以指定将哪些属性的值作为序列化內容.</br>
 * {@link Visibility#ANY}:表示全部都进行序列化 </br>
 * {@link Visibility#NONE}:表示全部都不进行序列化</br>
 * {@link Visibility#PUBLIC_ONLY}:表示仅由public修饰的属性才进行进行序列化</br>
 * {@link Visibility#PROTECTED_AND_PUBLIC}
 * :表示仅由public、protected修饰的属性才进行进行序列化</br>
 * {@link Visibility#NON_PRIVATE}:表示不是由private修饰的属性才进行进行序列化</br>
 * 2、
 * 在反序列化时如果未使用@JsonCreator指定构造函数则空构造函数必须存在</br>
 * 使用@JsonCreator时应配合使用@JsonProperty指定构造函数参数对应的json属性</br>
 * @author dimit
 *
 */
@JsonInclude(Include.NON_NULL) // 忽略值为空的字段
@JsonAutoDetect(getterVisibility = Visibility.PUBLIC_ONLY, fieldVisibility = Visibility.ANY)
public class Cat {
	/** 名称 */
	private String name;
	/** 生日 */
	private Date birthDate;

	/*
	 * public Cat() { }
	 */
	@JsonCreator
	public Cat(@JsonProperty("name") String name, @JsonProperty("birthDate") Date birthDate) {
		this.name = name;
		this.birthDate = birthDate;
	}

	public Cat(Date birthDate) {
		super();
		this.birthDate = birthDate;
	}

	/**
	 * 当方法中存在get方法而字段中不存在时可以设置
	 * 
	 * @JsonAutoDetect的getterVisibility的值来忽略這種get方法的值被序列化
	 */
	private int getAge() {
		return 1;
	}

	@Override
	public String toString() {
		return "Cat [name=" + name + ", birthDate=" + birthDate + "]";
	}
}

package com.dimit.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = As.PROPERTY, property = "@class")
public class Zoo {

	public String name;
	public String city;
	public List<Animal> animals = new ArrayList<Animal>();;

	@JsonCreator
	public Zoo(@JsonProperty("name") String name, @JsonProperty("city") String city) {
		this.name = name;
		this.city = city;
	}

	public void setAnimals(List animals) {
		this.animals = animals;
	}

	@Override
	public String toString() {
		return "Zoo [name=" + name + ", city=" + city + ", animals=" + animals + "]";
	}

	/**
	 * 增加animal
	 * 
	 * @param animal
	 */
	public Zoo add(Animal animal) {
		animals.add(animal);
		return this;
	}

}

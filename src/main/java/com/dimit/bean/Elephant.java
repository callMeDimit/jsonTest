package com.dimit.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(getterVisibility = Visibility.NONE)
public class Elephant extends Animal {

	@JsonProperty
	private String name;

	@JsonCreator
	public Elephant(@JsonProperty("name") String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getSound() {
		return "trumpet";
	}

	public String getType() {
		return "herbivorous";
	}

	public boolean isEndangered() {
		return false;
	}

	@Override
	public String toString() {
		return "Elephant [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()="
				+ getType() + ", isEndangered()=" + isEndangered() + "]";
	}

}
package com.dimit.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 这个类用于将json的注解与bird对象解耦。
 * @author dimit
 *
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE)
public abstract class BirdMixIn {
	BirdMixIn(@JsonProperty("name") String name) {
	};

	@JsonProperty("sound")
	abstract String getSound();

	@JsonProperty("habitat")
	abstract String getHabitat();
}
package com.dimit.bean;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Horse {
	private String name;
	private Date birthDate;
	private LocalDate now;

	@JsonCreator
	public Horse(@JsonProperty("name")String name) {
		this.name = name;
		this.birthDate = new Date();
		this.now = LocalDate.now();
	}

	@Override
	public String toString() {
		return "Horse [name=" + name + ", birthDate=" + birthDate + ", now=" + now + "]";
	}
}

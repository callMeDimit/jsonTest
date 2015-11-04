package com.dimit.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Artist {
	public String name;
	public Date birthDate;
	public int age;
	public String homeTown;
	public List<String> awardsWon = new ArrayList<String>();

	// getter and setter...
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	/*public List<String> getAwardsWon() {
		return awardsWon;
	}

	public void setAwardsWon(List<String> awardsWon) {
		this.awardsWon = awardsWon;
	}*/
}
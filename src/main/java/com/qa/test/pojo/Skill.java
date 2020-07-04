package com.qa.test.pojo;

public class Skill {

	private String name;
	private int experience;
	private int lastWorked;

	public Skill() {

	}

	public Skill(String name, int experience, int lastWorked) {
		this.name = name;
		this.experience = experience;
		this.lastWorked = lastWorked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getLastWorked() {
		return lastWorked;
	}

	public void setLastWorked(int lastWorked) {
		this.lastWorked = lastWorked;
	}

}

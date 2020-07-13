package com.test.ProgramPractice;

public class EmployeeData {
	private String name;
	private int salary;
	private int age;

	public EmployeeData() {
	}

	public EmployeeData(String name, int salary, int age) {
		this.name = name;
		this.salary = salary;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

package com.qa.pojo;

public class EmployeSkillDetails {
	private String name;
	private int empid;
	private boolean married;
	public EmployeSkillDetails() {

	}

	public EmployeSkillDetails(String name, int empid, boolean married) {
		this.name = name;
		this.empid = empid;
		this.married = married;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEmpid() {
		return empid;
	}

	public void setEmpid(int empid) {
		this.empid = empid;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}
}

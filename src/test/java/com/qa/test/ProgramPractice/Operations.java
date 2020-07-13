package com.qa.test.ProgramPractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.qa.pojo.EmployeSkillDetails;
import com.qa.pojo.Skill;

public class Operations {

	static void dataTypes() {
		byte var_byte = 100; // (number, 1 byte) -128 to 127
		System.out.println(var_byte);
		short var_short = 20000; // number, 2 bytes) -32,768 to 32,767
		System.out.println(var_short);
		int var_int = 10; // (number, 4 bytes) -2,147,483,648 to 2,147,483, 647
		System.out.println(var_int);
		long var_long = 922337234;// (number, 8 bytes) -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807
		System.out.println(var_long);
		float var_float = (float) 0.1; // (Float number, 4 bytes)- approximately ±3.40282347E+38F (6-7 significant
										// decimal digits) Java implements IEEE 754 standard
		System.out.println(var_float);
		double var_double = 4.67; // Double (Float number, 4 bytes)- approximately ±1.79769313486231570E+308 (15
									// significant decimal digits)
		System.out.println(var_double);
		char var_char = 'A'; // Char (a character, 2 bytes)- A-0 to 65,536 (unsigned)
		System.out.println(var_char);
		boolean var_bool = false; // Boolean (true or false, 1 byte)-true or false
		System.out.println(var_bool);
	}

	static void userInput() {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
			System.out.println("Enter length");
			int length = scan.nextInt();
			ArrayList<Integer> inputNumber = new ArrayList<Integer>();
			for (int i = 0; i < length; i++) {
				inputNumber.add(scan.nextInt());
			}
			System.out.println(inputNumber);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		scan.close();
	}

	static void incrementalOperator() {
		int postinc = 10;
		postinc++;
		System.out.println("postinc is " + postinc);
		int postinc1 = 10;
		System.out.println("postinc1++ is " + postinc1++);
		System.out.println("postinc1 is " + postinc1);
		int preinc = 10;
		System.out.println("++preinc is " + ++preinc);
		int plusassign = 10;
		plusassign += 5;
		System.out.println("plusassign is " + plusassign);
		int mulassign = 10;
		mulassign *= 5;
		System.out.println("mulassign is " + mulassign);
		int divassign = 10;
		divassign /= 10;
		System.out.println("divassign is " + divassign);
	}

	static void whileDo(int n) {
		while (n != 11) {
			System.out.println(n++);
		}
	}

	static void doWhile(int n) {
		do {
			n = n - 1;
			if (n % 2 == 0) {
				System.out.println(n);
			}
		} while (n != -1);
	}

	static void StringGame() {
		String value = null;
		value = "";
		value = "changed";
		StringBuffer st = new StringBuffer(value);
		st.append(value);
		StringBuilder sb = new StringBuilder(value);
		sb.append(st);
		sb.reverse();
		System.out.println(value);
		System.out.println(st);
		System.out.println(sb);
	}

	static void comparatorImplementations() {
		EmployeeData matru = new EmployeeData("Matru", 50000, 28);
		EmployeeData satya = new EmployeeData("Satya", 47000, 27);
		EmployeeData babu = new EmployeeData("Dash", 62000, 30);
		EmployeeData milan = new EmployeeData("Milan", 65000, 29);

		List<EmployeeData> ed = new ArrayList<EmployeeData>();
		ed.add(babu);
		ed.add(matru);
		ed.add(satya);
		ed.add(milan);
		System.out.println("===========UnSorted================");
		for (EmployeeData data : ed) {
			System.out.print(data.getName() + " ");
			System.out.print(data.getSalary() + " ");
			System.out.println(data.getAge());
		}
		Collections.sort(ed, new sortByName());
		System.out.println("===========Sorted Name================");
		for (EmployeeData data : ed) {
			System.out.print(data.getName() + " ");
			System.out.print(data.getSalary() + " ");
			System.out.println(data.getAge());
		}
		Collections.sort(ed, new sortBySalary());
		System.out.println("===========Sorted Salary================");
		for (EmployeeData data : ed) {
			System.out.print(data.getName() + " ");
			System.out.print(data.getSalary() + " ");
			System.out.println(data.getAge());
		}
		Collections.sort(ed, new sortByAge());
		Collections.reverse(ed);
		System.out.println("===========Sorted Age================");
		for (EmployeeData data : ed) {
			System.out.print(data.getName() + " ");
			System.out.print(data.getSalary() + " ");
			System.out.println(data.getAge());
		}
	}

	static void sortNumberBasedOnCustomizedComparator() {
		List<Integer> unsortedList = Arrays.asList(90, 58, 43, 67, 32, 98, 65);
		System.out.println(unsortedList);
		Collections.sort(unsortedList, new SortBySecondNumber());
	}
	
	@SuppressWarnings("unchecked")
	static void buildJSON() {
		Skill skill = new Skill();
		
		skill.setName("Java");
		skill.setExperience(5);
		skill.setLastWorked(2020);
		JSONObject skillArr1 = new JSONObject();
		
		skillArr1.put("name", skill.getName());
		skillArr1.put("experience",skill.getExperience());
		skillArr1.put("lastWorked", skill.getLastWorked());
		Skill skill1 = new Skill();
		
		skill1.setName("Selenium");
		skill1.setExperience(5);
		skill1.setLastWorked(2020);
		JSONObject skillArr2 = new JSONObject();
		
		skillArr2.put("name", skill1.getName());
		skillArr2.put("experience",skill1.getExperience());
		skillArr2.put("lastWorked", skill1.getLastWorked());
		JSONArray skillJsonCompleteArray = new JSONArray();
		
		skillJsonCompleteArray.add(skillArr1);
		skillJsonCompleteArray.add(skillArr2);
		JSONObject employeeObject = new JSONObject();
		
		employeeObject.put("Skill", skillJsonCompleteArray);
		EmployeSkillDetails esd = new EmployeSkillDetails();
		
		esd.setName("Dash");
		esd.setEmpid(4701);
		esd.setMarried(true);
		employeeObject.put("name", esd.getName());
		employeeObject.put("empid", esd.getEmpid());
		employeeObject.put("married", esd.isMarried());
		System.out.println(employeeObject);
	}
}

class sortByName implements Comparator<EmployeeData> {
	public int compare(EmployeeData o1, EmployeeData o2) {
		return o1.getName().compareTo(o1.getName());
	}
}

class sortBySalary implements Comparator<EmployeeData> {
	public int compare(EmployeeData o1, EmployeeData o2) {
		return o1.getSalary() - o2.getSalary();
	}
}

class sortByAge implements Comparator<EmployeeData> {
	public int compare(EmployeeData o1, EmployeeData o2) {
		return o1.getAge() - o2.getAge();
	}
}

class SortBySecondNumber implements Comparator<Integer> {
	public int compare(Integer o1, Integer o2) {
		return o1 - o2;
	}

}

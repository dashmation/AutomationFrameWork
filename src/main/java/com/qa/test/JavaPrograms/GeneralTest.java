package com.qa.test.JavaPrograms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GeneralTest {

	public static void toFindSubString(String input) {
		for (int i = 0; i < input.length(); i++) {
			for (int j = i + 1; j <= input.length(); j++) {
				System.out.println(input.substring(i, j));
			}
		}
	}

	public static void toFindSubStringWithoutUsingSubStringMethod(String input) {
		for (int i = 0; i < input.length(); i++) {
			StringBuilder subString = new StringBuilder().append(input.charAt(i));
			System.out.println(subString);
			for (int j = i + 1; j < input.length(); j++) {
				subString.append(input.charAt(j));
				System.out.println(subString);
			}
		}
	}

	public static void maximumOccurringCharacterInStringInJava(String input) {
		Map<Character, Integer> charVsCount = new HashMap<Character, Integer>();
		input = input.replace(" ", "");
		for (int i = 0; i < input.length(); i++) {
			int counter = 0;
			for (int j = 0; j < input.length(); j++) {
				if (input.charAt(i) == input.charAt(j)) {
					counter++;
				}
				charVsCount.put(input.charAt(i), counter);
			}
		}
		int maxCount = 0;
		char maxChar = 0;
		for (Entry<Character, Integer> entry : charVsCount.entrySet()) {
			if (entry.getValue() > maxCount) {
				maxChar = entry.getKey();
				maxCount = entry.getValue();
			}
		}
		
		System.out.println("Character is '"+maxChar+"' with Count as "+maxCount);
	}

	public static void main(String[] args) {
	}
}

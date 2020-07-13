package com.qa.JavaPrograms;

import java.util.Arrays;

public class CollectionsTest {
	
	public static void findSmallestAndSecondSmallest(int[] input) {
		Arrays.sort(input);
		int smallest=input[0];
		int second_Smallest=input[1];
		System.out.println("Smallest is "+smallest);
		System.out.println("Second Smallest is "+second_Smallest);
	}
	public static void main(String[] args) {
		int[] input = {17, 11, 23, 64, 41, 88, 35};
		findSmallestAndSecondSmallest(input);
	}
}

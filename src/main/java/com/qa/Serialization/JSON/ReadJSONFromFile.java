package com.qa.Serialization.JSON;


import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadJSONFromFile {
	
	public static void getJSONFromFile() {
	JSONParser jsonParser = new JSONParser();
	try {
		Object object = jsonParser.parse(new FileReader("D:\\Cucumber\\myTMO\\myTelBDDCucumber\\repos\\test.json"));
		JSONObject jsonObject = (JSONObject)object;
		JSONArray productList = (JSONArray)jsonObject.get("products");
		JSONArray locationsList = (JSONArray)jsonObject.get("locations");
		JSONArray familiesList = (JSONArray)jsonObject.get("families");
		JSONArray transactionsList = (JSONArray)jsonObject.get("transactions");
		System.out.println(productList.size());
		System.out.println(locationsList.size());
		System.out.println(familiesList.size());
		System.out.println(transactionsList.size());
		System.out.println(productList.get(0).toString());
		System.out.println(productList.get(1).toString());
		System.out.println(productList.get(2).toString());
		System.out.println(productList.get(3).toString());
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
		getJSONFromFile();
	}

}

package com.qa.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

public class CurlActions {
	public String curlGET(String url) {
		String response = "";
		String[] command = { "curl", "-i", "GET", "-H", "Accept:application/json", url };

		ProcessBuilder process = new ProcessBuilder(command);
		Process p;
		try {
			p = process.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			if (result.contains("{")) {
				response = result.substring(result.indexOf("{"));
			}
			System.out.println(response);

		} catch (IOException e) {
			System.out.println("Unable to execute curlGET due to " + e.getMessage());
		}
		return response;
	}

	public String curlPOST(String url, String request) {
		String response = "";
		String command = "curl -i -X POST " + url
				+ " -H 'cache-control: no-cache' -H 'Content-Type: application/json' -H 'postman-token: 6f9e7aa0-4c7a-40de-0cc0-34a8f8942b6d' -d"
				+ request;
		System.out.println(command);

		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			if (result.contains("{")) {
				response = result.substring(result.indexOf("{"));
			}
			System.out.println(response);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public String getValueFromResponse(String response, String key) {
		String value = "";
		try {
			System.out.println("Response to getValue is:" + response);
			JSONObject js = new JSONObject(response);
			value = js.get(key).toString();
		} catch (JSONException e) {
			System.out.println("Unable to execute getValueFromResponse due to " + e.getMessage());
			value = "notfound";
		}
		return value;
	}

	public String blindCurl(String[] command) {
		String response = "";
		ProcessBuilder process = new ProcessBuilder(command);
		Process p;
		try {
			p = process.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			if (result.contains("{")) {
				response = result.substring(result.indexOf("{"));
			}
			System.out.println(response);

		} catch (IOException e) {
			System.out.println("Unable to execute curlGET due to " + e.getMessage());
		}
		return response;
	}
}

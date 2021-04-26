package com.qa.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;
import org.testng.Reporter;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PerformOperations {
	public static RequestSpecification requestSpecification;

	public String id;
	public String reg;
	public String inuse;
	public String msisdn;
	public String email;
	public String fa;
	public String npah;
	public String multidev;

	/**
	 * @return the multidev
	 */
	public String getMultidev() {
		return multidev;
	}

	/**
	 * @param multidev the multidev to set
	 */
	public void setMultidev(String multidev) {
		this.multidev = multidev;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the reg
	 */
	public String getReg() {
		return reg;
	}

	/**
	 * @param reg the reg to set
	 */
	public void setReg(String reg) {
		this.reg = reg;
	}

	/**
	 * @return the inuse
	 */
	public String getInuse() {
		return inuse;
	}

	public enum SETInUSE {
		T, F;
	}

	/**
	 * @param inuse the inuse to set
	 */
	public void setInuse(String inuse) {
		this.inuse = inuse;
	}

	public void setInuse(SETInUSE SETInUSE) {
		switch (SETInUSE) {
		case T:
			updateInUseFlag("true", getId());
			break;
		case F:
			updateInUseFlag("false", getId());
			break;
		default:
			break;
		}
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fa
	 */
	public String getFa() {
		return fa;
	}

	/**
	 * @param fa the fa to set
	 */
	public void setFa(String fa) {
		this.fa = fa;
	}

	/**
	 * @return the npah
	 */
	public String getNpah() {
		return npah;
	}

	/**
	 * @param npah the npah to set
	 */
	public void setNpah(String npah) {
		this.npah = npah;
	}

	public PerformOperations() {
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		requestSpecBuilder.setBaseUri("http://localhost:3000/testbase");
		requestSpecBuilder.setContentType(ContentType.JSON);
		requestSpecBuilder.addHeader("Content-Type", "application/json");
		requestSpecification = requestSpecBuilder.build();
		requestSpecification = RestAssured.given().spec(requestSpecification);
	}

	@SuppressWarnings("unchecked")
	public Response updateWithQueryAndBodyParams(String key, String value, String id) {
		Response response = null;
		try {
			HashMap<String, String> putContent = new HashMap<String, String>();
			putContent.put(key, value);
			JSONObject json = new JSONObject();
			json.putAll(putContent);
			requestSpecification.with().body(json.toJSONString());
			response = requestSpecification.patch("/" + id);
			System.out.println("==============Inuse=============");
			response.prettyPrint();
		} catch (Exception e) {
//			Reporter.log("Unable to updateWithQueryAndBodyParams due to " + e.getMessage(), true);
		}
		return response;
	}

	private void updateInUseFlag(String value, String id) {
		try {
			updateWithQueryAndBodyParams("inuse", value, id);
		} catch (Exception e) {
			updateInUseFlag(value, id);
			Reporter.log("Unable to create updateInUseFlag due to " + e.getMessage(), true);
		}
	}

	public String getQueryParamsSet(List<String> input) {
		String finalInput = "";
		int range = input.size() - 1;
		for (int i = range; i >= 0; i--) {
			finalInput = input.get(i) + "&" + finalInput;
		}
		return finalInput.trim();
	}

	public void main(List<String> args) {
		String queryParamValue = "inuse=false&2fa=true&npah=false&multidev=true";
		List<String> queryParams = new LinkedList<String>(Arrays.asList(queryParamValue.split("&")));
		getQueryParamsSet(queryParams);
	}

	private List<String> getJSONValueFrom(Response response, String param) {
		List<String> jsonObj = response.path(param);
		return jsonObj;
	}

	private int getIndex(int size) {
		int value = 0;
		if (size > 1) {
			Random rand = new Random();
			value = rand.nextInt(size);
		}
		return value;
	}

	void resetDataTo(List<String> params) {
//		for (String eachParam : params) {
//			String expected = eachParam.replaceAll("&", "").toLowerCase();
//			switch (expected) {
//			case "2fa=true":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "2fa=false":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "npah=true":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "npah=false":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "2fa=no":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "npah=no":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "reg=false":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			case "reg=true":
//				Reporter.log(getMsisdn() + " ResetTestData to " + expected, true);
//				Reporter.log(getMsisdn() + "'s DataServer JSON updated to " + expected, true);
//				break;
//			default:
//				Reporter.log("No Method Found for " + expected, true);
//				break;
//			}
//		}
	}

	public void setValues(Response response) {
		int index = getIndex(getJSONValueFrom(response, "id").size());
		setId(getJSONValueFrom(response, "id").get(index));
		setInuse(SETInUSE.T);
		setInuse(getJSONValueFrom(response, "inuse").get(index));
		setReg(getJSONValueFrom(response, "reg").get(index));
		setMsisdn(getJSONValueFrom(response, "msisdn").get(index));
		setEmail(getJSONValueFrom(response, "email").get(index));
		setFa(getJSONValueFrom(response, "2fa").get(index));
		setNpah(getJSONValueFrom(response, "npah").get(index));
		setMultidev(getJSONValueFrom(response, "multidev").get(index));
	}

	public Response performGetOperation(String queryParamValue) {
		Response response = RestAssured.given().header("Content-Type", "application/json").get("http://localhost:3000/testbase?" + queryParamValue);
				
		return response;
	}

	public void getOperation(String queryParamValue) {
		Response response = null;
		int n = 3;
		try {
			List<String> removedParams = new ArrayList<String>();
			List<String> queryParams = new LinkedList<String>(Arrays.asList(queryParamValue.split("&")));
			response = performGetOperation(queryParamValue);
			response.prettyPrint();
			if (!response.path("id").toString().equals("[]")) {
				setValues(response);
			} else {
				for (int retry = 1; retry < n; retry++) {
					Reporter.log("Retyring for:" + retry, true);
					int range = queryParams.size();
					removedParams.add(queryParams.get(range - 1));
					queryParams.remove(range - 1);
					System.out.println(getQueryParamsSet(queryParams)); // 1234-123-12
					response = performGetOperation(getQueryParamsSet(queryParams));
					response.prettyPrint();
					if (!response.path("id").toString().equals("[]")) {
						setValues(response);
						resetDataTo(removedParams);
						Reporter.log("Data Indent Suggested: " + getQueryParamsSet(removedParams), true);
						break;
					}
				}
			}
		} catch (Exception e) {
			Reporter.log("Unable to getOperation due to " + e.getMessage(), true);
			e.printStackTrace();
		}
	}

	public void getDataFromServer(String filter) {
		getOperation(filter);
	}
}
package com.qa.api;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Reporter;


public class HTTPTestClient {


	public static HttpResponse GETCall(String URL) throws IOException {

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(URL);

		getRequest.addHeader("accept","application/json");

		HttpResponse response = httpClient.execute(getRequest);

		if(response.getStatusLine().getStatusCode() == 200){
			Reporter.log("GET - API Test Status code is 200",true);
		}

		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
		JSONObject jsonObject = new JSONObject(json);
		Reporter.log(jsonObject.toString(),true);
		return response;

	}

	private HttpResponse response;

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	private String raw_Response;

	public String getRaw_Response() {
		return raw_Response;
	}

	public void setRaw_Response(String raw_Response) {
		this.raw_Response = raw_Response;
	}

	public HttpResponse GET(String URL) throws IOException {

		HttpResponse response = null;
		try {
			@SuppressWarnings({ "resource" })
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(URL);

			getRequest.addHeader("accept", "application/json");

			response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("GET - API Test Status code is 200");
			}

			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			JSONObject jsonObject = new JSONObject(json);
			System.out.println(jsonObject.toString());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;

	}

	public CloseableHttpResponse POST(String URI, String Request) {
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient client = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(URI);
			StringEntity entity;
			entity = new StringEntity(Request);
			httpPost.setEntity(entity);
			httpPost.setHeader("Content-type", "application/json");
			response = client.execute(httpPost);
			System.out.println(response.getStatusLine());
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public enum CONTENT_TYPE {
		JSON, TEXT;
	}

	public HttpResponse GET(String URL, CONTENT_TYPE CONTENT_TYPE) throws IOException {

		HttpResponse response = null;
		try {
			@SuppressWarnings({"resource"})
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(URL);

			switch (CONTENT_TYPE) {
				case JSON:
					getRequest.addHeader("content-type", "application/json");
					break;
				case TEXT:
					getRequest.addHeader("content-type", "text/tab-separated-values");
					break;

				default:
					break;
			}

			response = httpClient.execute(getRequest);
			System.out.println(response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				System.out.println("GET - API Test Status code is 200");
			}
			setResponse(response);
			HttpEntity entity = response.getEntity();
			String output = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			setRaw_Response(output);

			switch (CONTENT_TYPE) {
				case JSON:
					JSONObject jsonObject = new JSONObject(getRaw_Response());
					System.out.println(jsonObject.toString());
					break;
				case TEXT:
					System.out.println(getRaw_Response());
					break;
				default:
					break;
			}

		} catch (

				ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
}

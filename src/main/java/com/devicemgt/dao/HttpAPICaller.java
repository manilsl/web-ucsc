package com.devicemgt.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpAPICaller {

	public String getRequest(String restURL) {
		String strResponse = "";
		try {

			HttpClient client = new DefaultHttpClient();
			HttpGet httpRequest = new HttpGet(restURL);
			HttpResponse httpResponse = client.execute(httpRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			// String line = "";
			strResponse = rd.readLine();
			System.out.println("Get Req :"+strResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strResponse;
	}

	@SuppressWarnings("finally")
	public String postRequest(String restURL, String payloadBody) {

		String strResponse = "";

		try {

			HttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(restURL);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setEntity(new StringEntity(payloadBody));
			HttpResponse httpResponse = client.execute(httpPost);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			strResponse = rd.readLine();
			System.out.println("Post Req :"+strResponse);
			

		} catch (Exception e) {
			e.printStackTrace();
			strResponse="Error";
		} finally {
			return strResponse;
		}

	}
	
	
	@SuppressWarnings("finally")
	public String putRequest(String restURL, String payloadBody) {

		String strResponse = "";

		try {

			HttpClient client = new DefaultHttpClient();
//			HttpPost httpPost = new HttpPost(restURL);
			HttpPut httpPut=new HttpPut(restURL);
			httpPut.setHeader("Content-Type", "application/json");
			httpPut.setEntity(new StringEntity(payloadBody));
			HttpResponse httpResponse = client.execute(httpPut);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			strResponse = rd.readLine();
			System.out.println("Put Req :"+strResponse);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return strResponse;
		}

	}
	
	
	public String deleteRequest(String strURL){
		String strResponse = "";
		try {

			HttpClient client = new DefaultHttpClient();
			HttpDelete httpRequest = new HttpDelete(strURL);
			HttpResponse httpResponse = client.execute(httpRequest);
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));

			// String line = "";
			strResponse = rd.readLine();
			System.out.println("Delete Req :"+strResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return strResponse;
	}

}

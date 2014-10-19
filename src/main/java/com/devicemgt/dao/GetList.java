package com.devicemgt.dao;

import java.util.LinkedList;

import org.json.JSONObject;

import com.google.gson.Gson;

public class GetList {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static LinkedList getList(String jsonBody , String rootElement , String className)
	{
		

		
		LinkedList templist = new LinkedList();
		
		try {

			JSONObject jsonObject = null;

			if (isValidJSON(jsonBody)) {

				jsonObject = new JSONObject(jsonBody);
			} else {
				jsonObject = new JSONObject();
				jsonObject.put(rootElement, jsonBody);

			}

		//	System.out.println(jsonObject.toString());
			for (int x = 0; x < jsonObject.getJSONArray(rootElement).length(); x++) {

				JSONObject jObject = new JSONObject();
	
				jObject.put(rootElement, jsonObject.getJSONArray(rootElement).get(x));
	//			System.out.println("2.3");
			//System.out.println(jObject.toString());
		//		System.out.println("3");
				 String theType = "com.devicemgt.model." + className;
		//		 System.out.println("4");
				 Class<?> theClass = Class.forName(theType);
		//		 System.out.println("5");
				
				Gson converter = new Gson();
				Object tempObject = converter.fromJson(jsonObject.getJSONArray(rootElement).get(x).toString(), theClass);
			//	System.out.println("6");
				templist.add(tempObject);
			}
			
			return templist;


		} catch (Exception e) {
			System.out.println(e.toString() + " getList");
			return templist;

		}

	}
	
	public static boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			return true;
		} catch (Exception ex) {
			System.out.println("Invalid json");
			return false;
		}
	}

}

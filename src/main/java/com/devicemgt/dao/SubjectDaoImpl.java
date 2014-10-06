package com.devicemgt.dao;

import java.util.LinkedList;
import com.devicemgt.model.Subject;
import com.google.gson.Gson;

public class SubjectDaoImpl implements SubjectDao{
	
	HttpAPICaller httpAPICaller;

	
	


	public String deleteSubject(String restURL) throws Exception {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}



	public LinkedList<Subject> getSubjectList(String jsonBody, String rootElement) {
		
		try{
		@SuppressWarnings("unchecked")
		LinkedList<Subject> subjectList = GetList.getList(jsonBody, rootElement,  rootElement);
		return subjectList;
		}
		catch(Exception e)
		{System.out.println(e.toString() + "  getSubjectList"); return null;}

	}


	public String addSubject(Subject subject, String restURL) {
		
		try
		{
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(subject);		
		
		httpAPICaller = new HttpAPICaller();
		
		System.out.println("restURL " + restURL);
		System.out.println("jsonString " + jsonString);
		jsonString = "{ \"Subject\" : " + jsonString + "}";
		
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;
		}
		catch(Exception e)
		{System.out.println(e.toString() + " addSubject"); return  null;}
	}


	public String updateSubject(Subject subject,  String restURL) {
		
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(subject);		
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;

	}





	

}

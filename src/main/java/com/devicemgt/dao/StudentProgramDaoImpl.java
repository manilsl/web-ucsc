package com.devicemgt.dao;

import java.util.LinkedList;
import com.devicemgt.model.StudentProgram;
import com.google.gson.Gson;

public class StudentProgramDaoImpl implements StudentProgramDao{
	
	HttpAPICaller httpAPICaller;

	
	


	public String deleteStudentProgram(String restURL) throws Exception {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}



	public LinkedList<StudentProgram> getStudentProgramList(String jsonBody, String rootElement) {
		
		try{
		@SuppressWarnings("unchecked")
		LinkedList<StudentProgram> studentProgramList = GetList.getList(jsonBody, rootElement,  rootElement);
		return studentProgramList;
		}
		catch(Exception e)
		{System.out.println(e.toString() + "  getStudentProgramList"); return null;}

	}


	public String addStudentProgram(StudentProgram studentProgram, String restURL) {
		
		try
		{
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(studentProgram);		
		
		httpAPICaller = new HttpAPICaller();
		
		System.out.println("restURL " + restURL);
		System.out.println("jsonString " + jsonString);
		jsonString = "{ \"StudentProgram\" : " + jsonString + "}";
		
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;
		}
		catch(Exception e)
		{System.out.println(e.toString() + " addStudentProgram"); return  null;}
	}


	public String updateStudentProgram(StudentProgram studentProgram,  String restURL) {
		
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(studentProgram);		
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;

	}





	

}

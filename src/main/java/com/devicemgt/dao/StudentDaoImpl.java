package com.devicemgt.dao;

import java.util.LinkedList;
import com.devicemgt.model.Student;
import com.google.gson.Gson;

public class StudentDaoImpl implements StudentDao{
	
	HttpAPICaller httpAPICaller;

	
	


	public String deleteStudent(String restURL) throws Exception {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}



	public LinkedList<Student> getStudentList(String jsonBody, String rootElement) {
		
		try{
		@SuppressWarnings("unchecked")
		LinkedList<Student> studentList = GetList.getList(jsonBody, rootElement,  rootElement);
		return studentList;
		}
		catch(Exception e)
		{System.out.println(e.toString() + "  getStudentList"); return null;}

	}


	public String addStudent(Student student, String restURL) {
		
		try
		{
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(student);		
		
		httpAPICaller = new HttpAPICaller();
		
		System.out.println("restURL " + restURL);
		System.out.println("jsonString " + jsonString);
		jsonString = "{ \"Student\" : " + jsonString + "}";
		
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;
		}
		catch(Exception e)
		{System.out.println(e.toString() + " addStudent"); return  null;}
	}


	public String updateStudent(Student student,  String restURL) {
		
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(student);		
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;

	}





	

}

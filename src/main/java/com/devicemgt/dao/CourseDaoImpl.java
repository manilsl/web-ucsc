package com.devicemgt.dao;

import java.util.LinkedList;
import com.devicemgt.model.Course;
import com.google.gson.Gson;

public class CourseDaoImpl implements CourseDao{
	
	HttpAPICaller httpAPICaller;

	
	/*
	
	
	@SuppressWarnings({ "unchecked" })
	public static void main(String[] args) {


		Course  course =  new Course();
		course.setCourseID("21312");
		course.setCourseName("afdf");
		//{"Course":{"courseID":"000002","courseName":"Degree"}}
		//{"courseID":"21312","courseName":"afdf"}
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(course);	
		
		System.out.println(jsonString);
		
		String jsonBody = "{       \"Course\":       [           {               \"courseID\":\"000001\",               \"courseName\": \"Masters\"           },           {               \"courseID\": \"000002\",               \"courseName\": \"Degree\"           }       ]    }"
;
		System.out.println(jsonBody);
		String rootElement = "Course";
		
		LinkedList<Course> courseList = GetList.getList(jsonBody, rootElement,  "Course");

		
		System.out.println(courseList.get(0).getCourseID() + courseList.get(0).getCourseName() );
		System.out.println(courseList.get(1).getCourseID() + courseList.get(1).getCourseName() );
		
		
	}*/

	


	public String deleteCourse(String restURL) throws Exception {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}



	public LinkedList<Course> getCourseList(String jsonBody, String rootElement) {
		
		try{
		@SuppressWarnings("unchecked")
		LinkedList<Course> courseList = GetList.getList(jsonBody, rootElement,  rootElement);
		return courseList;
		}
		catch(Exception e)
		{System.out.println(e.toString() + "  getCourseList"); return null;}

	}


	public String addCourse(Course course, String restURL) {
		
		try
		{
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(course);		
		
		httpAPICaller = new HttpAPICaller();
		
		System.out.println("restURL " + restURL);
		System.out.println("jsonString " + jsonString);
		jsonString = "{ \"Course\" : " + jsonString + "}";
		
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;
		}
		catch(Exception e)
		{System.out.println(e.toString() + " addCourse"); return  null;}
	}


	public String updateCourse(Course course,  String restURL) {
		
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(course);		
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;

	}
	
	

}

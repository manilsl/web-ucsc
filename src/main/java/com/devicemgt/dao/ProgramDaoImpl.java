package com.devicemgt.dao;

import java.util.LinkedList;
import com.devicemgt.model.Program;
import com.google.gson.Gson;

public class ProgramDaoImpl implements ProgramDao{
	
	HttpAPICaller httpAPICaller;

	
	


	public String deleteProgram(String restURL) throws Exception {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}



	public LinkedList<Program> getProgramList(String jsonBody, String rootElement) {
		
		try{
		@SuppressWarnings("unchecked")
		LinkedList<Program> programList = GetList.getList(jsonBody, rootElement,  rootElement);
		return programList;
		}
		catch(Exception e)
		{System.out.println(e.toString() + "  getProgramList"); return null;}

	}


	public String addProgram(Program program, String restURL) {
		
		try
		{
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(program);		
		
		httpAPICaller = new HttpAPICaller();
		
		System.out.println("restURL " + restURL);
		System.out.println("jsonString " + jsonString);
		jsonString = "{ \"Program\" : " + jsonString + "}";
		
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;
		}
		catch(Exception e)
		{System.out.println(e.toString() + " addProgram"); return  null;}
	}


	public String updateProgram(Program program,  String restURL) {
		
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(program);		
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;

	}





	

}

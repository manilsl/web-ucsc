package com.devicemgt.dao;

import java.util.LinkedList;
import com.devicemgt.model.Schedule;
import com.google.gson.Gson;

public class ScheduleDaoImpl implements ScheduleDao{
	
	HttpAPICaller httpAPICaller;

	
	


	public String deleteSchedule(String restURL) throws Exception {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}



	public LinkedList<Schedule> getScheduleList(String jsonBody, String rootElement) {
		
		try{
		@SuppressWarnings("unchecked")
		LinkedList<Schedule> scheduleList = GetList.getList(jsonBody, rootElement,  rootElement);
		return scheduleList;
		}
		catch(Exception e)
		{System.out.println(e.toString() + "  getScheduleList"); return null;}

	}


	public String addSchedule(Schedule schedule, String restURL) {
		
		try
		{
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(schedule);		
		
		httpAPICaller = new HttpAPICaller();
		
		System.out.println("restURL " + restURL);
		System.out.println("jsonString " + jsonString);
		jsonString = "{ \"Schedule\" : " + jsonString + "}";
		
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;
		}
		catch(Exception e)
		{System.out.println(e.toString() + " addSchedule"); return  null;}
	}


	public String updateSchedule(Schedule schedule,  String restURL) {
		
		Gson converter = new Gson() ;
		String jsonString = converter.toJson(schedule);		
		
		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.postRequest(restURL,jsonString);

		return strResponse;

	}





	

}

package com.devicemgt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.dao.StudentProgramDao;
import com.devicemgt.dao.StudentProgramDaoImpl;
import com.devicemgt.model.StudentProgram;
import com.devicemgt.model.StudentProgramDetail;
import com.devicemgt.util.BackendConstants;
import com.google.gson.Gson;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpAPICaller httpAPICaller;
	StudentProgram studentProgram;
	StudentProgramDao studentProgramDao;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		try {
			String term = request.getParameter("term");
			System.out.println("Data from ajax call " + term);

			String  para = "Masters%202013%20Batch%201";
			LinkedList<String> list = getSelectList(request,response,para);

			System.out.println("list " + list.get(0).toString());
			String searchList = new Gson().toJson(list);
			
			System.out.println("searchList " + searchList);
			
			response.getWriter().write(searchList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public LinkedList<String> getSelectList(HttpServletRequest request, HttpServletResponse response , String program) {

		LinkedList<StudentProgramDetail> studentProgramList = new LinkedList<StudentProgramDetail>();
		LinkedList<String> selectList = new LinkedList<String>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/studentprogram/getstudentprogramdetail?programName="+program;
			httpAPICaller = new HttpAPICaller();
			strURL.replace(" ", "%20");
			String line = httpAPICaller.getRequest(strURL);

			studentProgramDao = new StudentProgramDaoImpl();
			studentProgramList = studentProgramDao.getStudentProgramDetail(line, "StudentProgramDetail");
			
			for (int i = 0; i < studentProgramList.size(); i++) {
				
				selectList.add(studentProgramList.get(i).getSubjectName());
			}
			
			return selectList;
		} catch (Exception e) {
			System.out.println(e.toString() + " getSelectList");
			return selectList;
		} 

	}
}

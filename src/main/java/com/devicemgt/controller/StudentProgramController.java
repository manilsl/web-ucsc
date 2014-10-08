package com.devicemgt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.devicemgt.dao.StudentProgramDao;
import com.devicemgt.dao.StudentProgramDaoImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.StudentProgram;
import com.devicemgt.model.StudentProgramDetail;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;


@WebServlet("/StudentProgramController")
public class StudentProgramController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	StudentProgram studentProgram;
	StudentProgramDao studentProgramDao;
	HttpAPICaller httpAPICaller;
	boolean isValidated = true;
	String strName;
	String strDisplayID;
	String strStatus;
	String strType;
	String strOwner;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StudentProgramController() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("Hello from Servlet\n\n");

		String actionType = (String) request.getSession(false).getAttribute("actionType");
		System.out.println("actionType " + actionType);
		RequestDispatcher requestDispatcher = null;

		if (request.getParameter("getStudentProgram") != null) {

			actionType = "getStudentProgram";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteStudentProgram";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editStudentProgram";
		} else if (request.getParameter("updateButton") != null) {
			actionType = "updateStudentProgram";
		}

		if ((request.getParameter("addStudentProgram") != null)
				|| (actionType.equals("updateStudentProgram"))) {


			strName = request.getParameter("studentProgramName");
			strDisplayID = request.getParameter("studentProgramID");
			//strDescription = request.getParameter("description");



		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type1 : " + actionType);

			if (actionType.equals("getStudentProgram")) {

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);
				requestDispatcher = request.getRequestDispatcher("getstudentProgram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getStudentProgramDetail")) {

				LinkedList<StudentProgramDetail> studentProgramList = getStudentProgramDetail(request,response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramListDet", studentProgramList);
				requestDispatcher = request.getRequestDispatcher("getstudentProgram.jsp");
				requestDispatcher.forward(request, response);

			}else if (actionType.equals("getSearch")) {

				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;

				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?studentProgramName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&studentProgramName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/studentprogram/getstudentprogramdetail";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				studentProgramDao = new StudentProgramDaoImpl();
				LinkedList<StudentProgram> studentProgramList = studentProgramDao.getStudentProgramList(
						line, "StudentProgram");

				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request
						.getRequestDispatcher("getstudentProgram.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addStudentProgram")) {

				studentProgram = new StudentProgram();
				/*studentProgram.setStudentProgramName(strName);
				studentProgram.setStudentProgramDisplayID(strDisplayID);*/


				String restURL = BackendConstants.SERVICEURL +"/studentprogram/addstudentprogram";
				System.out.println("restURL add " + restURL);
				studentProgramDao = new StudentProgramDaoImpl();
				strResponse = studentProgramDao.addStudentProgram(studentProgram, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_marks_lecturer_enter.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getStudentProgramOnLoad")) {

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_studentProgram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteStudentProgram")) {

				String strDltRadio = request.getParameter("deleteStudentProgram");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/studentprogram/deletestudentprogram/"
						+ strDltRadio;

				studentProgramDao = new StudentProgramDaoImpl();
				try {
					strResponse = studentProgramDao.deleteStudentProgram(restURL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_studentProgram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editStudentProgram")) {

				String strBtnEdit = request.getParameter("editStudentProgram");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/studentprogram/getstudentprogram?studentProgramId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				studentProgramDao = new StudentProgramDaoImpl();
				LinkedList<StudentProgram> studentProgramList = studentProgramDao.getStudentProgramList(
						line, "StudentProgram");

				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request
						.getRequestDispatcher("edit_studentProgram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateStudentProgram")) {
				
				String strLength =  (String) request.getSession(false).getAttribute("updateLength");
				System.out.println("strLength " + strLength);
				
				int intLength = Integer.parseInt(strLength);
				System.out.println("intLength " + intLength);
				
				studentProgramDao = new StudentProgramDaoImpl();
				for (int i = 0; i < intLength; i++) {
					String check = request.getParameter("isEdit"+i);
					System.out.println(check);
					if(check.equals("1"))
					{
						
						System.out.println("Begin Update"  + i);
						String studentID = request.getParameter("studentID"+i);
						String programID = request.getParameter("programID"+i);
						String subjectID = request.getParameter("subjectID"+i);
						String lecturerMark = request.getParameter("lecturerMark"+i);
						System.out.println(lecturerMark + " is for lecturerMark " + i);
						StudentProgram studentProgram = new StudentProgram();
						studentProgram.setStudentID(studentID);
						studentProgram.setFinalMark(lecturerMark);
						studentProgram.setProgramID(programID);
						studentProgram.setSubjectID(subjectID);
						String restURL = BackendConstants.SERVICEURL +"/studentprogram/updatestudentprogram?studentID="
								+ studentID + "&programID=" + programID + "&subjectID=" + subjectID;
						System.out.println("Before Call " + i);
						strResponse = studentProgramDao.updateStudentProgram(studentProgram, restURL);
						System.out.println("after Call");
						
						
					}
					else 
					{System.out.println("not equal");}
					
				}
				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request.getRequestDispatcher("updateordelete_studentProgram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadStudentProgram")) {

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request
						.getRequestDispatcher("get_studentProgram.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_studentProgram.jsp");
			requestDispatcher.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

	@SuppressWarnings("finally")
	public LinkedList<StudentProgram> getStudentProgramFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<StudentProgram> studentProgramList = new LinkedList<StudentProgram>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/studentprogram/getstudentprogram";
			httpAPICaller = new HttpAPICaller();
			
			//System.out.println(strURL + " strURL");
			String line = httpAPICaller.getRequest(strURL);

		//	System.out.println(line + " line");
			studentProgramDao = new StudentProgramDaoImpl();
			studentProgramList = studentProgramDao.getStudentProgramList(line, "StudentProgram");

		} catch (Exception e) {
			System.out.println(e.toString() + " getStudentProgramFromDB");
		} finally {
			return studentProgramList;
		}

	}
	
	
	@SuppressWarnings("finally")
	public LinkedList<StudentProgramDetail> getStudentProgramDetail(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<StudentProgramDetail> studentProgramList = new LinkedList<StudentProgramDetail>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/studentprogram/getstudentprogramdetail";
			httpAPICaller = new HttpAPICaller();
			
			String line = httpAPICaller.getRequest(strURL);

			studentProgramDao = new StudentProgramDaoImpl();
			studentProgramList = studentProgramDao.getStudentProgramDetail(line, "StudentProgram");

		} catch (Exception e) {
			System.out.println(e.toString() + " getStudentProgramDetail");
		} finally {
			return studentProgramList;
		}

	}

}
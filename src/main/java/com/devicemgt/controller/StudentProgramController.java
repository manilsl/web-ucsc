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
		} else if (request.getParameter("updateBtn") != null) {
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

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getStudentProgram")) {

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);
				requestDispatcher = request.getRequestDispatcher("getstudentProgram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {


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

				String restURL = BackendConstants.SERVICEURL +"/studentProgram/getstudentProgramsdetail";

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


				String restURL = BackendConstants.SERVICEURL +"/studentProgram/addstudentProgram";
				System.out.println("restURL add " + restURL);
				studentProgramDao = new StudentProgramDaoImpl();
				strResponse = studentProgramDao.addStudentProgram(studentProgram, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_studentProgram.jsp");
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

				String restURL = BackendConstants.SERVICEURL +"/studentProgram/deletestudentProgram/"
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

				String restURL = BackendConstants.SERVICEURL +"/studentProgram/getstudentPrograms?studentProgramId="
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

				String strID = request.getParameter("dID");
				System.out.println(strID);

				studentProgram = new StudentProgram();
	/*			studentProgram.setName(strName);
				studentProgram.setDescription(strDescription);
				studentProgram.setStatusId(Integer.parseInt(strStatus));
				studentProgram.setTypeId(Integer.parseInt(strType));
*/
				System.out.println(studentProgram);

				String restURL = BackendConstants.SERVICEURL +"/studentProgram/updatestudentProgram/"
						+ strID;

				studentProgramDao = new StudentProgramDaoImpl();
				strResponse = studentProgramDao.updateStudentProgram(studentProgram, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<StudentProgram> studentProgramList = getStudentProgramFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentProgramList", studentProgramList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_studentProgram.jsp");
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

			String strURL = BackendConstants.SERVICEURL +"/studentProgram/getstudentProgram";
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

}
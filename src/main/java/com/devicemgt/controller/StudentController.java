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

import com.devicemgt.dao.StudentDao;
import com.devicemgt.dao.StudentDaoImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.Student;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;


@WebServlet("/StudentController")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Student student;
	StudentDao studentDao;
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
	public StudentController() {
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

		if (request.getParameter("getStudent") != null) {

			actionType = "getStudent";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteStudent";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editStudent";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateStudent";
		}

		if ((request.getParameter("addStudent") != null)
				|| (actionType.equals("updateStudent"))) {


			strName = request.getParameter("studentName");
			strDisplayID = request.getParameter("studentID");
			//strDescription = request.getParameter("description");



		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getStudent")) {

				LinkedList<Student> studentList = getStudentFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);
				requestDispatcher = request.getRequestDispatcher("getstudent.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {


				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;

				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?studentName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&studentName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/student/getstudentsdetail";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				studentDao = new StudentDaoImpl();
				LinkedList<Student> studentList = studentDao.getStudentList(
						line, "Student");

				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);

				requestDispatcher = request
						.getRequestDispatcher("getstudent.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addStudent")) {

				student = new Student();
				/*student.setStudentName(strName);
				student.setStudentDisplayID(strDisplayID);*/


				String restURL = BackendConstants.SERVICEURL +"/student/addstudent";
				System.out.println("restURL add " + restURL);
				studentDao = new StudentDaoImpl();
				strResponse = studentDao.addStudent(student, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_student.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getStudentOnLoad")) {

				LinkedList<Student> studentList = getStudentFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_student.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteStudent")) {

				String strDltRadio = request.getParameter("deleteStudent");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/student/deletestudent/"
						+ strDltRadio;

				studentDao = new StudentDaoImpl();
				try {
					strResponse = studentDao.deleteStudent(restURL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LinkedList<Student> studentList = getStudentFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_student.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editStudent")) {

				String strBtnEdit = request.getParameter("editStudent");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/student/getstudents?studentId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				studentDao = new StudentDaoImpl();
				LinkedList<Student> studentList = studentDao.getStudentList(
						line, "Student");

				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);

				requestDispatcher = request
						.getRequestDispatcher("edit_student.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateStudent")) {

				String strID = request.getParameter("dID");
				System.out.println(strID);

				student = new Student();
	/*			student.setName(strName);
				student.setDescription(strDescription);
				student.setStatusId(Integer.parseInt(strStatus));
				student.setTypeId(Integer.parseInt(strType));
*/
				System.out.println(student);

				String restURL = BackendConstants.SERVICEURL +"/student/updatestudent/"
						+ strID;

				studentDao = new StudentDaoImpl();
				strResponse = studentDao.updateStudent(student, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<Student> studentList = getStudentFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_student.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadStudent")) {

				LinkedList<Student> studentList = getStudentFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("StudentList", studentList);

				requestDispatcher = request
						.getRequestDispatcher("get_student.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_student.jsp");
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
	public LinkedList<Student> getStudentFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Student> studentList = new LinkedList<Student>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/student/getstudent";
			httpAPICaller = new HttpAPICaller();
			
			//System.out.println(strURL + " strURL");
			String line = httpAPICaller.getRequest(strURL);

		//	System.out.println(line + " line");
			studentDao = new StudentDaoImpl();
			studentList = studentDao.getStudentList(line, "Student");

		} catch (Exception e) {
			System.out.println(e.toString() + " getStudentFromDB");
		} finally {
			return studentList;
		}

	}

}
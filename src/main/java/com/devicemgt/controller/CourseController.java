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

import com.devicemgt.dao.CourseDao;
import com.devicemgt.dao.CourseDaoImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.Course;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;


@WebServlet("/CourseController")
public class CourseController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Course course;
	CourseDao courseDao;
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
	public CourseController() {
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

		if (request.getParameter("getCourse") != null) {

			actionType = "getCourse";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteCourse";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editCourse";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateCourse";
		}

		if ((request.getParameter("addCourse") != null)
				|| (actionType.equals("updateCourse"))) {


			strName = request.getParameter("courseName");
			strDisplayID = request.getParameter("courseID");
			//strDescription = request.getParameter("description");



		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getCourse")) {

				LinkedList<Course> courseList = getCourseFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);
				requestDispatcher = request.getRequestDispatcher("getcourse.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {


				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;

				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?courseName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&courseName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/course/getcoursesdetail";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				courseDao = new CourseDaoImpl();
				LinkedList<Course> courseList = courseDao.getCourseList(
						line, "Course");

				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);

				requestDispatcher = request
						.getRequestDispatcher("getcourse.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addCourse")) {

				course = new Course();
				course.setCourseName(strName);
				course.setCourseDisplayID(strDisplayID);


				String restURL = BackendConstants.SERVICEURL +"/course/addcourse";
				System.out.println("restURL add " + restURL);
				courseDao = new CourseDaoImpl();
				strResponse = courseDao.addCourse(course, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_course.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getCourseOnLoad")) {

				LinkedList<Course> courseList = getCourseFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_course.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteCourse")) {

				String strDltRadio = request.getParameter("deleteCourse");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/course/deletecourse/"
						+ strDltRadio;

				courseDao = new CourseDaoImpl();
				try {
					strResponse = courseDao.deleteCourse(restURL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LinkedList<Course> courseList = getCourseFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_course.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editCourse")) {

				String strBtnEdit = request.getParameter("editCourse");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/course/getcourses?courseId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				courseDao = new CourseDaoImpl();
				LinkedList<Course> courseList = courseDao.getCourseList(
						line, "Course");

				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);

				requestDispatcher = request
						.getRequestDispatcher("edit_course.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateCourse")) {

				String strID = request.getParameter("dID");
				System.out.println(strID);

				course = new Course();
	/*			course.setName(strName);
				course.setDescription(strDescription);
				course.setStatusId(Integer.parseInt(strStatus));
				course.setTypeId(Integer.parseInt(strType));
*/
				System.out.println(course);

				String restURL = BackendConstants.SERVICEURL +"/course/updatecourse/"
						+ strID;

				courseDao = new CourseDaoImpl();
				strResponse = courseDao.updateCourse(course, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<Course> courseList = getCourseFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_course.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadCourse")) {

				LinkedList<Course> courseList = getCourseFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("CourseList", courseList);

				requestDispatcher = request
						.getRequestDispatcher("get_course.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_course.jsp");
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
	public LinkedList<Course> getCourseFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Course> courseList = new LinkedList<Course>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/course/getcourse";
			httpAPICaller = new HttpAPICaller();
			
			//System.out.println(strURL + " strURL");
			String line = httpAPICaller.getRequest(strURL);

		//	System.out.println(line + " line");
			courseDao = new CourseDaoImpl();
			courseList = courseDao.getCourseList(line, "Course");

		} catch (Exception e) {
			System.out.println(e.toString() + " getCourseFromDB");
		} finally {
			return courseList;
		}

	}

}
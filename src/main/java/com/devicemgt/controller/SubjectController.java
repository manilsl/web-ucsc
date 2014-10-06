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

import com.devicemgt.dao.SubjectDao;
import com.devicemgt.dao.SubjectDaoImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.Subject;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;


@WebServlet("/SubjectController")
public class SubjectController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Subject subject;
	SubjectDao subjectDao;
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
	public SubjectController() {
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

		if (request.getParameter("getSubject") != null) {

			actionType = "getSubject";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteSubject";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editSubject";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateSubject";
		}

		if ((request.getParameter("addSubject") != null)
				|| (actionType.equals("updateSubject"))) {


			strName = request.getParameter("subjectName");
			strDisplayID = request.getParameter("subjectID");
			//strDescription = request.getParameter("description");



		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getSubject")) {

				LinkedList<Subject> subjectList = getSubjectFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);
				requestDispatcher = request.getRequestDispatcher("getsubject.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {


				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;

				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?subjectName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&subjectName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/subject/getsubjectsdetail";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				subjectDao = new SubjectDaoImpl();
				LinkedList<Subject> subjectList = subjectDao.getSubjectList(
						line, "Subject");

				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);

				requestDispatcher = request
						.getRequestDispatcher("getsubject.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addSubject")) {

				subject = new Subject();
				/*subject.setSubjectName(strName);
				subject.setSubjectDisplayID(strDisplayID);*/


				String restURL = BackendConstants.SERVICEURL +"/subject/addsubject";
				System.out.println("restURL add " + restURL);
				subjectDao = new SubjectDaoImpl();
				strResponse = subjectDao.addSubject(subject, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_subject.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSubjectOnLoad")) {

				LinkedList<Subject> subjectList = getSubjectFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_subject.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteSubject")) {

				String strDltRadio = request.getParameter("deleteSubject");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/subject/deletesubject/"
						+ strDltRadio;

				subjectDao = new SubjectDaoImpl();
				try {
					strResponse = subjectDao.deleteSubject(restURL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LinkedList<Subject> subjectList = getSubjectFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_subject.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editSubject")) {

				String strBtnEdit = request.getParameter("editSubject");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/subject/getsubjects?subjectId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				subjectDao = new SubjectDaoImpl();
				LinkedList<Subject> subjectList = subjectDao.getSubjectList(
						line, "Subject");

				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);

				requestDispatcher = request
						.getRequestDispatcher("edit_subject.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateSubject")) {

				String strID = request.getParameter("dID");
				System.out.println(strID);

				subject = new Subject();
	/*			subject.setName(strName);
				subject.setDescription(strDescription);
				subject.setStatusId(Integer.parseInt(strStatus));
				subject.setTypeId(Integer.parseInt(strType));
*/
				System.out.println(subject);

				String restURL = BackendConstants.SERVICEURL +"/subject/updatesubject/"
						+ strID;

				subjectDao = new SubjectDaoImpl();
				strResponse = subjectDao.updateSubject(subject, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<Subject> subjectList = getSubjectFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_subject.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadSubject")) {

				LinkedList<Subject> subjectList = getSubjectFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("SubjectList", subjectList);

				requestDispatcher = request
						.getRequestDispatcher("get_subject.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_subject.jsp");
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
	public LinkedList<Subject> getSubjectFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Subject> subjectList = new LinkedList<Subject>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/subject/getsubject";
			httpAPICaller = new HttpAPICaller();
			
			//System.out.println(strURL + " strURL");
			String line = httpAPICaller.getRequest(strURL);

		//	System.out.println(line + " line");
			subjectDao = new SubjectDaoImpl();
			subjectList = subjectDao.getSubjectList(line, "Subject");

		} catch (Exception e) {
			System.out.println(e.toString() + " getSubjectFromDB");
		} finally {
			return subjectList;
		}

	}

}
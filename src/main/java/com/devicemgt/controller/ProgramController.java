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

import com.devicemgt.dao.ProgramDao;
import com.devicemgt.dao.ProgramDaoImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.Program;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;


@WebServlet("/ProgramController")
public class ProgramController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Program program;
	ProgramDao programDao;
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
	public ProgramController() {
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

		if (request.getParameter("getProgram") != null) {

			actionType = "getProgram";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteProgram";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editProgram";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateProgram";
		}

		if ((request.getParameter("addProgram") != null)
				|| (actionType.equals("updateProgram"))) {


			strName = request.getParameter("programName");
			strDisplayID = request.getParameter("programID");
			//strDescription = request.getParameter("description");



		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getProgram")) {

				LinkedList<Program> programList = getProgramFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);
				requestDispatcher = request.getRequestDispatcher("getprogram.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {


				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;

				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?programName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&programName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/program/getprogram";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				programDao = new ProgramDaoImpl();
				LinkedList<Program> programList = programDao.getProgramList(
						line, "Program");

				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);

				requestDispatcher = request
						.getRequestDispatcher("getprogram.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addProgram")) {

				program = new Program();
				/*program.setProgramName(strName);
				program.setProgramDisplayID(strDisplayID);*/


				String restURL = BackendConstants.SERVICEURL +"/program/addprogram";
				System.out.println("restURL add " + restURL);
				programDao = new ProgramDaoImpl();
				strResponse = programDao.addProgram(program, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_program.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getProgramOnLoad")) {

				LinkedList<Program> programList = getProgramFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_program.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteProgram")) {

				String strDltRadio = request.getParameter("deleteProgram");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/program/deleteprogram/"
						+ strDltRadio;

				programDao = new ProgramDaoImpl();
				try {
					strResponse = programDao.deleteProgram(restURL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LinkedList<Program> programList = getProgramFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_program.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editProgram")) {

				String strBtnEdit = request.getParameter("editProgram");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/program/getprograms?programId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				programDao = new ProgramDaoImpl();
				LinkedList<Program> programList = programDao.getProgramList(
						line, "Program");

				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);

				requestDispatcher = request
						.getRequestDispatcher("edit_program.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateProgram")) {

				String strID = request.getParameter("dID");
				System.out.println(strID);

				program = new Program();
	/*			program.setName(strName);
				program.setDescription(strDescription);
				program.setStatusId(Integer.parseInt(strStatus));
				program.setTypeId(Integer.parseInt(strType));
*/
				System.out.println(program);

				String restURL = BackendConstants.SERVICEURL +"/program/updateprogram/"
						+ strID;

				programDao = new ProgramDaoImpl();
				strResponse = programDao.updateProgram(program, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<Program> programList = getProgramFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_program.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadProgram")) {

				LinkedList<Program> programList = getProgramFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("ProgramList", programList);

				requestDispatcher = request
						.getRequestDispatcher("get_program.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_program.jsp");
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
	public LinkedList<Program> getProgramFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Program> programList = new LinkedList<Program>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/program/getprogram";
			httpAPICaller = new HttpAPICaller();
			
			//System.out.println(strURL + " strURL");
			String line = httpAPICaller.getRequest(strURL);

		//	System.out.println(line + " line");
			programDao = new ProgramDaoImpl();
			programList = programDao.getProgramList(line, "Program");

		} catch (Exception e) {
			System.out.println(e.toString() + " getProgramFromDB");
		} finally {
			return programList;
		}

	}

}
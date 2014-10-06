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

import com.devicemgt.dao.ScheduleDao;
import com.devicemgt.dao.ScheduleDaoImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.Schedule;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;


@WebServlet("/ScheduleController")
public class ScheduleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Schedule schedule;
	ScheduleDao scheduleDao;
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
	public ScheduleController() {
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

		if (request.getParameter("getSchedule") != null) {

			actionType = "getSchedule";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteSchedule";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editSchedule";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateSchedule";
		}

		if ((request.getParameter("addSchedule") != null)
				|| (actionType.equals("updateSchedule"))) {


			strName = request.getParameter("scheduleName");
			strDisplayID = request.getParameter("scheduleID");
			//strDescription = request.getParameter("description");



		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getSchedule")) {

				LinkedList<Schedule> scheduleList = getScheduleFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);
				requestDispatcher = request.getRequestDispatcher("getschedule.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {


				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;

				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?scheduleName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&scheduleName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/schedule/getschedulesdetail";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				scheduleDao = new ScheduleDaoImpl();
				LinkedList<Schedule> scheduleList = scheduleDao.getScheduleList(
						line, "Schedule");

				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);

				requestDispatcher = request
						.getRequestDispatcher("getschedule.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addSchedule")) {

				schedule = new Schedule();
				/*schedule.setScheduleName(strName);
				schedule.setScheduleDisplayID(strDisplayID);*/


				String restURL = BackendConstants.SERVICEURL +"/schedule/addschedule";
				System.out.println("restURL add " + restURL);
				scheduleDao = new ScheduleDaoImpl();
				strResponse = scheduleDao.addSchedule(schedule, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_schedule.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getScheduleOnLoad")) {

				LinkedList<Schedule> scheduleList = getScheduleFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_schedule.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteSchedule")) {

				String strDltRadio = request.getParameter("deleteSchedule");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/schedule/deleteschedule/"
						+ strDltRadio;

				scheduleDao = new ScheduleDaoImpl();
				try {
					strResponse = scheduleDao.deleteSchedule(restURL);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				LinkedList<Schedule> scheduleList = getScheduleFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_schedule.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editSchedule")) {

				String strBtnEdit = request.getParameter("editSchedule");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/schedule/getschedules?scheduleId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				scheduleDao = new ScheduleDaoImpl();
				LinkedList<Schedule> scheduleList = scheduleDao.getScheduleList(
						line, "Schedule");

				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);

				requestDispatcher = request
						.getRequestDispatcher("edit_schedule.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateSchedule")) {

				String strID = request.getParameter("dID");
				System.out.println(strID);

				schedule = new Schedule();
	/*			schedule.setName(strName);
				schedule.setDescription(strDescription);
				schedule.setStatusId(Integer.parseInt(strStatus));
				schedule.setTypeId(Integer.parseInt(strType));
*/
				System.out.println(schedule);

				String restURL = BackendConstants.SERVICEURL +"/schedule/updateschedule/"
						+ strID;

				scheduleDao = new ScheduleDaoImpl();
				strResponse = scheduleDao.updateSchedule(schedule, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<Schedule> scheduleList = getScheduleFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_schedule.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadSchedule")) {

				LinkedList<Schedule> scheduleList = getScheduleFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("ScheduleList", scheduleList);

				requestDispatcher = request
						.getRequestDispatcher("get_schedule.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_schedule.jsp");
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
	public LinkedList<Schedule> getScheduleFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Schedule> scheduleList = new LinkedList<Schedule>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/schedule/getschedule";
			httpAPICaller = new HttpAPICaller();
			
			//System.out.println(strURL + " strURL");
			String line = httpAPICaller.getRequest(strURL);

		//	System.out.println(line + " line");
			scheduleDao = new ScheduleDaoImpl();
			scheduleList = scheduleDao.getScheduleList(line, "Schedule");

		} catch (Exception e) {
			System.out.println(e.toString() + " getScheduleFromDB");
		} finally {
			return scheduleList;
		}

	}

}
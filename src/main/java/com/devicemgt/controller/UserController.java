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

import com.devicemgt.dao.DeviceRepoDAOImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.dao.UserDAO;
import com.devicemgt.dao.UserDAOImpl;
import com.devicemgt.model.Device;
import com.devicemgt.model.User;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;
import com.devicemgt.util.PasswordEncript;
import com.devicemgt.util.Rest;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpAPICaller httpAPICaller;
	User user;
	UserDAO userDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RequestDispatcher requestDispatcher = null;

		String fname = null;
		String lname = null;
		String userName = null;
		String pass = null;
		String compass = null;
		String email = null;
		String tele = null;
		String description = null;

		String strResponse = "";

		String actionType = (String) request.getSession(false).getAttribute(
				"actionType");
		System.out.println(actionType);

		try {

			if (request.getParameter("deleteBtn") != null) {
				actionType = "deleteDevice";
			} else if (request.getParameter("editBtn") != null) {
				actionType = "editDevice";
			} else if (request.getParameter("updateBtn") != null) {
				actionType = "updateDevice";
			}

			if ((request.getParameter("userRegister") != null)) {

				fname = request.getParameter("firstName");
				lname = request.getParameter("lastName");
				userName = request.getParameter("userName");
				pass = request.getParameter("password");
				compass = request.getParameter("confirmPassword");
				email = request.getParameter("email");
				tele = request.getParameter("telephone");
				description = request.getParameter("description");
				boolean isValidated = true;

				if (fname == null || fname.length() == 0) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.FIRST_NAME + " "
									+ FrontConstants.REQUIRED);

				} else if (lname == null || lname.length() == 0) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.LAST_NAME + " "
									+ FrontConstants.REQUIRED);
				} else if (pass == null || pass.length() == 0) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.PASSWORD + " "
									+ FrontConstants.REQUIRED);
				} else if (email == null || email.length() == 0) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.EMAIL + " "
									+ FrontConstants.REQUIRED);
				} else if (tele == null || tele.length() == 0) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.TELEPHONE + " "
									+ FrontConstants.REQUIRED);
				} else if (!pass.equals(compass)) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.CONFIRM_PASSWORD + " "
									+ FrontConstants.REQUIRED);
				} else if (description == null || description.length() == 0) {
					isValidated = false;
					request.setAttribute(BackendConstants.ERROR_MESSAGE,
							FrontConstants.DESCRIPTION + " "
									+ FrontConstants.REQUIRED);
				}

				String passWord = PasswordEncript.getEncriptedPassword(pass);
				System.out.println(passWord);
				user = new User();
				user.setDescription(description);
				user.setEmail(email);
				user.setPasssword(pass);
				user.setTelNo(tele);
				user.setUserFname(fname);
				user.setUserLname(lname);
				user.setUsername(userName);

				String restURL = BackendConstants.SERVICEURL +"/user/adduser";

				userDAO = new UserDAOImpl();

				strResponse = userDAO.addUser(user, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				requestDispatcher = request
						.getRequestDispatcher("index.jsp");
				requestDispatcher.forward(request, response);

			}
			if (actionType.equals("getUsersOnLoad")) {

				String strURL = BackendConstants.SERVICEURL +"/user/getusers";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				System.out.println(line);

				userDAO = new UserDAOImpl();

				LinkedList<User> userList = userDAO.getUserList(line, "user");
				HttpSession session = request.getSession();

				session.setAttribute("UserList", userList);
				requestDispatcher = request
						.getRequestDispatcher("get_user.jsp");
				requestDispatcher.forward(request, response);
			}
			
			if (actionType.equals("getMyProfile")) {

				String strURL = BackendConstants.SERVICEURL +"/user/getusers";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				System.out.println(line);

				userDAO = new UserDAOImpl();

				LinkedList<User> userList = userDAO.getUserList(line, "user");
				HttpSession session = request.getSession();

				session.setAttribute("UserList", userList);
				requestDispatcher = request
						.getRequestDispatcher("getMyProfile.jsp");
				requestDispatcher.forward(request, response);
			}
			
			if (actionType.equals("deleteDevice")) {

				String strDltRadio = request.getParameter("deleteUser");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/user/deleteuser/"
						+ strDltRadio;

				userDAO = new UserDAOImpl();
				strResponse = userDAO.deleteUser(restURL);

				String strURL = BackendConstants.SERVICEURL +"/user/getusers";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				System.out.println(line);

				userDAO = new UserDAOImpl();

				LinkedList<User> userList = userDAO.getUserList(line, "user");
				HttpSession session = request.getSession();
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				session.setAttribute("UserList", userList);
				requestDispatcher = request
						.getRequestDispatcher("get_user.jsp");
				requestDispatcher.forward(request, response);

			}
			if (actionType.equals("editDevice")) {

				String strBtnEdit = request.getParameter("editUser");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/user/getusers?userId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				System.out.println(line);

				userDAO = new UserDAOImpl();

				LinkedList<User> userList = userDAO.getUserList(line, "user");

				HttpSession session = request.getSession();

				session.setAttribute("UserList", userList);
				requestDispatcher = request
						.getRequestDispatcher("edit_user.jsp");
				requestDispatcher.forward(request, response);

			}
			if (actionType.equals("updateDevice")) {

				fname = request.getParameter("firstName");
				lname = request.getParameter("lastName");
				userName = request.getParameter("userName");
				
				compass = request.getParameter("confirmPassword");
				email = request.getParameter("email");
				tele = request.getParameter("telephone");
				description = request.getParameter("description");
				String role=request.getParameter("role");

				String strID = request.getParameter("uID");
				System.out.println(strID);

				user = new User();
				user.setUserId(strID);
				user.setEmail(email);
				user.setTelNo(tele);
				user.setUserFname(fname);
				user.setUserLname(lname);
				user.setUsername(userName);
				user.setRole(role);

				System.out.println(user);

				String restURL = BackendConstants.SERVICEURL +"/user/updateuser/"
						+ strID;

				userDAO = new UserDAOImpl();
				strResponse = userDAO.updateUser(user, restURL);
				System.out.println(strResponse);
				
				String strURL = Rest.getProperty() +"/user/getusers";
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(strURL);

				LinkedList<User> userList = userDAO.getUserList(line, "user");
				HttpSession session = request.getSession();
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				session.setAttribute("UserList", userList);
				requestDispatcher = request
						.getRequestDispatcher("get_user.jsp");
				requestDispatcher.forward(request, response);

			}

		} catch (Exception e) {
			// TODO: handle exception
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

}

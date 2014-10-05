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

import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.dao.UserDAO;
import com.devicemgt.dao.UserDAOImpl;
import com.devicemgt.model.User;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;
import com.devicemgt.util.PasswordEncript;
import com.devicemgt.util.Rest;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	HttpAPICaller httpAPICaller;
	User user;
	UserDAO userDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		RequestDispatcher requestDispatcher = null;

		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");

		boolean isValidated = true;
		if (userName == null || userName.length() == 0) {
			isValidated = false;
			request.setAttribute(BackendConstants.ERROR_MESSAGE,
					FrontConstants.USERNAME + " " + FrontConstants.REQUIRED);
		} else if (passWord == null || passWord.length() == 0) {
			isValidated = false;
			request.setAttribute(BackendConstants.ERROR_MESSAGE,
					FrontConstants.PASSWORD + " " + FrontConstants.REQUIRED);
		}

		if (isValidated) {

			String encriptedPassword = new PasswordEncript()
					.getEncriptedPassword(passWord);
			String restURL = BackendConstants.SERVICEURL
					+ "/login/getlogin?username=" + userName + "&password="
					+ encriptedPassword;

			httpAPICaller = new HttpAPICaller();
			String line = httpAPICaller.getRequest(restURL);

			if (line != null) {

				System.out.println(line);

				userDAO = new UserDAOImpl();

				LinkedList<User> userList = userDAO.getUserList(line, "user");

				if (userList.size() != 0) {

					HttpSession session = request.getSession();

					session.setAttribute(BackendConstants.LOGIN, userList);
					// request.getSession().setAttribute(BackendConstants.LOGIN,
					// userList);

					System.out.println(userList.get(0).getUsername());

					if (userList.get(0).getRole().equals("admin")) {
						response.sendRedirect("https://appserver.dev.cloud.wso2.com/t/isg9251/webapps/devicerepoadmin-default-SNAPSHOT/");
					}
					if (userList.get(0).getRole().equals("user")) {
						requestDispatcher = request
								.getRequestDispatcher("home.jsp");
						requestDispatcher.forward(request, response);
					}

				} else {
					request.setAttribute(BackendConstants.ERROR_MESSAGE,"Username And/Or Password Wrong");
//					requestDispatcher = request
//							.getRequestDispatcher("index.jsp");
					requestDispatcher.forward(request, response);
				}

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("index.jsp");
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

}

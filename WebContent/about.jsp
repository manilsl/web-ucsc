<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
</head>
<body id="page1">

	<%
		LinkedList<User> deviceList = (LinkedList<User>) session
				.getAttribute(BackendConstants.LOGIN);

		if (deviceList != null) {
			//String username = deviceList.get(0).getUsername();

			//String actionType = "getUsersOnLoad";
			session.setAttribute(BackendConstants.LOGIN, deviceList);

			Cookie userName = new Cookie("user_name", deviceList.get(0)
					.getUsername());
			Cookie userRole = new Cookie("user_role", deviceList.get(0)
					.getRole());
			Cookie userID = new Cookie("user_id", deviceList.get(0)
					.getUserId());

			userName.setMaxAge(60 * 60 * 24);
			userRole.setMaxAge(60 * 60 * 24);
			userID.setMaxAge(60 * 60 * 24);

			response.addCookie(userName);
			response.addCookie(userRole);
			response.addCookie(userID);

			String username = null;
			String role = "user";

			Cookie cookie = null;
			Cookie[] cookies = null;
			cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					cookie = cookies[i];
					if (cookie.getName().equals("user_name")) {
						username = cookie.getValue();
					} else if (cookie.getName().equals("user_role")) {
						role = cookie.getValue();
					}
				}
			}

			if (username != null) {
	%>

	<table height="100" width="100%" border="0" cellspacing="1"
		bgcolor="#474747">
		<tr>
			<td><font color="#fff">Welcome : <%=username%></font></td>
		</tr>
		<tr>
			<td width="160" />
			<td width="800"><img src="images/banner.png" width="600"
				height="80" /></td>
			<td width="130"><img src="images/wso2-logo.png" width="100"
				height="40" /></td>
		</tr>
	</table>
	<div id="maindiv">

		<nav>
		<ul id="menu">
			<li><a href="home.jsp"><span><span>Home</span></span></a></li>
			<li><a href="about.jsp"><span><span>About</span></span></a></li>
			<li><a>Device</a>
				<ul>
					
					<li><a href="get_device.jsp">View Device</a></li>
					<li><a href="getdevicetype.jsp">View Device Type</a></li>
				</ul></li>
			<li><a href="home.jsp">Activity</a>
				<ul>
					<li><a href="get_transaction.jsp">View Activity</a></li>
				</ul></li>
			<li><a href="home.jsp">Administration</a>
				<ul>
					<li><a href="getMyProfile.jsp">My Information</a></li>
					<li><a href="user_register.jsp">Add User</a></li>
					<li><a href="get_transaction_status.jsp">View Activity
							Status</a></li>
					<li><a href="get_device_status.jsp">View Device Status</a></li>

				</ul></li>

		</ul>
		</nav>
		
		<div id="frame">
			<div id="content">

				<table width="900" height="80" border="0" cellspacing="1">
					<tr height="60"></tr>



					<tr>
						
						<td width="150"></td>
						<td width="600">
							<h3 align="left">Team Members</h3> <br />
							<li type="square">Inoka Suresh</li>
						<br />
							<li type="square">Manil Liyanage</li>
						<br />
							<li type="square">Jayomi Rajapakshe</li>
						<br />
							<li type="square">Sampath Amarakoon</li>
						<br />


						</td>
						<td width="20">
					</tr>

					<tr height="60"></tr>
				</table>

			</div>
		</div>
		
		<%
			String errorMessage = (String) request
							.getAttribute(BackendConstants.ERROR_MESSAGE);
					if (errorMessage != null) {
						out.println("*" + errorMessage);
					}
		%>


		<div id="frame">
			<center>
				<div id="content"></div>
			</center>
		</div>
		<%
			} else {
					//String url = "http://localhost:8080/devicerepository/";
					String url = "/";
		%>
		<jsp:forward page="<%=url%>" />

		<%
			}
			} else {
				String url = "/";
			}
		%>


		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>
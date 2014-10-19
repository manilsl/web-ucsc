<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.Device"%>
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

	
	<table height="100" width="100%" border="0" cellspacing="1"
		bgcolor="#474747">
		<%-- <tr>
			<td><font color="#fff">Welcome : <%=username%></font></td>
		</tr> --%>
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
					<li><a href="">Add Devices</a></li>
					<li><a href="">Edit Details</a></li>
					<li><a href="">Search</a></li>
					<li><a href="">Remove Device</a></li>
				</ul></li>
			<li><a href="home.jsp">Activity</a>
				<ul>
					<li><a href="">Lend</a></li>
					<li><a href="">View Activity</a></li>
					<li><a href="">Alter
							Activity</a></li>
				</ul></li>
			<li><a href="home.jsp">Administration</a>
				<ul>
					<li><a href="">My Information</a></li>
					<li><a href="">Add User</a></li>
					<li><a href="">Delete User</a></li>
					<li><a href="">Edit User</a></li>
					<li><a href="">Add Activity
							Status</a></li>
					<li><a href="">View Activity
							Status</a></li>
					<li><a href="">Add Device Status</a></li>
					<li><a href="">View Device Status</a></li>

				</ul></li>

		</ul>
		</nav>

		<center style="Background-color: #ccff00;">
			<b><font color="red"> <%
 	String errorMessage = (String) request
 				.getAttribute(BackendConstants.ERROR_MESSAGE);
 		if (errorMessage != null) {
 			out.println("*" + errorMessage);
 		}
 %>
			</font></b>

			<%
				String actionType = "addDevice";
					session.setAttribute("actionType", actionType);
			%>
		</center>
		<div id="frame">
			<div id="content">
			<center>
				<form action="UserController" method="post">
					
						<h2>User Registration</h2>
						<table border="0" cellspacing="1">
							<tr>
								<td>First Name :</td>
								<td><input type="text" name="firstName"
									placeholder="Enter First Name" /></td>
							</tr>
							<tr>
								<td>Last Name :</td>
								<td><input type="text" name="lastName"
									placeholder="Enter Last Name" /></td>
							</tr>
							<tr>
								<td>User Name :</td>
								<td><input type="text" name="userName"
									placeholder="Enter User Name" /></td>
							</tr>

							<tr>
								<td>Password :</td>
								<td><input type="password" name="password"
									placeholder="Enter Password" /></td>
							</tr>
							<tr>
								<td>Confirm Password :</td>
								<td><input type="password" name="confirmPassword"
									placeholder="Re Enter Password" /></td>
							</tr>
							<tr>
								<td>Email :</td>
								<td><input type="text" name="email"
									placeholder="Enter Email" /></td>
							</tr>
							<tr>
								<td>Telephone :</td>
								<td><input type="text" name="telephone"
									placeholder="Enter Telephone #" maxlength="15" /></td>
							</tr>
							<tr>
								<td>Description :</td>
								<td><input type="text" name="description"
									placeholder="optional" /></td>
							</tr>


							<tr>
								<td></td>
								<td><input type="submit" value="Register"
									name="userRegister" /></td>
							</tr>
						</table>
					
				</form></center>
		<%-- 		<%
					} else {
						String url = "/";
				%>
				<jsp:forward page="<%=url%>" />
				<%
					}
				%> --%>

			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>
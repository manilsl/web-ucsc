<%@page import="com.devicemgt.util.*"%>
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
		<tr>
			<td width="160" />
			<td width="800"><img src="images/banner.png" width="600"
				height="80" /></td>
			<td width="130"><img src="images/wso2-logo.png" width="100"
				height="40" /></td>
		</tr>
	</table>
	<div id="maindiv">

		<center style="Background-color: #ccff00;">
			<b><font color="red"> <%
 	String errorMessage = (String) request
 			.getAttribute(BackendConstants.ERROR_MESSAGE);
 	if (errorMessage != null) {
 		out.println("*" + errorMessage);
 	}
 %>
			</font></b>
		</center>
		<div>
			<center>
				<div>

					<form action="LoginController" method="post">
						<table height="80" border="0" cellspacing="1">
							<tr height="100"></tr>

							<tr>
								<td>Username </td>
								<td><input type="text" name="userName"
									placeholder="Enter Username" /></td>
							</tr>
							<tr>
								<td>Password</td>
								<td><input type="password" name="passWord"
									placeholder="Enter Password" /></td>
							</tr>
							<tr>
								<td></td>
								<td><input type="submit" name="login" value="Login" /></td>
							</tr>
						</table>
						<a href="user_register.jsp">OR Register With Us</a>
					</form>


				</div>
			</center>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>
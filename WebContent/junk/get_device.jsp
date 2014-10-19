<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.Course"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Course Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script>
	function loadCourses() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("maindiv").innerHTML = xmlhttp.responseText;
			}
		}
		xmlhttp.open("GET", "CourseController", true);
		xmlhttp.send();
	}
</script>

</head>
<body id="page1" onload="loadCourses()" />

<%-- <%
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
%> --%>

<%
	String actionType = "loadCourse";
		session.setAttribute("actionType", actionType);
%>
<table height="100" width="100%" border="0" cellspacing="1"
	bgcolor="#474747">
	<tr>
		<td><font color="#fff">Welcome : MANIL></font></td>
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
		<li><a>Course</a>
			<ul>

				<li><a href="get_course.jsp">View Course</a></li>
				<li><a href="getcoursetype.jsp">View Course Type</a></li>
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
				<li><a href="get_course_status.jsp">View Course Status</a></li>

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
	</center>

	<%
		LinkedList<Course> courseList = (LinkedList<Course>) session.getAttribute("CourseList");
	%>

	<div id="frame">
		<div id="content">
			<form action="CourseController" method="post">

				<table width="900" height="80" border="0" cellspacing="1">
					<tr height="60">
						<td align="center"><font font-size="large">Search for
								Activities</font></td>
					</tr>
					<%
						if (courseList != null) {
					%>
					<tr height="50">

						<td align="center">Course : <input type="text" id="dNAME"
							list="searchCourseList" name="dNAME" class="dNAME"
							placeholder="search here" autocomplete="off" /> <datalist
								id="searchCourseList"> <%
 	for (int y = 0; y < courseList.size(); y++) {
 %>
							<option id="<%=courseList.get(y).getCourseID()%>"
								value="<%=courseList.get(y).getCourseName()%>">
								<%=courseList.get(y).getCourseName()%>
							</option>
							<%
								}
							%> </datalist>
						</td>
					</tr>

					<tr>

						</td>


						<td align="center"><br /> <input type="submit"
							value="Get All Course" name="getCourse" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"
							value="Search" name="getSearch" /> <%
 	} else {
 %> <%
 	out.println("Please wait .. ");
 			response.setIntHeader("Refresh", 2);
 		}
 %></td>



					</tr>
					<tr height="60"></tr>
				</table>

			</form>
			<%-- <%
				} else {
					String url = "/";
			%>
			<jsp:forward page="<%=url%>" />
			<%
				}
			%>
 --%>
		</div>
	</div>

	<footer>

	<p class="rf">©2014 WSO2</p>
	<div style="clear: both;"></div>
	</footer>
	</body>
</html>
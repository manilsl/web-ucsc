<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.Transaction"%>
<%@page import="com.devicemgt.model.Device"%>
<%@page import="com.devicemgt.model.TransactionStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script>
	function loadDevices() {
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
		xmlhttp.open("GET", "TransactionController", true);
		xmlhttp.send();
	}
</script>
</head>
<body id="page1" onload="loadDevices()">

	<%
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

	<%
		String actionType = "loadList";
			session.setAttribute("actionType", actionType);
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
		<div id="frame">
			<div id="content">

				<%
					LinkedList<Device> deviceList = (LinkedList<Device>) session
								.getAttribute("DeviceListShow");
						LinkedList<TransactionStatus> statusList = (LinkedList<TransactionStatus>) session
								.getAttribute("StatusListShow");
				%>

				<form action="TransactionController" method="get">
					<table width="900" height="80" border="0" cellspacing="1">
						<tr height="60">
							<td align="center"><font font-size="large">Search for
									Activities</font></td>
						</tr>
						<%
							if (deviceList != null && statusList != null) {
						%>
						<tr height="50">

							<td align="center">Device : <input type="text"
								id="searchDevice" list="searchDeviceList" name="searchDevice"
								class="searchDevice" placeholder="search here"
								autocomplete="off" /> <datalist id="searchDeviceList">
								<%
									for (int y = 0; y < deviceList.size(); y++) {
								%>
								<option id="<%=deviceList.get(y).getId()%>"
									value="<%=deviceList.get(y).getName()%>">
									<%=deviceList.get(y).getName()%>
								</option>
								<%
									}
								%> </datalist>
							</td>
						</tr>

						<tr height="50">

							<td align="center">Status : <input type="text"
								id="searchStatus" list="searchStatusList" name="searchStatus"
								class="searchStatus" placeholder="search here"
								autocomplete="off" /> <datalist id="searchStatusList">

								<%
									for (int y = 0; y < statusList.size(); y++) {
								%>
								<option id="<%=statusList.get(y).getTransactionStatusId()%>"
									value="<%=statusList.get(y).getTransactionStatusName()%>">
									<%=statusList.get(y).getTransactionStatusName()%>
								</option>
								<%
									}
								%> </datalist>
							</td>

						</tr>

						<tr>

							</td>


							<td align="center"><br /> <input type="submit"
								value="Get All Activities" name="getTransaction" />
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"
								value="Search Activities" name="getSearch" /> <%
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
				<%
					} else {
						String url = "/";
				%>
				<jsp:forward page="<%=url%>" />
				<%
					}
				%>

			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>
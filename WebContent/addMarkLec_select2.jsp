<%@page import="com.devicemgt.util.*"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.devicemgt.model.StudentProgram"%>
<%@page import="com.devicemgt.model.StudentProgramDetail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WSO2 Device Repository</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<%-- <%LinkedList<StudentProgramDetail> marksList =null;%>  --%>
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
		xmlhttp.open("GET", "StudentProgramController", true);
		xmlhttp.send();
	}
	
	
	</script>
	
<script>
function checkEdited(index) {

	
	document.getElementById("isEdit"+index).value = "1";

	 
	}
</script>
addMarkLec_select2
</head>
		<%	String actionType = "getStudentProgramDetail";
			session.setAttribute("actionType", actionType);
		%>

<body id="page1"  onload="loadCourses()">

	<table height="100" width="100%" border="0" cellspacing="1"
		bgcolor="#474747">
		<tr>
			<td><font color="#fff">Welcome : name</font></td>
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
					<li><a href="add_device.jsp">Add Devices</a></li>
					<li><a href="updateordelete_device.jsp">Alter Details</a></li>
					<li><a href="get_device.jsp">View Device</a></li>
					<li><a href="add_devicetype.jsp">Add Device Type</a></li>
					<li><a href="getdevicetype.jsp">View Device Type</a></li>
				</ul></li>
			<li><a href="home.jsp">Activity</a>
				<ul>
					<li><a href="add_transaction.jsp">Lend</a></li>
					<li><a href="get_transaction.jsp">View Activity</a></li>
					<li><a href="updateordelete_transaction.jsp">Alter
							Activity</a></li>
				</ul></li>
			<li><a href="home.jsp">Administration</a>
				<ul>
					<li><a href="getMyProfile.jsp">My Information</a></li>
					<li><a href="user_register.jsp">Add User</a></li>
					<li><a href="get_user.jsp">Delete User</a></li>
					<li><a href="get_user.jsp">Edit User</a></li>
					<li><a href="add_transaction_status.jsp">Add Activity
							Status</a></li>
					<li><a href="get_transaction_status.jsp">View Activity
							Status</a></li>
					<li><a href="add_device_status.jsp">Add Device Status</a></li>
					<li><a href="get_device_status.jsp">View Device Status</a></li>

				</ul></li>

		</ul>
		</nav>

		<center style="Background-color: #ccff00;">
			<b><font color="red"> <%
 	String errorMessage = (String) request.getAttribute(BackendConstants.ERROR_MESSAGE);
 		if (errorMessage != null) {
 			out.println("*" + errorMessage);
 		}
 %>
			</font></b>
		</center>

		<% LinkedList<StudentProgramDetail> marksList = (LinkedList<StudentProgramDetail>) session.getAttribute("StudentProgramListDet");	%>

		<div id="frame">
			<div id="content">
				

				<form action="StudentProgramController" method="post">
					<table width="900" height="80" border="1" cellspacing="1">
						<tr height="60"></tr>
	
							<td align="center"><input type="text" name="car2" 
								value="car2" /></td>

						<%
						
							if (marksList != null) {

									for (int y = 0; y < marksList.size(); y++) {


						%>

						<tr>
							<td align="center" style="display:none;">
							<input type="text" name="studentID<%=y%>"
								value="<%=marksList.get(y).getStudentID()%>" /></td>

						
						
					
							<td align="center" style="display:none;">
							<input type="text" name="programID<%=y%>"
								value="<%=marksList.get(y).getProgramID()%>" /></td>

						
						
							<td align="center" style="display:none;">
							<input type="text" name="subjectID<%=y%>" 
								value="<%=marksList.get(y).getSubjectID()%>" /></td>
							
							
							<td align="center"><input type="text" name="studentName<%=y%>"
								value="<%=marksList.get(y).getStudentName()%>" /></td>

						
						
					
							<td class="unandpwd">Program :</td>
							<td align="center"><input type="text" name="programName<%=y%>"
								value="<%=marksList.get(y).getProgramName()%>" /></td>

						
						
							<td class="unandpwd">Subject :</td>
							<td align="center"><input type="text" name="subjectName<%=y%>" 
								value="<%=marksList.get(y).getSubjectName()%>" /></td>
							

						
							<td class="unandpwd" >Marks :</td>
							<td align="center" ><input type="text" name="lecturerMark<%=y%>" 
								value="<%=marksList.get(y).getFinalMark()%>" onkeypress="checkEdited(<%=y%>);"/></td>
					
							<td align="center" style="display:none;">
								<input type="text" name="isEdit<%=y%>" id="isEdit<%=y%>"value="0"  readonly/>
							</td>	


						</tr>

						<tr height="5" />
						
						
						<%
						
				
						String size2 = 		Integer.toString(marksList.size());
						session.setAttribute("updateLength", size2);
						session.setAttribute("actionType", "getStudentProgramDetail");
									}
								}
							else {%> Loading......<%
								
								
							}
						%>


						<tr height="60"></tr>
					
						
				
								
				<select  id="programSelect"  name="programSelect"  onclick="ChangeCarList();"> 
				 <!--  <option value="">--Program--</option>  --> 
				  <option value="1">Masters 2013 Batch 1</option>
				  <option value="2">Masters 2014 Batch 2</option>
				  <option value="3">Degree 2013 Batch 1</option> 
				  <option value="4">Degree 2014 Batch 2</option>
				  <option value="5">Diploma 2013 Batch 1</option>
				</select> 

				<select id="carmodel"></select> 
						
					</table>
					<input type="submit" name="updateButton" value="Update">
					
					


<script type="text/javascript">

function getXMLObject()  //XML OBJECT
{
   var xmlHttp = false;
   try {
     xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
   }
   catch (e) {
     try {
       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
     }
     catch (e2) {
       xmlHttp = false   // No Browser accepts the XMLHTTP Object then false
     
   }
   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
   }
   return xmlHttp;  // Mandatory Statement returning the ajax object created
}
}

var xmlhttp = new getXMLObject();   //xmlhttp holds the ajax object

function ajaxFunction() {
	//alert("inside ajaxFunc");
    var carList = document.getElementById("programSelect");
    var selectedProgram = carList.options[carList.selectedIndex].value;
  var getdate = new Date();  //Used to prevent caching during ajax call
  if(xmlhttp) {

	//  alert(selectedProgram);
    xmlhttp.open("GET","StudentProgramController?program="+selectedProgram,true);
    xmlhttp.onreadystatechange  = handleServerResponse;
    xmlhttp.setRequestHeader('Content-Type', 'application/text');
    xmlhttp.send("program=program");
  }
}

function handleServerResponse() {
   if (xmlhttp.readyState == 4) {
     if(xmlhttp.status == 200) {
     //  document.myForm.time.value=xmlhttp.responseText;  
     }
     else {
        alert("Error during AJAX call. Please try again");
     }
   }
}
</script>


<script>	
	
	var carsAndModels={};
	carsAndModels['VO']=['V70','XC60','XC90'];
	carsAndModels['VW']=['Golf','Polo','Scirocco','Touareg'];
	carsAndModels['BMW']=['M6','X5','Z3'];

	function ChangeCarList() {
	
		<%	session.setAttribute("actionType", "getSelectList"); 	%>
		
		ajaxFunction();
		
		<% LinkedList<String> selectList = (LinkedList<String>) session.getAttribute("SelectedList");	%>
	    var carList = document.getElementById("programSelect");
	    var modelList = document.getElementById("carmodel");
	    var selCar = carList.options[carList.selectedIndex].value;
	    
	    while (modelList.options.length) {
	        modelList.remove(0);
	    }
	    var cars = carsAndModels[selCar];
	    if (cars) {
	    	
	    	<%try{
	    	 int f = 0;

	    	 
	    	 %>
	  
	    	 var colArray = new Array();
	    	 var len = "<%=selectList.size()%>";
	    	 
	    	 <% for (int g=0; g<selectList.size(); g++) { %>
	    	 colArray[<%= g %>] = "<%= selectList.get(g).toString() %>"; 
	    	 <% } %>
	    	 
	    
			 for (var i = 0; i < len; i++) {

		  			var car = new Option(colArray[i],i);
			        modelList.options.add(car);
   
			 }
	        
	        <%}catch(Exception d){System.out.println(d.toString());}%>
	    }
	} 
</script>

				</form>
			
			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>
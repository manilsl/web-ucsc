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
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script src="autocompleter.js"></script>



<%-- <%LinkedList<StudentProgramDetail> marksList =null;%>  --%>
<!-- <script>
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
</script> -->

</head>
<%-- 		<%	String actionType = "getStudentProgramDetail";
			session.setAttribute("actionType", actionType);
		%> --%>

<body id="page1">
	<div id="maindiv">



		<div id="frame">
			<div id="content">
				<input type="text" id="search" name="search" class="search" />

				<form action="StudentProgramController" method="get">
					
								
						<select id="car"  name="car"  onclick="ChangeCarList();"> 
						  <option value="">-- Car --</option> 
						  <option value="VO">Volvo</option> 
						  <option value="VW">Volkswagen</option> 
						  <option value="Masters 2013 Batch 1">Masters 2013 Batch 1</option> 
						</select> 
				
						<select id="carmodel"></select> 
						<input type="text" name="car2" value="car2">
						
						<input type="submit" name="getSelectList" value="getSelectList">
				</form>
<%-- 			<script>	
	
			var carsAndModels={};
			carsAndModels['VO']=['V70','XC60','XC90'];
			carsAndModels['VW']=['Golf','Polo','Scirocco','Touareg'];
			carsAndModels['BMW']=['M6','X5','Z3'];
		
			function ChangeCarList() {
			
				<%	session.setAttribute("actionType", "getSelectList"); 	%>
				
				ajaxFunction();
				
				<% LinkedList<String> selectList = (LinkedList<String>) session.getAttribute("SelectedList");	%>
			    var carList = document.getElementById("car");
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
				
			    var carList = document.getElementById("car");
			    var selectedProgram = carList.options[carList.selectedIndex].value;
			  var getdate = new Date();  //Used to prevent caching during ajax call
			  if(xmlhttp) {
			
			    xmlhttp.open("GET","StudentProgramController",true);
			    xmlhttp.onreadystatechange  = handleServerResponse;
			    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			    xmlhttp.send(null);
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
			</script> --%>
				
			
			</div>
		</div>

		<footer>

		<p class="rf">©2014 WSO2</p>
		<div style="clear: both;"></div>
		</footer>
</body>
</html>
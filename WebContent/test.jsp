<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP and Servlet using AJAX</title>
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
   }
   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
   }
   return xmlHttp;  // Mandatory Statement returning the ajax object created
}

var xmlhttp = new getXMLObject();   //xmlhttp holds the ajax object

function ajaxFunction() {
  var getdate = new Date();  //Used to prevent caching during ajax call
  if(xmlhttp) {

    xmlhttp.open("GET","ControlServlet?gettime=gettime" ,true);
    xmlhttp.onreadystatechange  = handleServerResponse;
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(null);
  }
}

function handleServerResponse() {
   if (xmlhttp.readyState == 4) {
     if(xmlhttp.status == 200) {
       document.myForm.time.value=xmlhttp.responseText;  
     }
     else {
        alert("Error during AJAX call. Please try again");
     }
   }
}



var carsAndModels={};
carsAndModels['VO']=['V70','XC60','XC90'];
carsAndModels['VW']=['Golf','Polo','Scirocco','Touareg'];
carsAndModels['BMW']=['M6','X5','Z3'];

function ChangeCarList() {
    var carList = document.getElementById("car");
    var modelList = document.getElementById("carmodel");
    var selCar = carList.options[carList.selectedIndex].value;
    while (modelList.options.length) {
        modelList.remove(0);
    }
    var cars = carsAndModels[selCar];
    if (cars) {
        for (var i = 0; i < cars.length; i++) {
            var car = new Option(cars[i],i);
            modelList.options.add(car);
        }
    }
} 
</script>
</head>
<body onload="ChangeCarList();">
<form name="myForm">
Server Time:<input type="text" name="time" />
<br />
<input type="button" onClick="ajaxFunction()" value="Click"/>
<br />
</form>

<p>The data from servlet: ${data}</p>

<input type="text" id="overhere" value="before data"/>

<input type="text" id="overhere2" value="overhre2"/>



<select id="car" onchange="ChangeCarList();"> 
  <option value="">-- Car --</option> 
  <option value="VO">Volvo</option> 
  <option value="VW">Volkswagen</option> 
  <option value="BMW">BMW</option> 
</select> 

<select id="carmodel"></select> 


</body>
</head>
</html>
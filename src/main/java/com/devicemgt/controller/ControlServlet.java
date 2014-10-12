package com.devicemgt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ControlServlet")
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ControlServlet() {
		super();

	}

	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        
		response.setContentType("text/html");
		StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?").append(request.getQueryString());
        }
        String completeURL = requestURL.toString();
        
        System.out.println("completeURL " + completeURL);
        RequestDispatcher requestDispatcher = null;
        HttpSession session = request.getSession();
        String[] array = {"a","b"};
        session.setAttribute("array", array);
        
        
        session.setAttribute("data", "Pissu hadenawa");
        
        if(request.getParameter("gettime")!= null && !request.getParameter("gettime").toString().equals("")){ 
        PrintWriter out = response.getWriter();
        Date df = new Date();
        out.println(df.getTime());
        
       
        
        
        
        }
       // requestDispatcher = request.getRequestDispatcher("test.jsp");
	//	requestDispatcher.forward(request, response);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }
}


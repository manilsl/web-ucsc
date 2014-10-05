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

import com.devicemgt.dao.DeviceDAO;
import com.devicemgt.dao.DeviceRepoDAOImpl;
import com.devicemgt.dao.HttpAPICaller;
import com.devicemgt.model.Device;
import com.devicemgt.util.BackendConstants;
import com.devicemgt.util.FrontConstants;
import com.devicemgt.util.Rest;

/**
 * Servlet implementation class DeviceController
 */
@WebServlet("/DeviceController")
public class DeviceController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Device device;
	DeviceDAO deviceRepoDAO;
	HttpAPICaller httpAPICaller;
	boolean isValidated = true;
	String strName;
	String strDescription;
	String strStatus;
	String strType;
	String strOwner;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeviceController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("Hello from Servlet\n\n");

		String actionType = (String) request.getSession(false).getAttribute(
				"actionType");

		RequestDispatcher requestDispatcher = null;

		if (request.getParameter("getDvices") != null) {

			actionType = "getDvices";

		} else if (request.getParameter("getSearch") != null) {
			actionType = "getSearch";
		} else if (request.getParameter("deleteBtn") != null) {
			actionType = "deleteDevice";
		} else if (request.getParameter("editBtn") != null) {
			actionType = "editDevice";
		} else if (request.getParameter("updateBtn") != null) {
			actionType = "updateDevice";
		}

		if ((request.getParameter("addDevice") != null)
				|| (actionType.equals("updateDevice"))) {

			String strNoDescription = "No Description";
			strName = request.getParameter("dname");
			strDescription = request.getParameter("description");
			strStatus = request.getParameter("status");
			strType = request.getParameter("type");

			if (strName == null || strName.length() == 0) {
				isValidated = false;
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						FrontConstants.DNAME + " " + FrontConstants.REQUIRED);

			} else if (strDescription == null || strDescription.length() == 0) {
				strDescription = strNoDescription;
			} else if (strStatus == null || strStatus.length() == 0) {
				isValidated = false;
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						FrontConstants.STATUS + " " + FrontConstants.REQUIRED);
			} else if (strType == null || strType.length() == 0) {
				isValidated = false;
				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						FrontConstants.TYPE + " " + FrontConstants.REQUIRED);
			}

		}

		String strResponse = "";
		if (isValidated) {

			System.out.println("Action type : " + actionType);

			if (actionType.equals("getDvices")) {

				LinkedList<Device> deviceList = getDeviceFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);
				requestDispatcher = request
						.getRequestDispatcher("getdevice.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getSearch")) {

//				String strSelectId = request.getParameter("dID");
				String strSelectName = request.getParameter("dNAME");
				strSelectName = strSelectName.replace(" ", "%20");

				String options = null;
				boolean firstPara = false;
//				if (!strSelectId.equals("Not Selected")) {
//
//					options = "?deviceId=" + strSelectId;
//					firstPara = true;
//
//				}
				if (!strSelectName.equals("")) {

					if (firstPara == false) {
						options = "?deviceName=" + strSelectName;
						firstPara = true;
					} else {
						options = options + "&deviceName=" + strSelectName;
					}

				}

				String restURL = BackendConstants.SERVICEURL +"/device/getdevicesdetail";

				if (firstPara) {
					restURL = restURL + options;
				}

				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				deviceRepoDAO = new DeviceRepoDAOImpl();
				LinkedList<Device> deviceList = deviceRepoDAO.getDeviceList(
						line, "Device");

				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);

				requestDispatcher = request
						.getRequestDispatcher("getdevice.jsp");
				requestDispatcher.forward(request, response);

				out.print(strResponse);

			}

			else if (actionType.equals("addDevice")) {

				device = new Device();
				device.setName(strName);
				device.setDescription(strDescription);
				device.setStatusId(Integer.parseInt(strStatus));
				device.setTypeId(Integer.parseInt(strType));

				String restURL = BackendConstants.SERVICEURL +"/device/adddevice";

				deviceRepoDAO = new DeviceRepoDAOImpl();
				strResponse = deviceRepoDAO.addDevice(device, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				requestDispatcher = request
						.getRequestDispatcher("add_device.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("getDvicesOnLoad")) {

				LinkedList<Device> deviceList = getDeviceFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_device.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("deleteDevice")) {

				String strDltRadio = request.getParameter("deleteDevice");
				System.out.println(strDltRadio);

				String restURL = BackendConstants.SERVICEURL +"/device/deletedevice/"
						+ strDltRadio;

				deviceRepoDAO = new DeviceRepoDAOImpl();
				strResponse = deviceRepoDAO.deleteDevice(restURL);

				LinkedList<Device> deviceList = getDeviceFromDB(request,
						response);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);
				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_device.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("editDevice")) {

				String strBtnEdit = request.getParameter("editDevice");
				System.out.println(strBtnEdit);

				String restURL = BackendConstants.SERVICEURL +"/device/getdevices?deviceId="
						+ strBtnEdit;
				httpAPICaller = new HttpAPICaller();
				String line = httpAPICaller.getRequest(restURL);

				deviceRepoDAO = new DeviceRepoDAOImpl();
				LinkedList<Device> deviceList = deviceRepoDAO.getDeviceList(
						line, "Device");

				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);

				requestDispatcher = request
						.getRequestDispatcher("edit_device.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("updateDevice")) {

				String strID = request.getParameter("dID");
				System.out.println(strID);

				device = new Device();
				device.setName(strName);
				device.setDescription(strDescription);
				device.setStatusId(Integer.parseInt(strStatus));
				device.setTypeId(Integer.parseInt(strType));

				System.out.println(device);

				String restURL = BackendConstants.SERVICEURL +"/device/updatedevice/"
						+ strID;

				deviceRepoDAO = new DeviceRepoDAOImpl();
				strResponse = deviceRepoDAO.updateDevice(device, restURL);

				request.setAttribute(BackendConstants.ERROR_MESSAGE,
						strResponse);

				LinkedList<Device> deviceList = getDeviceFromDB(request,
						response);
				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);

				requestDispatcher = request
						.getRequestDispatcher("updateordelete_device.jsp");
				requestDispatcher.forward(request, response);

			} else if (actionType.equals("loadDevice")) {

				LinkedList<Device> deviceList = getDeviceFromDB(request,
						response);

				HttpSession session = request.getSession();

				session.setAttribute("DeviceList", deviceList);

				requestDispatcher = request
						.getRequestDispatcher("get_device.jsp");
				requestDispatcher.forward(request, response);

			}

		} else {
			requestDispatcher = request.getRequestDispatcher("add_device.jsp");
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

	public LinkedList<Device> getDeviceFromDB(HttpServletRequest request,
			HttpServletResponse response) {

		LinkedList<Device> deviceList = new LinkedList<Device>();
		try {

			String strURL = BackendConstants.SERVICEURL +"/device/getdevices";
			httpAPICaller = new HttpAPICaller();
			String line = httpAPICaller.getRequest(strURL);

			System.out.println(line);
			deviceRepoDAO = new DeviceRepoDAOImpl();
			deviceList = deviceRepoDAO.getDeviceList(line, "Device");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			return deviceList;
		}

	}

}
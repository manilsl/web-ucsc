package com.HelloApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.devicemgt.model.Device;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloServlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HelloServlet() {
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
		PrintWriter out = response.getWriter();
		out.print("Hello from Servlet");

		HttpClient client = new DefaultHttpClient();
		HttpGet httpRequest = new HttpGet(
				"http://127.0.0.1:9763/DeviceMgt_Service-1.0.0/services/device_mgt_services/getDevice");
		HttpResponse httpResponse = client.execute(httpRequest);
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				httpResponse.getEntity().getContent()));

		String line = "";
		line = rd.readLine();
		/*
		 * while ((line = rd.readLine()) != null) { System.out.println(line);
		 * out.print(line); out.print("\n");
		 * 
		 * System.out.println("\n"); }
		 */
		// line

		LinkedList<Device> deviceList = getDeviceList(line, "device");

		// System.out.println(line);

		// String line = "";
		//
		// while ((line = rd.readLine()) != null) {
		// System.out.println(line);
		// out.print(line);
		// out.print("\n");
		//
		// System.out.println("\n");
		// }

		try {

			// JAXBContext jaxbContext = JAXBContext.newInstance(Device.class);
			//
			// Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			//
			// Device device = (Device) jaxbUnmarshaller.unmarshal(httpResponse
			// .getEntity().getContent());
			//
			// out.print("\n");
			// // out.print(device.getListOfDevices().get(0));
			// out.print("\n");
			// out.print(device);

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

	@SuppressWarnings({ "finally", "unused" })
	private LinkedList<Device> getDeviceList(String body, String rootElement) {
		LinkedList<Device> deviceList = new LinkedList<Device>();
		try {
			JSONObject jsonObject = null;

			if (isValidJSON(body)) {

				jsonObject = new JSONObject(body);
			} else {
				jsonObject = new JSONObject();
				jsonObject.put(rootElement, body);

			}

			for (int x = 0; x < jsonObject.getJSONArray(rootElement).length(); x++) {
				JSONObject jObject = new JSONObject();
				jObject.put(rootElement, jsonObject.getJSONArray(rootElement)
						.get(x));

				Device tempDevice = new Device();
				tempDevice.setId(Integer.parseInt(jObject
						.getJSONObject(rootElement).get("id").toString()));
				tempDevice
						.setStatusId(Integer.parseInt(jObject
								.getJSONObject(rootElement).get("statusId")
								.toString()));
				tempDevice.setTypeId(Integer.parseInt(jObject
						.getJSONObject(rootElement).get("typeId").toString()));
				tempDevice.setDescription(jObject.getJSONObject(rootElement)
						.get("description").toString());
				tempDevice.setName(jObject.getJSONObject(rootElement)
						.get("name").toString());
				deviceList.add(tempDevice);
			}

			/*
			 * for (int y = 0; y < deviceList.size(); y++) {
			 * System.out.println("Number " + y);
			 * System.out.println(deviceList.get(y).getId());
			 * System.out.println(deviceList.get(y).getStatusId());
			 * System.out.println(deviceList.get(y).getTypeId());
			 * System.out.println(deviceList.get(y).getDescription());
			 * System.out.println(deviceList.get(y).getName());
			 * System.out.println();
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			return deviceList;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private boolean isValidJSON(String json) {

		try {
			new JSONObject(json);
			return true;
		} catch (JSONException ex) {
			return false;
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
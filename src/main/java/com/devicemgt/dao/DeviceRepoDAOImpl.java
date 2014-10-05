package com.devicemgt.dao;

import java.util.LinkedList;

import org.json.JSONObject;

import com.devicemgt.model.Device;

public class DeviceRepoDAOImpl implements DeviceDAO{
	
	HttpAPICaller httpAPICaller;

	public String addDevice(Device arg0, String arg1) {
		// String payloadBoby = "<device> " + "<id>" + device.getId() + "</id> "
				// + "<name>" + device.getName() + "</name> " + "<description>"
				// + device.getDescription() + "</description> " + "<statusId>"
				// + device.getStatusId() + "</statusId> " + "<typeId>"
				// + device.getTypeId() + "</typeId> " + "</device>";
				String payloadBoby = "{Device: {deviceDescription: "
						+ arg0.getDescription() + "," + "DeviceName: "
						+ arg0.getName() + "," + "statusId: " + arg0.getStatusId()
						+ "," + "typeId: " + arg0.getTypeId() + "}}";

				httpAPICaller = new HttpAPICaller();
				String strResponse = httpAPICaller.postRequest(arg1, payloadBoby);

				return strResponse;
	}

	
	@SuppressWarnings("finally")
	public LinkedList<Device> getDeviceList(String jsonBody, String rootElement) {

		System.out.println(jsonBody);
		LinkedList<Device> deviceList = new LinkedList<Device>();
		try {
			JSONObject jsonObject = null;

			if (isValidJSON(jsonBody)) {

				jsonObject = new JSONObject(jsonBody);
			} else {
				jsonObject = new JSONObject();
				jsonObject.put(rootElement, jsonBody);

			}

			for (int x = 0; x < jsonObject.getJSONArray(rootElement).length(); x++) {
				JSONObject jObject = new JSONObject();
				jObject.put(rootElement, jsonObject.getJSONArray(rootElement)
						.get(x));

				Device tempDevice = new Device();
				// tempDevice.setId(Integer.parseInt(jObject
				// .getJSONObject(rootElement).get("id").toString()));
				// tempDevice
				// .setStatusId(Integer.parseInt(jObject
				// .getJSONObject(rootElement).get("statusId")
				// .toString()));
				// tempDevice.setTypeId(Integer.parseInt(jObject
				// .getJSONObject(rootElement).get("typeId").toString()));
				// tempDevice.setDescription(jObject.getJSONObject(rootElement)
				// .get("description").toString());
				// tempDevice.setName(jObject.getJSONObject(rootElement)
				// .get("name").toString());

				tempDevice.setDescription(jObject.getJSONObject(rootElement)
						.get("deviceDescription").toString());
				tempDevice
						.setId(Integer.parseInt(jObject
								.getJSONObject(rootElement).get("deviceId")
								.toString()));
				tempDevice.setName(jObject.getJSONObject(rootElement)
						.get("DeviceName").toString());
				tempDevice
						.setStatusId(Integer.parseInt(jObject
								.getJSONObject(rootElement).get("statusId")
								.toString()));
				tempDevice.setTypeId(Integer.parseInt(jObject
						.getJSONObject(rootElement).get("typeId").toString()));

				deviceList.add(tempDevice);
			}

			// for (int y = 0; y < deviceList.size(); y++) {
			// System.out.println("Number " + y);
			// System.out.println(deviceList.get(y).getId());
			// System.out.println(deviceList.get(y).getStatusId());
			// System.out.println(deviceList.get(y).getTypeId());
			// System.out.println(deviceList.get(y).getDescription());
			// System.out.println(deviceList.get(y).getName());
			// System.out.println();
			//
			// }

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

	
		return deviceList;}}

	
	public String deleteDevice(String restURL) {
		
		httpAPICaller = new HttpAPICaller();
		String strResponse=httpAPICaller.deleteRequest(restURL);
		
		return strResponse;
	}

	
	public boolean isValidJSON(String json) {
		try {
			new JSONObject(json);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	
	public String updateDevice(Device arg0, String arg1) {
		
		String payloadBoby = "{Device: {deviceDescription: "
				+ arg0.getDescription() + "," + "DeviceName: "
				+ arg0.getName() + "," + "statusId: " + arg0.getStatusId()
				+ "," + "typdeId: " + arg0.getTypeId() + "}}";

		httpAPICaller = new HttpAPICaller();
		String strResponse = httpAPICaller.putRequest(arg1, payloadBoby);

		return strResponse;
	}
	
	

}

package com.devicemgt.dao;

import java.util.LinkedList;

import com.devicemgt.model.Device;

public interface DeviceDAO {
	
	public String deleteDevice(String restURL);

	public boolean isValidJSON(String json);

	public LinkedList<Device> getDeviceList(String jsonBody, String rootElement);

	public String addDevice(Device device, String restURL);

	public String updateDevice(Device device, String restURL);

}

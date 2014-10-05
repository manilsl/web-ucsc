package com.devicemgt.dao;

import java.util.LinkedList;

import com.devicemgt.model.DeviceStatus;

public interface DeviceStatusDao {   
   
	public String deleteDeviceStatus(String restURL);
	public boolean isValidJSON(String json);
	public LinkedList<DeviceStatus> getDeviceStatus(String jsonBody, String rootElement);
	public String addDeviceStatus(DeviceStatus deviceStatus, String restURL);

}

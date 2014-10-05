package com.devicemgt.dao;

import java.util.LinkedList;

import com.devicemgt.model.Device;
import com.devicemgt.model.DeviceType;

public interface DeviceTypeDAO {
	
	
	public boolean isValidJSON(String json);

	public LinkedList<DeviceType> getDeviceTypeList(String jsonBody, String rootElement);

	public String addDeviceType(DeviceType deviceType, String restURL);

}

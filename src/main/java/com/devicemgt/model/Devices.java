package com.devicemgt.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Devices {

	private ArrayList<Device> listOfDevice;

	public ArrayList<Device> getListOfDevices() {
		return listOfDevice;
	}

	@XmlElement(name = "device")
	public void setListOfDevices(ArrayList<Device> listOfDevice) {

		this.listOfDevice = listOfDevice;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "" + listOfDevice.toString();
	}

}

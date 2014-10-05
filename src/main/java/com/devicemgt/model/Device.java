package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Device")
//@XmlType(propOrder = { "intId", "strName", "strDescription", "intStatusId",
//		"intTypeId" })
public class Device {

	private int intId;
	private String strName;
	private String strDescription;
	private int intStatusId;
	private int intTypeId;


	public int getId() {
		return intId;
	}

	@XmlElement(name = "id")
	public void setId(int intId) {
		this.intId = intId;
	}
	
	public String getName() {
		return strName;
	}

	@XmlElement(name = "name")
	public void setName(String strName) {
		this.strName = strName;
	}

	public String getDescription() {
		return strDescription;
	}

	@XmlElement(name = "description")
	public void setDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public int getStatusId() {
		return intStatusId;
	}

	@XmlElement(name = "statusId")
	public void setStatusId(int intStatusId) {
		this.intStatusId = intStatusId;
	}

	public int getTypeId() {
		return intTypeId;
	}

	@XmlElement(name = "typeId")
	public void setTypeId(int intTypeId) {
		this.intTypeId = intTypeId;
	}

	@Override
	public String toString() {

		return strName + ", " + intId + ", " + strDescription + ", "
				+ intStatusId + ", " + intTypeId;
	}

}

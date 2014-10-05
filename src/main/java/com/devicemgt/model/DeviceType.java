package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="DeviceType")
public class DeviceType
{
    
    String deviceTypeId;
    String deviceTypeName;
    String deviceTypeDescription;

    
    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    @XmlElement(name="deviceTypeId")
    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    
    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    @XmlElement(name="deviceTypeName")
    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }

  
    public String getDeviceTypeDescription() {
        return deviceTypeDescription;
    }

    @XmlElement(name="deviceTypeDescription")
    public void setDeviceTypeDescription(String deviceTypeDescription) {
     this.deviceTypeDescription=deviceTypeDescription;
    }
}
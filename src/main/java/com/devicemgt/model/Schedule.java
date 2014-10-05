package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by manilsl on 7/10/14.
 */
@XmlRootElement(name="Schedule")
public class Schedule {

    String scheduleID;
    String year;
    String batch;

    @XmlElement(name="scheduleID")
    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }
    
    @XmlElement(name="year")
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    
    @XmlElement(name="batch")
    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}

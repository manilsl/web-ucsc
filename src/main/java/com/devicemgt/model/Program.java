package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by manilsl on 7/10/14.
 */
@XmlRootElement(name="Program")
public class Program {

    String programID;
    String scheduleID;
    String courseID;

    @XmlElement(name="courseID")
    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    
    @XmlElement(name="scheduleID")
    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    @XmlElement(name="programID")
    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }
}

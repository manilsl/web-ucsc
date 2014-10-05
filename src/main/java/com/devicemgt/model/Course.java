package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by manilsl on 7/10/14.
 */
@XmlRootElement(name="Course")
public class Course {

    String courseDisplayID;
    String courseName;
    String courseID;

    @XmlElement(name="courseDisplayID")
    public String getCourseDisplayID() {
        return courseDisplayID;
    }

    public void setCourseDisplayID(String courseDisplayID) {
        this.courseDisplayID = courseDisplayID;
    }



    @XmlElement(name="courseID")
    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @XmlElement(name="courseName")
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}

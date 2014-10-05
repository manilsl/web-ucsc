package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by manilsl on 7/10/14.
 */
@XmlRootElement(name="StudentProgram")
public class StudentProgram {

    String studentID;
    String programID;
    String subjectID;
    double finalMark;

    @XmlElement(name="studentID")
    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @XmlElement(name="programID")
    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) {
        this.programID = programID;
    }

    @XmlElement(name="subjectID")
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    @XmlElement(name="finalMark")
    public double getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(double finalMark) {
        this.finalMark = finalMark;
    }
}

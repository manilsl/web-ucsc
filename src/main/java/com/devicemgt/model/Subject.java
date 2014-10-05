package com.devicemgt.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by manilsl on 7/10/14.
 */
@XmlRootElement(name="Subject")
public class Subject {

    String subjectID;
    String subjectName;

    @XmlElement(name="subjectID")
    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    @XmlElement(name="subjectName")
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}

package com.devicemgt.dao;

import com.devicemgt.model.Subject;
import java.util.LinkedList;

public interface SubjectDao {

	public String deleteSubject(String restURL) throws Exception;
    public LinkedList<Subject> getSubjectList(String jsonBody, String rootElement);
    public String addSubject(Subject subject, String restURL);
    public String updateSubject(Subject subject,  String restURL);

}

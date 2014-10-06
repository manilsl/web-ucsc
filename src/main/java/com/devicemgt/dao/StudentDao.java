package com.devicemgt.dao;

import com.devicemgt.model.Student;
import java.util.LinkedList;

public interface StudentDao {

	public String deleteStudent(String restURL) throws Exception;
    public LinkedList<Student> getStudentList(String jsonBody, String rootElement);
    public String addStudent(Student student, String restURL);
    public String updateStudent(Student student,  String restURL);
}

package com.devicemgt.dao;

import com.devicemgt.model.StudentProgram;
import java.util.LinkedList;

public interface StudentProgramDao {

	public String deleteStudentProgram(String restURL) throws Exception;
    public LinkedList<StudentProgram> getStudentProgramList(String jsonBody, String rootElement);
    public String addStudentProgram(StudentProgram studentProgram, String restURL);
    public String updateStudentProgram(StudentProgram studentProgram,  String restURL);

}

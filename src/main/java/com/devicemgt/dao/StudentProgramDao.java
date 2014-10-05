package com.devicemgt.dao;

import com.devicemgt.model.StudentProgram;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

public interface StudentProgramDao {

    public String deleteStudentProgram(UriInfo id) throws Exception;
    public LinkedList<StudentProgram> getStudentProgram(UriInfo parameters);
    public String addStudentProgram(StudentProgram studentProgram);
    public String editStudentProgram(StudentProgram studentProgram, UriInfo id);

}

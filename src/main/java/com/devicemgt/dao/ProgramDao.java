package com.devicemgt.dao;


import com.devicemgt.model.Program;


import javax.ws.rs.core.UriInfo;


import java.util.LinkedList;

public interface ProgramDao {

    public String deleteProgram(String id) throws Exception;
    public LinkedList<Program> getProgram(UriInfo parameters);
    public String addProgram(Program program);
    public String editProgram(Program program, String id);

}

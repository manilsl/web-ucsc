package com.devicemgt.dao;


import com.devicemgt.model.Program;





import javax.ws.rs.core.UriInfo;





import java.util.LinkedList;

public interface ProgramDao {

    public String deleteProgram(String restURL) throws Exception;
    public LinkedList<Program> getProgramList(String jsonBody, String rootElement);
    public String addProgram(Program program, String restURL);
    public String updateProgram(Program program,  String restURL);

}

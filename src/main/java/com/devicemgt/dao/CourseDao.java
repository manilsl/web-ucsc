package com.devicemgt.dao;


import com.devicemgt.model.Course;

import javax.ws.rs.core.UriInfo;






import java.util.LinkedList;

public interface CourseDao {

    public String deleteCourse(String restURL) throws Exception;
    public LinkedList<Course> getCourseList(String jsonBody, String rootElement);
    public String addCourse(Course course ,String restURL);
    public String updateCourse(Course course, String restURL);

}

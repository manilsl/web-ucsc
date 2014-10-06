package com.devicemgt.dao;

import com.devicemgt.model.Schedule;
import java.util.LinkedList;

public interface ScheduleDao {

    public String deleteSchedule(String restURL) throws Exception;
    public LinkedList<Schedule> getScheduleList(String jsonBody, String rootElement);
    public String addSchedule(Schedule schedule, String restURL);
    public String updateSchedule(Schedule schedule,  String restURL);

}

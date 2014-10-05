package com.devicemgt.dao;

import java.util.LinkedList;

import com.devicemgt.model.Device;
import com.devicemgt.model.User;

public interface UserDAO {
	
	public String addUser(User user, String restURL);
	public LinkedList<User> getUserList(String jsonBody, String rootElement);
	public String deleteUser(String restURL);
	public String updateUser(User user, String restURL);

}

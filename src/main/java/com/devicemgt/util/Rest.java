package com.devicemgt.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Rest {
	

	public static String getProperty() {
		String dataSource = null;
		try {
		Properties properties = new Properties();
		properties.load(Rest.class
		.getResourceAsStream("api.properties"));
		dataSource = properties.getProperty("api");
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		return dataSource;
		}

}

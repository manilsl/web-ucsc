package com.devicemgt.dao;

import java.util.LinkedList;

import com.devicemgt.model.TransactionStatus;

public interface TransactionStatusDao {   
   
	public String deleteTransactionStatus(String restURL);
	public boolean isValidJSON(String json);
	public LinkedList<TransactionStatus> getTransactionStatus(String jsonBody, String rootElement);
	public String addDevice(TransactionStatus transactionStatus, String restURL);

}

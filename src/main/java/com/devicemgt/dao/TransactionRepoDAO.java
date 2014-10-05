package com.devicemgt.dao;

import java.util.LinkedList;

import com.devicemgt.model.Transaction;

public interface TransactionRepoDAO {

	public LinkedList<Transaction> getTransactionList(String jsonBody, String rootElement);

	
	
	public String addTransaction(Transaction transaction, String restURL);

}

package com.cbsapi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cbsapi.model.Account;
import com.cbsapi.model.stmt;
import com.cbsapi.model.txn;
@Service
public interface LCBSService {

	Account createAccount(Account account);
	List<Account> listAccounts();
	List<Account> createAccounts(List<Account> accounts);
	Account getAccountById(int id);
	Account updateAccount(Account account);
	String deleteAccountById(int id);
	//========end for account=================
	
	txn createTxn(txn txnobj);
	List<txn> listTxns();
	 List<txn> createTxns(List<txn> txnobjs);
	txn getTxnById(int id);
	txn updateTxn(txn txnobj);
	String deleteTxnById(int id);
	//==========end of txn entity==============
	stmt createStmt(stmt stmtobj);
	List<stmt> findStmtByAccount(String accountNumber);
}

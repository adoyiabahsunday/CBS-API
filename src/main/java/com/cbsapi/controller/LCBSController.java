package com.cbsapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cbsapi.model.Account;
import com.cbsapi.model.stmt;
import com.cbsapi.model.txn;
import com.cbsapi.service.LCBSService;

@RestController
public class LCBSController {

	@Autowired
	private LCBSService LCBSServiceobj;

	//map method to create account the is is the account number in this case
	@PostMapping("/createAcct")
	public Account createAcct(@RequestBody Account account) {

		System.out.println();
		return LCBSServiceobj.createAccount(account);
	}

	//map method to list all accounts created
	@GetMapping("/accounts")
	public List<Account> getAccounts() {

		return LCBSServiceobj.listAccounts();
	}

	//map method to create multiple accounts 
	@PostMapping("/createAccts")
	public List<Account> createAccts(@RequestBody List<Account> accounts) {

		return LCBSServiceobj.createAccounts(accounts);
	}

	//map method to view account by id
	@GetMapping("/account/{id}")
	public Account getAcctById(@PathVariable int id) {

		return LCBSServiceobj.getAccountById(id);
	}

	//map method to update account info
	@PutMapping("/updateAccount")
	public Account updateAccount(@RequestBody Account account) {

		return LCBSServiceobj.updateAccount(account);
	}

	//map method to delete account
	@DeleteMapping("/account/{id}")
	public String deleteAccount(@PathVariable int id) {

		return LCBSServiceobj.deleteAccountById(id);

	}
	//================end of account entity===================
	
	
	
	@PostMapping("/createTxn")
	public txn createTxn(@RequestBody txn txnobj) {

		
		return LCBSServiceobj.createTxn(txnobj);
	}

	@GetMapping("/getTxns")
	public List<txn> getTxns() {

		return LCBSServiceobj.listTxns();
	}

	@PostMapping("/createTxns")
	public List<txn> createTxns(@RequestBody List<txn> txnobjs) {

		return LCBSServiceobj.createTxns(txnobjs);
	}

	@GetMapping("/getTxn/{id}")
	public txn getTxnById(@PathVariable int id) {

		return LCBSServiceobj.getTxnById(id);
	}

	@PutMapping("/updateTxn")
	public txn updateTxn(@RequestBody txn txnobj) {

		return LCBSServiceobj.updateTxn(txnobj);
	}

	@DeleteMapping("/deleteTxn/{id}")
	public String deleteTxn(@PathVariable int id) {

		return LCBSServiceobj.deleteTxnById(id);

	}
	
	//map method to list statementEntry
		@GetMapping("/statementEntry/{account}")
		public List<stmt> getStatemententry(@PathVariable String account) {

			return LCBSServiceobj.findStmtByAccount(account);
		}
}

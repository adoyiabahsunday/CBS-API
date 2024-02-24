package com.cbsapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbsapi.model.Account;
import com.cbsapi.model.stmt;
import com.cbsapi.model.txn;
import com.cbsapi.repository.AccountRepository;
import com.cbsapi.repository.stmtRepository;
import com.cbsapi.repository.txnRepository;

@Service
public class LCBSServiceImpl implements LCBSService {

	@Autowired
	private AccountRepository acctrepo;

	@Autowired
	private txnRepository txnrepo;
	
	@Autowired
	private stmtRepository stmtrepo;

	@Override
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		return acctrepo.save(account);
	}

	@Override
	public List<Account> listAccounts() {
		// TODO Auto-generated method stub
		return acctrepo.findAll();
	}

	@Override
	public List<Account> createAccounts(List<Account> accounts) {
		return acctrepo.saveAll(accounts);
	}

	@Override
	public Account getAccountById(int id) {
		return acctrepo.findById(id).orElse(null);
	}

	@Override
	public Account updateAccount(Account account) {
		Account oldRec = null;
		Optional<Account> optionalaccount = acctrepo.findById(account.getId());
		if (optionalaccount.isPresent()) {
			oldRec = optionalaccount.get();

			oldRec.setCustomer(account.getCustomer());
			oldRec.setCategory(account.getCategory());
			oldRec.setAccountTitle(account.getAccountTitle());
			oldRec.setPositionType(account.getPositionType());
			oldRec.setCurrency(account.getCurrency());
			oldRec.setCurrencyMarket(account.getCurrencyMarket());
			oldRec.setAccountOfficer(account.getAccountOfficer());
			oldRec.setConditionGroup(account.getConditionGroup());
			oldRec.setOpenActualBalance(account.getOpenActualBalance());
			oldRec.setOpenClearedBalance(account.getOpenClearedBalance());
			oldRec.setOnlineActualBalance(account.getOnlineActualBalance());
			oldRec.setOnlineClearedBalance(account.getOnlineClearedBalance());
			oldRec.setWorkingBalance(account.getWorkingBalance());
			oldRec.setDateLastCrCust(account.getDateLastCrCust());
			oldRec.setAmntLastCrCust(account.getAmntLastCrCust());
			oldRec.setTranLastCrCust(account.getTranLastCrCust());
			oldRec.setPassbook(account.getPassbook());
			oldRec.setOpeningDate(account.getOpeningDate());
			oldRec.setOpenCategory(account.getOpenCategory());
			oldRec.setChargeCurrency(account.getChargeCurrency());
			oldRec.setChargeMarket(account.getChargeMarket());
			oldRec.setInterestCurrency(account.getInterestCurrency());
			oldRec.setInterestMarket(account.getInterestMarket());
			oldRec.setAltAcct(account.getAltAcct());
			oldRec.setHvtFlag(account.getHvtFlag());
			oldRec.setDateLastUpdate(account.getDateLastUpdate());
			oldRec.setBranchCode(account.getBranchCode());
			acctrepo.save(oldRec);

		} else {

			return new Account();
		}
		return oldRec;
	}

	@Override
	public String deleteAccountById(int id) {
		acctrepo.deleteById(id);
		return id + " was deleted successfully";
	}
//=====================End of account entity==================================

	@Override
	public txn createTxn(txn txnobj) {
		
		//this section generate ramdom id for statement table
		Random rand = new Random();
		double stmtDRId = rand.nextDouble();
        double stmtCRId = stmtDRId+1;
        txnobj.setStmtNumberDR(String.valueOf(stmtDRId));
        txnobj.setStmtNumberCR(String.valueOf(stmtCRId));
        //=======end===============
		
long drAcct = txnobj.getDebitAccountNumber();
long crAcct = txnobj.getCreditAccountNumber();
Account accountDr = this.getAccountById((int)drAcct);
Account accountCr = this.getAccountById((int)crAcct);


if(accountDr.getWorkingBalance() < txnobj.getDebitAmount() ) {
	txnobj.setMessage("Insufficient Funds");
	return txnobj;
}

		//return txnrepo.save(txnobj);
		txn txnrtn = txnrepo.save(txnobj);
		 accountDr.setWorkingBalance(accountDr.getWorkingBalance()-txnobj.getDebitAmount());
		 this.updateAccount(accountDr);//This update working balance of debit account
		 accountCr.setWorkingBalance(accountCr.getWorkingBalance()+txnobj.getDebitAmount());
		 this.updateAccount(accountCr);//This update working balance of credit account
		 
		 //=========this section update statement table=================================
		stmt stmtDR= new stmt();
		
		
		stmtDR.setId(String.valueOf(stmtDRId));
		stmtDR.setAccountNumber(String.valueOf(drAcct));
		stmtDR.setBranchCode(txnobj.getBranchCode());
		stmtDR.setAmountLcy(0-txnobj.getDebitAmount());
		stmtDR.setTransactionCode(txnobj.getTransactionType());
		stmtDR.setCustomerId(txnobj.getChargedCustomer());
		stmtDR.setAccountOfficer(accountDr.getAccountOfficer());
		stmtDR.setProductCategory(accountDr.getCategory());
		stmtDR.setValueDate(txnobj.getDebitValueDate());
		stmtDR.setCurrency(txnobj.getDebitCurrency());
		stmtDR.setTransReference(String.valueOf(txnrtn.getId()));
		stmtDR.setType("DEBIT");
		this.createStmt(stmtDR);
		
		//for stmtCr
		
		stmt stmtCR= new stmt();
		stmtCR.setId(String.valueOf(stmtCRId));
		stmtCR.setAccountNumber(String.valueOf(crAcct));
		stmtCR.setBranchCode(txnobj.getBranchCode());
		stmtCR.setAmountLcy(txnobj.getDebitAmount());
		stmtCR.setTransactionCode(txnobj.getTransactionType());
		stmtCR.setCustomerId(txnobj.getChargedCustomer());
		stmtCR.setAccountOfficer(accountCr.getAccountOfficer());
		stmtCR.setProductCategory(accountCr.getCategory());
		stmtCR.setValueDate(txnobj.getCreditValueDate());
		stmtCR.setCurrency(txnobj.getCreditCurrency());
		stmtCR.setTransReference(String.valueOf(txnrtn.getId()));
		stmtCR.setType("CREDIT");
		this.createStmt(stmtCR);
		
		 //=========end=================================
		 return txnrtn;	
	}

	@Override
	public List<txn> listTxns() { 

		return txnrepo.findAll();
	}

	@Override
	public List<txn> createTxns(List<txn> txnobjs) {
		return txnrepo.saveAll(txnobjs);
	}

	@Override
	public txn getTxnById(int id) {
		return txnrepo.findById(id).orElse(null);
	}

	@Override
	public txn updateTxn(txn txnobj) {
		txn oldRec = null;
		Optional<txn> optionaltxn = txnrepo.findById(txnobj.getId());
		if (optionaltxn.isPresent()) {
			oldRec = optionaltxn.get();

			oldRec.setTransactionType(txnobj.getTransactionType());
			oldRec.setDebitAccountNumber(txnobj.getDebitAccountNumber());
			oldRec.setCurrencyMarketDr(txnobj.getCurrencyMarketDr());
			oldRec.setDebitCurrency(txnobj.getDebitCurrency());
			oldRec.setDebitAmount(txnobj.getDebitAmount());
			oldRec.setDebitValueDate(txnobj.getDebitValueDate());
			oldRec.setCreditAccountNumber(txnobj.getCreditAccountNumber());
			oldRec.setCurrencyMarketCr(txnobj.getCurrencyMarketCr());
			oldRec.setCreditCurrency(txnobj.getCreditCurrency());
			oldRec.setCreditValueDate(txnobj.getCreditValueDate());
			oldRec.setTreasuryRate(txnobj.getTreasuryRate());
			oldRec.setProcessingDate(txnobj.getProcessingDate());
			oldRec.setChargeCode(txnobj.getChargeCode());
			oldRec.setBaseCurrency(txnobj.getBaseCurrency());
			oldRec.setProfitCentreCust(txnobj.getProfitCentreCust());
			oldRec.setAmountDebited(txnobj.getAmountDebited());
			oldRec.setAmountCredited(txnobj.getAmountCredited());
			oldRec.setCustomerRate(txnobj.getCustomerRate());
			oldRec.setCreditBranchCode(txnobj.getCreditBranchCode());
			oldRec.setDebitBranchCode(txnobj.getDebitBranchCode());
			oldRec.setChargedCustomer(txnobj.getChargedCustomer());
			oldRec.setStmtNumberDR(txnobj.getStmtNumberDR());
			oldRec.setStmtNumberCR(txnobj.getStmtNumberCR());
			oldRec.setBranchCode(txnobj.getBranchCode());
			txnrepo.save(oldRec);

		} else {

			return new txn();
		}
		return oldRec;
	}

	@Override
	public String deleteTxnById(int id) {
		txnrepo.deleteById(id);
		return id + " was deleted successfully";
	}

	@Override
	public stmt createStmt(stmt stmtobj) { 
		return stmtrepo.save(stmtobj);
	}
	
	@Override
	public List<stmt> findStmtByAccount(String accountNumber) {
		
		 return stmtrepo.findByAccountNumber(accountNumber);
	}
}

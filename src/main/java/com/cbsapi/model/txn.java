package com.cbsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class txn {
	@Id
	@GeneratedValue
	private int id;
	private String transactionType;
	private long debitAccountNumber;
	private String currencyMarketDr;
	private String debitCurrency;
	private double debitAmount;
	private double creditAmount;
	private String debitValueDate;
	private long creditAccountNumber;
	private String currencyMarketCr;
	private String creditCurrency;
	private String creditValueDate;
	private double treasuryRate;
	private String processingDate;
	private String chargeCode;
	private String baseCurrency;
	private String profitCentreCust;
	private double amountDebited;
	private double amountCredited;
	private double customerRate;
	private String creditBranchCode;
	private String debitBranchCode;
	private String chargedCustomer;
	private String stmtNumberDR;
	private String stmtNumberCR;
	private String branchCode;
	private String message;

}

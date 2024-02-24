package com.cbsapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data 

public class Account { 
@Id
@GeneratedValue
private int id;
	private String customer;
	private String category	;	
	private String accountTitle;
	private String positionType;		
	private String currency;
	private String currencyMarket;
	private String accountOfficer;
	private String conditionGroup;
	private double openActualBalance;
	private double openClearedBalance;
	private double onlineActualBalance;
	private double onlineClearedBalance;
	private double workingBalance;
	private String dateLastCrCust;
	private double amntLastCrCust;
	private String tranLastCrCust;
	private String passbook;
	private String openingDate;
	private String openCategory;
	private String chargeCurrency;
	private String chargeMarket;
	private String interestCurrency;
	private String interestMarket;
	private String altAcct;
	private String hvtFlag;
	private String dateLastUpdate;
	private String branchCode;
	
}

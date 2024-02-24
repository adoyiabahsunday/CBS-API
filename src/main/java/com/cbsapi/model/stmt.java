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
public class stmt {

	@Id
	private String id;
	private String accountNumber;	
	private String branchCode;
	private double amountLcy;
	private String transactionCode;
	private String narrative;
	private String customerId;
	private String accountOfficer;
	private String productCategory;
	private String valueDate;
	private String currency;
	private double amountFcy;	
	private double exchangeRate;	
	private String positionType;
	private String exposureDate;
	private String currencyMarket;
	private String departmentCode;
	private String transReference;	
	private String systemId;
	private String bookingDate;
	private String type;
	private String groupKey;
	private String processingDate;
}

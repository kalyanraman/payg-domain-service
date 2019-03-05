package com.eon.poc.document.model;

import java.io.Serializable;

import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

import lombok.Data;

/**
 * @author ANKAMMA PALLAPU
 *
 */
@Document
@Data
public class Transaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Field
	private String transactionId;

	@Field
	private Double debitAmount;

	@Field
	private Double creditAmount;

	@Field
	private Double balance;

	@Field
	private String status;

	@Field
	private String type;

	@Field
	private String notes;

	@Field
	private String transactionDateTime;

	@Field
	private String utrn;

}

package com.eon.poc.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;
import org.springframework.data.couchbase.core.mapping.id.IdAttribute;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

import lombok.Data;

@Document
@Data
public class Products implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationStrategy.USE_ATTRIBUTES, delimiter = "::")
	private String id;

	@IdAttribute(order = 0)
	private Integer paygProductId;

	@IdAttribute(order = 1)
	private String productCode;

	@IdAttribute(order = 2)
	private Integer year;

	@IdAttribute(order = 3)
	private Integer month;

	@Field
	private Double meterBalance;

	@Field
	@CreatedDate
	private DateTime meterBalanceDateTime;

	@Field
	private String predicatedExpiryDate;

	@Field
	private String productDescription;

	@Field
	private Double debtReclaimRate;

	@Field
	private String debtReclaimFrequency;

	@Field
	private Boolean nonDisConnectedEnable;
	@Field
	private List<Transaction> transactions = new ArrayList<>();

}

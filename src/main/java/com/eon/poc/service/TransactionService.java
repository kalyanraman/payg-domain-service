package com.eon.poc.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.eon.poc.document.model.Products;
import com.eon.poc.exception.TransactionNotFoundException;
import com.eon.poc.repository.TransactionRepository;
import com.eon.poc.transaction.model.ProductResponse;

@Service
public class TransactionService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);
	@Autowired
	TransactionRepository transactionRepository;

	public ProductResponse saveTransaction(Products paymentRequest) {
		logger.info("TransactionService class of saveTransaction method start :{}", paymentRequest.toString());
		ProductResponse productResponse = new ProductResponse();
		Products response = transactionRepository.save(paymentRequest);
		productResponse.setPayProductId(response.getId());
		logger.info("TransactionService class of saveTransaction method end :{}", productResponse.toString());
		return productResponse;
	}

	public Products getTransactionsByPaygId(String paygProductId) {
		logger.info("TransactionService class of getTransactionsByPaygId method start :{}", paygProductId);
		return transactionRepository.findById(paygProductId).orElseThrow(
				() -> new TransactionNotFoundException("Transactions Not Found", HttpStatus.NOT_FOUND.value()));
	}

	public Products getTransactions(String paygProductId, String productCode, String year, String month) {
		logger.info("TransactionService class of getTransactions method start :{}", paygProductId, productCode, year,
				month);
		String id = new StringBuffer(paygProductId).append("::").append(productCode).append("::").append(year)
				.append("::").append(month).toString();
		return transactionRepository.findById(id)

				.orElseThrow(() -> new TransactionNotFoundException("Transactions are Not Found",
						HttpStatus.NOT_FOUND.value()));
	}

	public List<Products> getAllTransactionsList() {
		logger.info("TransactionService class of getAllTransactionsList method start :{}");
		List<Products> list = new ArrayList<>();
		transactionRepository.findAll().forEach(payment -> list.add(payment));
		if (list.isEmpty()) {
			new TransactionNotFoundException("Transactions are Not Found", HttpStatus.NOT_FOUND.value());
		}
		return list;
	}

	public void deleteAllTransactions() {
		logger.info("TransactionService class of deleteAllTransactions method start :{}");
		transactionRepository.deleteAll();

	}
}

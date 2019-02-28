package com.eon.poc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eon.poc.document.model.Products;
import com.eon.poc.service.TransactionService;
import com.eon.poc.transaction.model.ProductResponse;

/**
 * @author ankamma pallapu
 *
 */
@RestController
@RequestMapping("/payment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {
	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
	@Autowired
	TransactionService transactionService;

	@PostMapping(value = "/transaction")
	public ProductResponse saveTransactions(@RequestBody Products paymentRequest) {
		logger.info("TransactionController class of saveTransactions method start :{}");
		return transactionService.saveTransaction(paymentRequest);
	}

	@GetMapping(value = "/getTransactionsByPaygId")
	public Products getTransactionsByPaygId(@RequestParam String paygProductId) {
		logger.info("TransactionController class of getTransactionsByPaygId method start :{}", paygProductId);
		return transactionService.getTransactionsByPaygId(paygProductId);
	}

	@GetMapping(value = "/getTransactions")
	public Products getTransactions(@RequestParam String paygProductId, @RequestParam String productCode,
			@RequestParam String year, @RequestParam String month) {
		logger.info("TransactionController class of getTransactions method start :{}", paygProductId, productCode, year,
				month);
		return transactionService.getTransactions(paygProductId, productCode, year, month);
	}

	@GetMapping(value = "/getAllTransactionsList")
	public List<Products> getAllTransactionsList() {
		logger.info("TransactionController class of getAllTransactionsList method start :{}");
		return transactionService.getAllTransactionsList();
	}

	@DeleteMapping(value = "/deleteAllTransactions")
	public void deleteAllTransactions() {
		logger.info("TransactionController class of deleteAllTransactions method start :{}");
		transactionService.deleteAllTransactions();
	}

}

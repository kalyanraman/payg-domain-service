package com.eon.poc.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.eon.poc.document.model.Products;
import com.eon.poc.document.model.Transaction;
import com.eon.poc.exception.TransactionNotFoundException;
import com.eon.poc.repository.TransactionRepository;
import com.eon.poc.transaction.model.ProductResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionService.class)
public class TransactionServiceTest {

	@Autowired
	TransactionService transactionService;

	@MockBean
	private TransactionRepository transactionRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void saveTransactionTest() {
		when(transactionRepository.save(buildProducts())).thenReturn(buildProducts());
		ProductResponse productResponse = transactionService.saveTransaction(buildProducts());
		Assertions.assertThat(productResponse).isNotNull();
		Assertions.assertThat(productResponse.getPayProductId()).isEqualTo("333::EON333::2019::3");

	}

	@Test
	public void getTransactionByIdTest() {
		when(transactionRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(buildProducts()));
		Products productResponse = transactionService.getTransactionsByPaygId("12");

		Assertions.assertThat(productResponse).isNotNull();
		Assertions.assertThat(productResponse.getPaygProductId()).isEqualTo(12);

	}

	@Test
	public void getTransactionByIdNotFoundTest() {
		when(transactionRepository.findById(Mockito.any()))
				.thenThrow(new TransactionNotFoundException("data not found", 404));
		thrown.expect(TransactionNotFoundException.class);
		transactionService.getTransactionsByPaygId("12");

	}

	@Test
	public void getTransactionsTest() {
		when(transactionRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(buildProducts()));
		Products productResponse = transactionService.getTransactions("12", "e12", "2019", "2");

		Assertions.assertThat(productResponse).isNotNull();
		Assertions.assertThat(productResponse.getPaygProductId()).isEqualTo(12);

	}

	@Test
	public void getTransactionsNotFoundTest() {
		when(transactionRepository.findById(Mockito.any()))
				.thenThrow(new TransactionNotFoundException("data not found", 404));
		thrown.expect(TransactionNotFoundException.class);
		transactionService.getTransactions("12", "e12", "2019", "2");

	}

	private Products buildProducts() {
		Products payment = new Products();
		payment.setDebtReclaimFrequency("weekly");
		payment.setDebtReclaimRate(12.00);
		payment.setId("333::EON333::2019::3");

		payment.setPaygProductId(12);
		payment.setProductCode("EON12");
		payment.setTransactions(setTransactions());

		return payment;
	}

	private List<Transaction> setTransactions() {
		List<Transaction> transactions = new ArrayList<>();
		Transaction transaction = new Transaction();
		transaction.setBalance(10.00);
		transaction.setCreditAmount(10.00);
		transaction.setDebitAmount(10.00);
		transaction.setStatus("pending");
		transaction.setTransactionId("1234");
		transaction.setType("type");
		transaction.setUtrn("123");
		return transactions;
	}

}

/**
 * 
 */
package com.eon.poc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eon.poc.document.model.Products;
import com.eon.poc.document.model.Transaction;
import com.eon.poc.exception.TransactionNotFoundException;
import com.eon.poc.service.TransactionService;
import com.eon.poc.transaction.model.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author ankamma pallapu
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TransactionController.class)
public class TransactionControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(TransactionControllerTest.class);
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	TransactionService transactionService;

	private static final String URI = "http://localhost:9090/payment";

	@Test
	public void saveTransactionTest() throws Exception {
		ProductResponse productResponse = buildProductResponse();
		when(transactionService.saveTransaction(Mockito.any())).thenReturn(productResponse);
		mockMvc.perform(MockMvcRequestBuilders.post(URI + "/transaction").accept(MediaType.APPLICATION_JSON)
				.content(getJson(productResponse)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.payProductId").value("333::EON333::2019::3"));

	}

	@Test
	public void getTransactionTest() throws Exception {
		Products products = buildProducts();
		when(transactionService.getTransactionsByPaygId(Mockito.any())).thenReturn(products);
		this.mockMvc.perform(get(URI + "/getTransactionsByPaygId?paygProductId=12")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.paygProductId").value(12)).andExpect(jsonPath("$.productCode").value("EON12"));

	}

	@Test
	public void testgetTransaction_Not_found() throws Exception {
		when(transactionService.getTransactionsByPaygId(Mockito.any()))
				.thenThrow(new TransactionNotFoundException("data not found", 404));
		try {
			this.mockMvc.perform(get(URI + "/getTransactionsByPaygId?paygProductId=12")).andDo(print())
					.andExpect(status().isNotFound())
					.andExpect(content().contentType("application/json;charset=UTF-8"));
		} catch (JsonProcessingException e) {
			logger.error("Exception occured while transactions are not :{}", e);
		} catch (Exception e) {
			logger.error("Exception occured while transactions are not :{}", e);
		}

	}

	@Test
	public void testgetTransaction_Internal_Error() throws Exception {
		when(transactionService.getTransactionsByPaygId(Mockito.any()))
				.thenThrow(new RuntimeException("Unable to process the request,please try after some time"));

		try {
			this.mockMvc.perform(get(URI + "/getTransactionsByPaygId?paygProductId=12")).andDo(print())
					.andExpect(status().is5xxServerError())
					.andExpect(content().contentType("application/json;charset=UTF-8"));
		} catch (JsonProcessingException e) {
			logger.error("Exception occured while system error test case  :{}", e);
		} catch (Exception e) {
			logger.error("Exception occured while system error test case  not :{}", e);
		}

	}

	private Products buildProducts() {
		Products products = new Products();
		products.setDebtReclaimFrequency("weekly");
		products.setDebtReclaimRate(12.00);

		products.setPaygProductId(12);
		products.setProductCode("EON12");
		products.setTransactions(setTransactions());

		return products;
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

	private ProductResponse buildProductResponse() {
		ProductResponse paymentResponse = new ProductResponse();
		paymentResponse.setPayProductId("333::EON333::2019::3");

		return paymentResponse;
	}

	private byte[] getJson(Object products) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(products);
		return json.getBytes();
	}

}

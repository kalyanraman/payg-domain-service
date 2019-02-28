/**
 * 
 */
package com.eon.poc.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.eon.poc.document.model.Product;
import com.eon.poc.exception.ProductsNotFoundException;
import com.eon.poc.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * @author ankamma pallapu
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProductService productService;

	private static final String URI = "http://localhost:9090/products";

	@Test
	public void saveProductsTest() throws Exception {
		Product productResponse = buildProductResponse();
		when(productService.saveProducts(Mockito.any())).thenReturn(productResponse);
		mockMvc.perform(MockMvcRequestBuilders.post(URI + "/product").accept(MediaType.APPLICATION_JSON)
				.content(getJson(productResponse)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.productDescription").value("GAS"));

	}

	@Test
	public void getProductsTest() throws Exception {
		List<Product> products = getProductList();
		when(productService.getAllProducts()).thenReturn(products);
		this.mockMvc.perform(get(URI + "/getAllProducts")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$[0].productDescription").value("GAS"))
				.andExpect(jsonPath("$[0].productCode").value("G"));

	}

	@Test
	public void deleteAllProductTest() throws Exception {
		Mockito.doNothing().when(productService).deleteAllProducts();
		this.mockMvc.perform(delete(URI + "/deleteAllTransactions")).andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void testGetProducts_Not_found() throws Exception {
		when(productService.getAllProducts()).thenThrow(new ProductsNotFoundException("data not found", 404));
		try {
			this.mockMvc.perform(get(URI + "/getAllProducts")).andDo(print()).andExpect(status().isNotFound())
					.andExpect(content().contentType("application/json;charset=UTF-8"));
		} catch (JsonProcessingException e) {
			logger.error("Exception occured while transactions are not :{}", e);
		} catch (Exception e) {
			logger.error("Exception occured while transactions are not :{}", e);
		}

	}

	@Test
	public void testGetProducts_Internal_Error() throws Exception {
		when(productService.getAllProducts())
				.thenThrow(new RuntimeException("Unable to process the request,please try after some time"));

		try {
			this.mockMvc.perform(get(URI + "/getAllProducts")).andDo(print())
					.andExpect(status().is5xxServerError())
					.andExpect(content().contentType("application/json;charset=UTF-8"));
		} catch (JsonProcessingException e) {
			logger.error("Exception occured while system error test case  :{}", e);
		} catch (Exception e) {
			logger.error("Exception occured while system error test case  not :{}", e);
		}

	}

	private List<Product> getProductList() {
		List<Product> products = new ArrayList<>();
		products.add(buildProductResponse());
		return products;

	}

	private Product buildProductResponse() {
		Product product = new Product();
		product.setProductCode("G");
		product.setProductDescription("GAS");
		product.setProductId(1);

		return product;
	}

	private byte[] getJson(Object products) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(products);
		return json.getBytes();
	}

}

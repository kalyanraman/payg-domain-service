package com.eon.poc.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.eon.poc.document.model.Product;
import com.eon.poc.exception.ProductsNotFoundException;
import com.eon.poc.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {

	@Autowired
	ProductService ProductService;

	@MockBean
	private ProductRepository productRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void saveProductsTest() {
		when(productRepository.save(buildProductResponse())).thenReturn(buildProductResponse());
		Product productResponse = ProductService.saveProducts(buildProductResponse());
		Assertions.assertThat(productResponse).isNotNull();
		Assertions.assertThat(productResponse.getProductDescription()).isEqualTo("GAS");

	}

	@Test
	public void getAllProductsTest() {
		when(productRepository.findAll()).thenReturn(getProductList());
		List<Product> productResponse = ProductService.getAllProducts();

		Assertions.assertThat(productResponse).isNotNull();
		Assertions.assertThat(productResponse.get(0).getProductDescription()).isEqualTo("GAS");

	}

	@Test
	public void deleteAllProductsTest() {
		Mockito.doNothing().when(productRepository).deleteAll();
		ProductService.deleteAllProducts();

	}

	@Test
	public void getAllProductsNotFoundTest() {
		when(productRepository.findAll()).thenThrow(new ProductsNotFoundException("data not found", 404));
		thrown.expect(ProductsNotFoundException.class);
		ProductService.getAllProducts();

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
}

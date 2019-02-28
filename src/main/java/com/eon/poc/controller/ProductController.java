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
import org.springframework.web.bind.annotation.RestController;

import com.eon.poc.document.model.Product;
import com.eon.poc.service.ProductService;

/**
 * @author ankamma pallapu
 *
 */
@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@PostMapping(value = "/product")
	public Product saveProducts(@RequestBody Product productRequest) {
		logger.info("ProductController class of saveProducts method start :{}",productRequest.toString());
		return productService.saveProducts(productRequest);
	}

	@GetMapping(value = "/getAllProducts")
	public List<Product> getAllProducts() {
		logger.info("ProductController class of getAllProducts method start :{}");
		return productService.getAllProducts();
	}

	@DeleteMapping(value = "/deleteAllTransactions")
	public void deleteAllProducts() {
		logger.info("ProductController class of deleteAllProducts method start :{}");
		productService.deleteAllProducts();
	}

}

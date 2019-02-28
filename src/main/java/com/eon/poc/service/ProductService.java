package com.eon.poc.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.eon.poc.document.model.Product;
import com.eon.poc.exception.ProductsNotFoundException;
import com.eon.poc.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	ProductRepository productRepository;

	public Product saveProducts(Product product) {
		logger.info("ProductService class of saveProducts method start :{}", product.toString());
		return productRepository.save(product);

	}

	public void deleteAllProducts() {
		logger.info("ProductService class of deleteAllProducts method start :{}");
		productRepository.deleteAll();

	}

	public List<Product> getAllProducts() {
		logger.info("ProductService class of getAllProducts method start :{}");
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(product -> products.add(product));
		if (products.isEmpty()) {
			throw new ProductsNotFoundException("Products are not found", HttpStatus.NOT_FOUND.value());
		}
		return products;
	}

}

package com.eon.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public final ErrorResult handleInternalError(RuntimeException ex) {

		return new ErrorResult(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	@ExceptionHandler(TransactionNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public final ErrorResult handleDataNotFound(TransactionNotFoundException ex) {

		return new ErrorResult(ex.getErrorMessage(), HttpStatus.NOT_FOUND.value());
	}

	@ExceptionHandler(ProductsNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public final ErrorResult handleProductDataNotFound(ProductsNotFoundException ex) {

		return new ErrorResult(ex.getErrorMessage(), HttpStatus.NOT_FOUND.value());
	}

}

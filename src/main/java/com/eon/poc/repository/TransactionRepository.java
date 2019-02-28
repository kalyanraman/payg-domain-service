/**
 * 
 */
package com.eon.poc.repository;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.eon.poc.document.model.Products;

/**
 * @author ankamma pallapu
 *
 */
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "products", viewName = "all")
public interface TransactionRepository extends CouchbaseRepository<Products, String> {

}

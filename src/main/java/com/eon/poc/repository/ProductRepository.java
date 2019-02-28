/**
 * 
 */
package com.eon.poc.repository;

import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import com.eon.poc.document.model.Product;

/**
 * @author ankamma pallapu
 *
 */
@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "product", viewName = "all")
public interface ProductRepository extends CouchbaseRepository<Product, String> {

}

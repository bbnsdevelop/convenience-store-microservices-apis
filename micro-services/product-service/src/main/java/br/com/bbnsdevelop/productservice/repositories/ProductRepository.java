package br.com.bbnsdevelop.productservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.bbnsdevelop.productservice.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{
	

}

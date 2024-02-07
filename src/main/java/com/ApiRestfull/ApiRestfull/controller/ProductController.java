package com.ApiRestfull.ApiRestfull.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ApiRestfull.ApiRestfull.dtos.ProductRecordDto;
import com.ApiRestfull.ApiRestfull.models.ProductModel;
import com.ApiRestfull.ApiRestfull.repositories.ProductRepository;

import jakarta.validation.Valid;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/productes")
	public ResponseEntity<List<ProductModel>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Object> getOne(@PathVariable (value = "id") UUID id){
		Optional<ProductModel> product1 = productRepository.findById(id);
		
		if (product1.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found Product");
		}
		return ResponseEntity.status(HttpStatus.OK).body(product1.get());
		
	}

		

	@PostMapping("/products")
	public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
		var productModel = new ProductModel();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));

	}
	
	@DeleteMapping("/products/{id}") 
	public ResponseEntity<Object> deleteProduct (@PathVariable (value = "id") UUID id){
		Optional<ProductModel> product1 = productRepository.findById(id);
		productRepository.delete(product1.get());
		return ResponseEntity.status(HttpStatus.OK).body("Product Deleted Suceddfully");
		
	}

	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable(value="id") UUID id,
												@RequestBody @Valid ProductRecordDto productRecordDto) {
		Optional<ProductModel> product1 = productRepository.findById(id);
		var productModel = product1.get();
		BeanUtils.copyProperties(productRecordDto, productModel);
		return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
		
	}

}
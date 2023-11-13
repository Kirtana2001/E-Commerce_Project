package com.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.Product;
import com.project.respository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public void save(Product p) {
		productRepository.save(p);
	}

	public List<Product> getAllProduct() {
		return productRepository.findAll();
	}

	public Product getProductById(int id) {
		return productRepository.findById(id).get();
	}

	public void deleteById(int id) {
		productRepository.deleteById(id);
	}
}

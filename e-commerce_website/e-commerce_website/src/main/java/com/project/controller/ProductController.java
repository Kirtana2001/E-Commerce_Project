package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.project.entity.Product;
import com.project.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/admin_product_register")
	public String productRegister() {
		return "adminproductRegister";
	}

	@GetMapping("/available_products")
	public ModelAndView getAllProduct() {
		List<Product> list = productService.getAllProduct();
		return new ModelAndView("productList", "product", list);
	}

	@GetMapping("/manage_products")
	public ModelAndView getAllProductsAdmin() {
		List<Product> list = productService.getAllProduct();
		return new ModelAndView("adminProductList", "product", list);
	}

	@PostMapping("/save")
	public String addProduct(@ModelAttribute Product p) {
		productService.save(p);
		return "redirect:/manage_products";
	}

	@RequestMapping("/adminEditProduct/{id}")
	public String editProduct(@PathVariable("id") int id, Model model) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "adminProductEdit";
	}

	@RequestMapping("/adminDeleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		productService.deleteById(id);
		return "redirect:/manage_products";
	}

}

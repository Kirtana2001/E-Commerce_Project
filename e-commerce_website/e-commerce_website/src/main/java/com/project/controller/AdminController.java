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
import com.project.entity.User;
import com.project.service.ProductService;
import com.project.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	ProductService productService;

	@Autowired
	private UserService userService;

	@GetMapping("/adminHome")
	public String adminHome() {
		return "admin_page";
	}

	@GetMapping("/admin_product_register")
	public String productRegister() {
		return "adminProductRegister";
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
		return "adminproductEdit";
	}

	@RequestMapping("/adminDeleteProduct/{id}")
	public String deleteProduct(@PathVariable("id") int id) {
		productService.deleteById(id);
		return "redirect:/manage_products";
	}

	@GetMapping("/manage_users")
	public ModelAndView getAllUsers() {
		List<User> list = userService.getAllUsers();
		return new ModelAndView("adminUserList", "user", list);
	}
}

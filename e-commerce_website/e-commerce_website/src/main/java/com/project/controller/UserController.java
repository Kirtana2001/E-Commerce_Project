package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.project.entity.Product;
import com.project.entity.User;
import com.project.respository.UserRepository;
import com.project.service.ProductService;
import com.project.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	ProductService productService;

	@Autowired
	public UserService userService;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public PasswordEncoder passwordEncoder;

	@GetMapping("/userHome")
	public String userHome() {
		return "home_page";
	}

	@GetMapping("/available_products")
	public ModelAndView getAllBook() {
		List<Product> list = productService.getAllProduct();
		return new ModelAndView("productList", "product", list);
	}

	@GetMapping("/updateDetails")
	public String userUpdate(Model model) {
		// Get the currently logged-in user's email
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		// Find the user by email
		User user = userRepository.findByEmail(username);

		if (user == null) {
			// Handle the case where the user is not found, e.g., show an error message
			return "error_page"; // Create an error page or specify a different error handling mechanism
		}

		model.addAttribute("user", user);
		return "user_update";
	}

	@PostMapping("/saveUpdates")
	public String saveUpdates(@ModelAttribute User user, @RequestParam("newPassword") String newPassword) {
		// Retrieve the user from the database by their ID.
		User existingUser = userRepository.findById(user.getId()).orElse(null);

		if (existingUser != null) {
			// Update the user's details based on the form data.
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setAddress(user.getAddress());
			existingUser.setContact(user.getContact());

			if (!newPassword.isEmpty()) {
				// Hash the new password and update the user's password.
				String hashedPassword = passwordEncoder.encode(newPassword);
				existingUser.setPassword(hashedPassword);
			}

			// Save the updated user in the database.
			userRepository.save(existingUser);
		}

		return "home_page";
	}

}

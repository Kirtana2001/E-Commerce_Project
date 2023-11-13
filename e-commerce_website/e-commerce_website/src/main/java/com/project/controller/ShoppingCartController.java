package com.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.entity.CartItem;
import com.project.entity.Product;
import com.project.entity.User;
import com.project.respository.UserRepository;
import com.project.service.ProductService;
import com.project.service.ShoppingCartService;

@Controller
@RequestMapping("/user")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService cartService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductService productService;

	@GetMapping("/cart")
	public String showShoppingCart(Model model, Principal principal) {
		String username = principal.getName(); // Get the username of the currently logged-in user
		User currentUser = userRepository.findByEmail(username);

		List<CartItem> cartItems = cartService.listCartItems(currentUser);

		// Calculate the total amount
		double totalAmount = 0;
		for (CartItem cartItem : cartItems) {
			totalAmount += cartItem.getProduct().getPrice() * cartItem.getQuantity();
		}

		model.addAttribute("cartItems", cartItems);
		// model.addAttribute("pageTitle", "Shopping Cart");
		model.addAttribute("totalAmount", totalAmount);
		return "shopping_cart";
	}

	@GetMapping("/myCart/{id}")
	public String addToCart(@PathVariable int id, Principal principal) {
		// Get the currently logged-in user
		String username = principal.getName();

		// Retrieve the product with the given id (you'll need to implement this method)
		Product selectedProduct = productService.getProductById(id);

		// Add the product to the user's cart (you'll need to implement this method)
		cartService.addToCart(username, selectedProduct);

		// Redirect the user to the "Available Products" page or another appropriate
		// page
		return "redirect:/available_products";
	}

	@GetMapping("/deleteCartItem/{id}")
	public String deleteCartItem(@PathVariable int id, Principal principal) {
		// Get the currently logged-in user
		String username = principal.getName();

		// Delete the cart item by its ID
		// You will need to implement a service method for deleting cart items by ID
		cartService.deleteCartItemById(id, username);

		// Redirect the user back to the shopping cart page
		return "redirect:/user/cart";
	}
}

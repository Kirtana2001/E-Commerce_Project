package com.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entity.CartItem;
import com.project.entity.Product;
import com.project.entity.User;
import com.project.respository.CartItemRepository;
import com.project.respository.UserRepository;

@Service
public class ShoppingCartService {

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private UserRepository userRepository;

	public List<CartItem> listCartItems(User user) {
		return cartItemRepository.findByUser(user);
	}

	public void addToCart(String username, Product selectedProduct) {
		// Fetch the user
		User user = userRepository.findByEmail(username);

		// Create a CartItem
		CartItem cartItem = new CartItem();
		cartItem.setProduct(selectedProduct);
		cartItem.setUser(user);
		cartItem.setQuantity(1); // Set the quantity as needed

		// Add the item to the shopping cart
		cartItemRepository.save(cartItem);
	}

	public void deleteCartItemById(int cartItemId, String username) {
		User user = userRepository.findByEmail(username);
		Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);

		if (user != null && cartItem.isPresent()) {
			// Ensure that the cart item belongs to the user
			if (cartItem.get().getUser().getId() == user.getId()) {
				cartItemRepository.delete(cartItem.get());
			}
		}
	}

}

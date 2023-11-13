package com.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Order;
import com.project.entity.User;
import com.project.respository.UserRepository;
import com.project.service.OrderService;

@Controller
@RequestMapping("/user")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserRepository userRepository;

	// Create an order
	@GetMapping("/placeOrder")
	public String placeOrder(Principal principal, Model model, @RequestParam("totalAmount") double totalAmount) {
		String username = principal.getName();
		User user = userRepository.findByEmail(username);

		Order order = orderService.createOrder(user, totalAmount);

		// You can add additional logic here if needed

		return "redirect:/user/orders";
	}

	// Retrieve orders for a user
	@GetMapping("/orders")
	public String viewOrders(Principal principal, Model model) {
		String username = principal.getName();
		User user = userRepository.findByEmail(username);

		List<Order> orders = orderService.getOrdersByUser(user);

		model.addAttribute("orders", orders);

		return "orderList";
	}
}

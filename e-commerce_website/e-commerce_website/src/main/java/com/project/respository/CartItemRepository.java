package com.project.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entity.CartItem;
import com.project.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	public List<CartItem> findByUser(User user);

}

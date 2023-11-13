package com.project.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entity.Order;
import com.project.entity.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findByUser(User user);

}

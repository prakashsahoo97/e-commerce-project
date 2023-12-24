package com.prakash.service.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.exception.OrderException;
import com.prakash.model.Address;
import com.prakash.model.Order;
import com.prakash.model.User;
import com.prakash.repositpry.CartRepository;
import com.prakash.service.CartService;
import com.prakash.service.OrderService;
import com.prakash.service.ProductService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class OrderServiceImplementation implements OrderService {
	
	private CartRepository cartRepository;
	private CartService cartService;
	private ProductService productService;

	@Override
	public Order createOrder(User user, Address shippingAdress) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order findOrderById(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> usersOrderHistory(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order placedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order confirmedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order shippedOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliveredOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancledOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOrder(Long orderId) throws OrderException {
		// TODO Auto-generated method stub

	}

}

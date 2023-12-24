package com.prakash.service;

import com.prakash.exception.ProductException;
import com.prakash.model.Cart;
import com.prakash.model.User;
import com.prakash.request.AddItemRequest;

public interface CartService {

	public Cart createCart(User user);

	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

	public Cart findUserCart(Long userId);
}

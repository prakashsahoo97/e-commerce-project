package com.prakash.service;

import com.prakash.exception.CartItemException;
import com.prakash.exception.UserException;
import com.prakash.model.Cart;
import com.prakash.model.CartItem;
import com.prakash.model.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem)throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Cart cart,Product product,String size,Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartItemId)throws CartItemException;
}

package com.prakash.service.implementation;

import org.springframework.stereotype.Service;

import com.prakash.exception.ProductException;
import com.prakash.model.Cart;
import com.prakash.model.CartItem;
import com.prakash.model.Product;
import com.prakash.model.User;
import com.prakash.repositpry.CartRepository;
import com.prakash.request.AddItemRequest;
import com.prakash.service.CartItemService;
import com.prakash.service.CartService;
import com.prakash.service.ProductService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {
	
	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;

	@Override
	public Cart createCart(User user) {

		Cart cart=new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {

		Cart cart=cartRepository.findByUserId(userId);
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setSize(req.getSize());
			cartItem.setUserId(userId);
			
			Double price=req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			
			CartItem createdCartItem=cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		
		return "Item Added To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		
		double totalPrice=0;
		double totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItems()) {
			totalPrice+=cartItem.getPrice();
			totalDiscountedPrice+=cartItem.getDiscountedPrice();
			totalItem+=cartItem.getQuantity();
		}
		
		cart.setTotalPrice(totalPrice);
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setDiscounte(totalPrice -totalDiscountedPrice);
		return cartRepository.save(cart);
	}

}

package com.prakash.service.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prakash.exception.CartItemException;
import com.prakash.exception.UserException;
import com.prakash.model.Cart;
import com.prakash.model.CartItem;
import com.prakash.model.Product;
import com.prakash.model.User;
import com.prakash.repositpry.CartItemRepository;
import com.prakash.repositpry.CartRepository;
import com.prakash.service.CartItemService;
import com.prakash.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartItemServiceImplementation implements CartItemService {


	private CartItemRepository cartItemRepository;
	private CartRepository cartRepository;
	private UserService userService;
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {

		cartItem.setQuantity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
		return cartItemRepository.save(cartItem);
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		if(user.getId().equals(userId)) {
			item.setQuantity(cartItem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getQuantity()*item.getProduct().getDiscountedPrice());
			
			return cartItemRepository.save(item);
		}else {
			throw new CartItemException("You can't update another user's Cart_Item...");
		}
		
		
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {

		
		return cartItemRepository.isCartItemExist(cart, product, size, userId);
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

		CartItem item=findCartItemById(cartItemId);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			cartItemRepository.deleteById(item.getId());
		}else {
			throw new CartItemException("You can't remove another user's Cart_Item...");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {

		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent())
					return opt.get();
		
		throw new CartItemException("cartItem not found with id :"+cartItemId);
	}

}

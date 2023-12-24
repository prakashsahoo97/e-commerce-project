package com.prakash.repositpry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prakash.model.Cart;
import com.prakash.model.CartItem;
import com.prakash.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	@Query("Select ci from CartItem ci where ci.cart=:cart and "
										  + "ci.product=:product And "
										  + "ci.size=:size And "
										  + "ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart,
									@Param("product") Product product,
									@Param("size") String size,
									@Param("userId") Long userId);

}

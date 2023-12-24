package com.prakash.repositpry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prakash.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("Select c from Cart c where c.User.id=: userId")
	public Cart findByUserId(@Param("userId") Long userId);
}

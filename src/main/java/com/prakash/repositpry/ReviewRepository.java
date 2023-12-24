package com.prakash.repositpry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prakash.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("Select r form Review r where r.product.id= :productId")
	public List<Review> getProductReviews(@Param("productId") Long productId);
}

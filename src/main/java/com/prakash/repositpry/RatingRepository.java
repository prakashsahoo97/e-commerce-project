package com.prakash.repositpry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prakash.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	@Query("Select r from Rating r where r.product.id= :productId")
	public List<Rating> getProductRatings(@Param("productId") Long productId);
}

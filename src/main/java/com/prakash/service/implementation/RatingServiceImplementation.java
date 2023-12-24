package com.prakash.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.exception.ProductException;
import com.prakash.model.Product;
import com.prakash.model.Rating;
import com.prakash.model.User;
import com.prakash.repositpry.RatingRepository;
import com.prakash.request.RatingRequest;
import com.prakash.service.ProductService;
import com.prakash.service.RatingService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class RatingServiceImplementation implements RatingService {
	
	private RatingRepository ratingRepository;
	private ProductService productService;

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {

		Product product=productService.findProductById(req.getProductId());
		
		Rating rating=new Rating();
		rating.setUser(user);
		rating.setProduct(product);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductsRating(Long productId) {		
		
		return ratingRepository.getProductRatings(productId);
	}

}

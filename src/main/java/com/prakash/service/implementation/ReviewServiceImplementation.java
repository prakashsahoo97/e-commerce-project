package com.prakash.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.prakash.exception.ProductException;
import com.prakash.model.Product;
import com.prakash.model.Review;
import com.prakash.model.User;
import com.prakash.repositpry.ReviewRepository;
import com.prakash.request.ReviewRequest;
import com.prakash.service.ProductService;
import com.prakash.service.ReviewService;

import lombok.AllArgsConstructor;
@Service 
@AllArgsConstructor
public class ReviewServiceImplementation implements ReviewService {

	private ReviewRepository reviewRepository;
	
	private ProductService productService;
	
	
	@Override
	public Review createReview(ReviewRequest req, User user) throws ProductException {

		Product product=productService.findProductById(req.getProductId());
		
		Review review=new Review();
		review.setReview(req.getReview());
		review.setProduct(product);
		review.setUser(user);
		review.setCreatedAt(LocalDateTime.now());
		
		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getProductReviews(Long productId) {

		return reviewRepository.getProductReviews(productId);
	}

}

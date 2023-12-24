package com.prakash.service;

import java.util.List;

import com.prakash.exception.ProductException;
import com.prakash.model.Review;
import com.prakash.model.User;
import com.prakash.request.ReviewRequest;

public interface ReviewService {

	
	public Review createReview(ReviewRequest req,User user)throws ProductException;
	
	public List<Review> getProductReviews(Long productId);
}

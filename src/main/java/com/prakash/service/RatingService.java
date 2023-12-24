package com.prakash.service;

import java.util.List;

import com.prakash.exception.ProductException;
import com.prakash.model.Rating;
import com.prakash.model.User;
import com.prakash.request.RatingRequest;

public interface RatingService {
	
	public Rating createRating(RatingRequest req,User user)throws ProductException;

	public List<Rating> getProductsRating(Long productId);
}

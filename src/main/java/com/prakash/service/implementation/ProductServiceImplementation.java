package com.prakash.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.prakash.exception.ProductException;
import com.prakash.model.Category;
import com.prakash.model.Product;
import com.prakash.repositpry.CategoryRepository;
import com.prakash.repositpry.ProductRepository;
import com.prakash.request.CreateProductRequest;
import com.prakash.service.ProductService;
import com.prakash.service.UserService;

import lombok.AllArgsConstructor;

@Service
public class ProductServiceImplementation implements ProductService {
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private UserService userService;
//this is product service implementation
	@Override
	public Product createProduct(CreateProductRequest req) {

		Category topLevel = categoryRepository.findByName(req.getTopLevelCategory());

		if (topLevel == null) {
			Category topLevelCategory = new Category();
			topLevelCategory.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);

			topLevel = categoryRepository.save(topLevelCategory);
		}

		Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), 
																	  topLevel.getName());
		if (secondLevel == null) {
			Category secondLevelCategory = new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);

			secondLevel = categoryRepository.save(secondLevelCategory);
		}

		Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), 
																	 secondLevel.getName());
		if (thirdLevel == null) {
			Category thirdLevelCategory = new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);

			thirdLevel = categoryRepository.save(thirdLevelCategory);
		}
		
		Product newProduct=new Product();
		newProduct.setTitle(req.getTitle());
		newProduct.setColor(req.getColor());
		newProduct.setDescription(req.getDescription());
		newProduct.setDiscountedPrice(req.getDiscountedPrice());
		newProduct.setDiscountedPercent(req.getDiscountedPercent());
		newProduct.setImageUrl(req.getImageUrl());
		newProduct.setBrand(req.getBrand());
		newProduct.setPrice(req.getPrice());
		newProduct.setSizes(req.getSize());
		newProduct.setQuantity(req.getQuantity());
		newProduct.setCategory(thirdLevel);
		newProduct.setCreatedAt(LocalDateTime.now());
		
		return productRepository.save(newProduct);
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {

			Product product = findProductById(productId);
			product.getSizes().clear();
			productRepository.delete(product);
		return "Product deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		
		Product product = findProductById(productId);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		
		Optional<Product> optional = productRepository.findById(id);
		
		if(optional.isPresent())
			return optional.get();
		throw new ProductException("Product not found with id: "+id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String Category, List<String> colors, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		List<Product> products = productRepository.filterProducts(Category, minPrice, maxPrice, minDiscount, sort);
		
		
		if (!colors.isEmpty()) {
			products = products.stream()
			        .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
			        .collect(Collectors.toList());
		} 

		if(stock!=null) {
			if(stock.equals("in_stock")) {
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if (stock.equals("out_of_stock")) {
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());				
			}
		}
		
		int startIndex = (int) pageable.getOffset();
		int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
		
		List<Product> pageContent = products.subList(startIndex, endIndex);
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		
		return filteredProducts;
	}

}

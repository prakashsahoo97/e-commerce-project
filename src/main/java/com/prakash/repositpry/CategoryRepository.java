package com.prakash.repositpry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prakash.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByName(String name);
	
	@Query("SELECT C FROM Category C WHERE C.name=:name AND C.parentCategory.name= :parentCategoryName ")
	public Category findByNameAndParent(@Param("name") String name,@Param("parentCategoryName") String parentCategoryName);
}

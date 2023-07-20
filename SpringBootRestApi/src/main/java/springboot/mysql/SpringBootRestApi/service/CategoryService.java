package springboot.mysql.SpringBootRestApi.service;

import java.util.List;
import java.util.Optional;

import springboot.mysql.SpringBootRestApi.entity.Category;

public interface CategoryService {
	
	Category addCategory(Category category);
	
	List<Category> getAllCategories();
	
	Optional<Category> getCategoryById(Long id);
	
	void deleteCategory(Long id);
	
	Category updateCategory(Category category);
	
	

}

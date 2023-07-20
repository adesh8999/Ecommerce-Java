package springboot.mysql.SpringBootRestApi.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import springboot.mysql.SpringBootRestApi.entity.Category;
import springboot.mysql.SpringBootRestApi.repository.CategoryRepository;
import springboot.mysql.SpringBootRestApi.service.CategoryService;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category addCategory(Category category) {

		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> getCategoryById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);

	}

	@Override
	public Category updateCategory(Category category) {
		Category cat = categoryRepository.findById(category.getCatId()).get();
		cat.setCatName(category.getCatName());

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		category.setActive(true);
		category.setCreatedDate(timestamp);
		category.setUpdatedDate(timestamp);

		return categoryRepository.save(cat);
	}

}

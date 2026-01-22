package com.example.inventory_management.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.inventory_management.Repository.CategoriesRepository;
import com.example.inventory_management.entity.Categories;

@Service
public class categoriesService {

	private CategoriesRepository categoriesRepo;

	public categoriesService(CategoriesRepository categoriesRepo) {
		this.categoriesRepo = categoriesRepo;
	}

	public List<Categories> getcategories() {
		return categoriesRepo.findAll();
	}

	public Categories add(Categories categories) {
		return categoriesRepo.save(categories);

	}

	public void del(Long id) {
		categoriesRepo.deleteById(id);

	}

	public Categories update(Long id, Categories categories) {
		Categories cat = categoriesRepo.findById(id).orElseThrow(() -> new RuntimeException("categories not found"));
		categories.setCategories_id(id);
		return categoriesRepo.save(categories);
	}

	public Categories toggle(Long id) {
		Categories cat = categoriesRepo.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
		cat.setStatus(!cat.isStatus());
		return categoriesRepo.save(cat);
	}

}

package com.bookingTour.service;

import com.bookingTour.model.CategoryModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {

    public CategoryModel findCategory(Long id);

    public CategoryModel addCategory(CategoryModel categoryModel) throws Exception;

    public CategoryModel editCategory(CategoryModel categoryModel) throws Exception;

    public boolean deleteCategory(CategoryModel categoryModel) throws Exception;

    public List<CategoryModel> findAll(CategoryModel categoryModel);

    public Page<CategoryModel> paginate(CategoryModel categoryModel);

    public int count(CategoryModel categoryModel);
}

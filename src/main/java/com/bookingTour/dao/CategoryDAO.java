package com.bookingTour.dao;

import com.bookingTour.entity.Category;
import com.bookingTour.model.CategoryModel;
import org.springframework.data.domain.Page;

public interface CategoryDAO extends GenericDAO<Category, Long> {

    public Page<Category> paginate(CategoryModel categoryModel);

}

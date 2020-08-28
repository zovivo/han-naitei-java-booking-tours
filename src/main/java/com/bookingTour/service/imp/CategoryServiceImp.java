package com.bookingTour.service.imp;

import com.bookingTour.dao.CategoryDAO;
import com.bookingTour.entity.Category;
import com.bookingTour.model.CategoryModel;
import com.bookingTour.model.TourModel;
import com.bookingTour.service.CategoryService;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImp implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImp.class);

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    public CategoryModel findCategory(Long id) {
        logger.info("Checking the category in the database");
        try {
            Category category = categoryDAO.find(id);
            CategoryModel categoryModel = null;
            if (category != null) {
                categoryModel = new CategoryModel();
                BeanUtils.copyProperties(category, categoryModel);
            }
            return categoryModel;
        } catch (Exception e) {
            logger.error("An error occurred while fetching the category details from the database", e);
            return null;
        }
    }

    @Override
    @Transactional
    public CategoryModel addCategory(CategoryModel categoryModel) throws Exception {
        logger.info("Adding the category in the database");
        try {
            Category category = new Category();
            category.setName(categoryModel.getName());
            category = categoryDAO.makePersistent(category);
            categoryModel = new CategoryModel();
            BeanUtils.copyProperties(category, categoryModel);
            return categoryModel;
        } catch (Exception e) {
            logger.error("An error occurred while adding the category details to the database", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public CategoryModel editCategory(CategoryModel categoryModel) throws Exception {
        logger.info("Updating the category in the database");
        try {
            Category category = categoryDAO.find(categoryModel.getId(), true);
            if (StringUtils.hasText(categoryModel.getName())) {
                category.setName(categoryModel.getName());
            }
            categoryDAO.makePersistent(category);
            return categoryModel;
        } catch (Exception e) {
            logger.error("An error occurred while updating the category details to the database", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean deleteCategory(CategoryModel categoryModel) throws Exception {
        logger.info("Deleting the category in the database");
        try {
            Category category = categoryDAO.find(categoryModel.getId(), true);
            categoryDAO.makeTransient(category);
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while adding the category details to the database", e);
            throw e;
        }
    }

    @Override
    public List<CategoryModel> findAll(CategoryModel categoryModel) {
        logger.info("Fetching all microposts in the database");
        List<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
        try {
            List<Category> categories = categoryDAO.findAll();
            for (Category category : categories) {
                CategoryModel model = new CategoryModel();
                BeanUtils.copyProperties(category, model);
                categoryModels.add(model);
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching all categories from the database", e);
        }
        return categoryModels;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryModel> paginate(CategoryModel categoryModel) {
        try {
            Page<Category> categories = categoryDAO.paginate(categoryModel);
            return categories.map(category -> {
                CategoryModel model = new CategoryModel();
                BeanUtils.copyProperties(category, model);
                return model;
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public int count(CategoryModel categoryModel) {
        logger.info("Counting the category in the database");
        try {
            return categoryDAO.count(Restrictions.eq("name", categoryModel.getName()));
        } catch (Exception e) {
            logger.error("An error occurred while counting the category details from the database", e);
            return 0;
        }
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }
}

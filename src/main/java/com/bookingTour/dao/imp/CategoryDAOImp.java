package com.bookingTour.dao.imp;

import com.bookingTour.dao.CategoryDAO;
import com.bookingTour.entity.Category;
import com.bookingTour.entity.Tour;
import com.bookingTour.model.CategoryModel;
import com.bookingTour.model.TourModel;
import com.bookingTour.util.SearchQueryTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CategoryDAOImp extends GenericDAOImp<Category, Long> implements CategoryDAO {

    public CategoryDAOImp() {
        super(Category.class);
    }

    @Override
    public Page<Category> paginate(CategoryModel categoryModel) {
        String sql = "FROM Category c WHERE c.name = :name";
        String countSql = "SELECT COUNT(*) FROM Category c WHERE c.name = :name";
        SearchQueryTemplate searchQueryTemplate = new SearchQueryTemplate(sql, countSql, categoryModel.getPageable());
        searchQueryTemplate.addParameter("name", categoryModel.getName());
        searchQueryTemplate.addOrder(Sort.Direction.DESC, "createTime");
        return paginate(searchQueryTemplate);
    }
}

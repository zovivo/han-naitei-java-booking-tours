package com.bookingTour.controller;

import com.bookingTour.model.CategoryModel;
import com.bookingTour.model.TourModel;
import com.bookingTour.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(TourController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = {"","/index"})
    public String index(@RequestParam(name = "page", required = false) Optional<Integer> page, Locale locale,
                        Model model, HttpServletRequest request) {
        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setPage(page.orElse(1));
        Page<CategoryModel> categoryModels = categoryService.paginate(categoryModel);
        model.addAttribute("categories", categoryModels);
        return "categories/index";
    }

    @GetMapping(value = { "/add" })
    public String add(Locale locale, Model model) {
        model.addAttribute("category", new CategoryModel());
        return "categories/add";
    }

    @PostMapping(value = "/")
    public String create(@ModelAttribute("category") @Validated CategoryModel categoryModel, BindingResult bindingResult, Model model, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors()) {
            logger.info("Returning add category page, validate failed");
            return "categories/create";
        }
        categoryService.addCategory(categoryModel);
        return "redirect: " + request.getContextPath() + "/index";
    }

    @GetMapping(value = "/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findCategory(id));
        return "categories/show";
    }

    @GetMapping(value = "{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findCategory(id));
        return "categories/edit";
    }

    @PutMapping(value = "/{id}")
    public String update(@ModelAttribute("category") @Validated CategoryModel categoryModel,
                         BindingResult bindingResult, Model model, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors()) {
            logger.info("Returning edit category page, validate failed");
            return "categories/edit";
        }
        CategoryModel category = categoryService.editCategory(categoryModel);
        return "redirect: " + request.getContextPath() + "/categories/" + category.getId();
    }

    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ResponseEntity<String> destroy(@PathVariable Long id, Model model, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        logger.info("Deleting category: " + id);
        categoryService.deleteCategory(new CategoryModel(id));
        String contentType = request.getContentType();
        if (contentType.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return new ResponseEntity<String>("{\"result\" : \"OK\", \"id\" : " + id + ", \"model\" : \"category\"}",
                    HttpStatus.OK);
        } else {
            response.sendRedirect(request.getContextPath() + "/categories");
            return new ResponseEntity<String>(HttpStatus.OK);
        }
    }

}

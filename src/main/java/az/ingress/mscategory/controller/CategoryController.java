package az.ingress.mscategory.controller;

import az.ingress.mscategory.model.response.CategoryResponse;
import az.ingress.mscategory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/categories")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CategoryController {

    CategoryService service;

    @GetMapping
    public List<CategoryResponse> getCategories() {
        return service.getCategories();
    }

    @GetMapping("{id}")
    public CategoryResponse getCategory(@PathVariable Long id) {
        return service.getCategory(id);
    }
}
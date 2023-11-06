package az.ingress.mscategory.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import az.ingress.mscategory.model.request.CategoryRequest;
import az.ingress.mscategory.service.CategoryService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("internal/v1/categories")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InternalCategoryController {

    CategoryService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveCategory(@RequestBody CategoryRequest request) {
        service.saveCategory(request);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateCategory(@PathVariable Long id, @RequestBody CategoryRequest request) {
        service.updateCategory(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
    }
}
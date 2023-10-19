package az.ingress.mscategory.controller;

import az.ingress.mscategory.model.request.SaveSubcategoryRequest;
import az.ingress.mscategory.model.request.UpdateSubcategoryRequest;
import az.ingress.mscategory.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@RequestMapping("internal/v1/subcategories")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InternalSubcategoryController {

    SubcategoryService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public void saveSubcategory(@RequestBody SaveSubcategoryRequest request) {
        service.saveSubcategory(request);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateSubcategory(@PathVariable Long id, @RequestBody UpdateSubcategoryRequest request) {
        service.updateSubcategory(id, request);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteSubcategory(@PathVariable Long id) {
        service.deleteSubcategory(id);
    }
}
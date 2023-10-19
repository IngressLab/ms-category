package az.ingress.mscategory.controller;

import az.ingress.mscategory.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import az.ingress.mscategory.model.response.SubcategoryResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/subcategories")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SubcategoryController {

    SubcategoryService service;

    @GetMapping("{id}")
    public SubcategoryResponse getSubcategory(@PathVariable Long id) {
        return service.getSubcategory(id);
    }
}
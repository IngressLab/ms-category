package az.ingress.mscategory.service;

import az.ingress.mscategory.annotation.Log;
import az.ingress.mscategory.exception.AlreadyExistsException;
import az.ingress.mscategory.model.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import az.ingress.mscategory.dao.entity.CategoryEntity;
import az.ingress.mscategory.dao.repository.CategoryRepository;
import az.ingress.mscategory.exception.NotExistsException;
import az.ingress.mscategory.model.request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.ingress.mscategory.exception.ExceptionMessages.CATEGORY_ALREADY_EXISTS_ERROR_WITH_NAME;
import static az.ingress.mscategory.mapper.CategoryMapper.CATEGORY_MAPPER;
import static lombok.AccessLevel.PRIVATE;
import static az.ingress.mscategory.exception.ExceptionMessages.CATEGORY_NOT_EXISTS_ERROR_WITH_ID;

@Log
@RequiredArgsConstructor
@Service
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository repository;

    public List<CategoryResponse> getCategories() {
        return repository.findAll()
                .stream()
                .map(CATEGORY_MAPPER::mapEntityToResponse)
                .toList();
    }

    public CategoryResponse getCategory(Long id) {
        return CATEGORY_MAPPER.mapEntityToResponse(getCategoryIfExists(id));
    }

    public void saveCategory(CategoryRequest request) {
        if (isCategoryExists(request.getName())) throw new AlreadyExistsException(CATEGORY_ALREADY_EXISTS_ERROR_WITH_NAME.getMessage().formatted(request.getName()));
        repository.save(CATEGORY_MAPPER.mapRequestToEntity(request));
    }

    public void updateCategory(Long id, CategoryRequest request) {
        var category = getCategoryIfExists(id);
        CATEGORY_MAPPER.partialUpdate(request, category);
        repository.save(category);
    }

    public void deleteCategory(Long id) {
        var category = getCategoryIfExists(id);
        category.setIsVisible(false);
        repository.save(category);
    }

    public CategoryEntity getCategoryReferenceIfExists(Long id) {
        if (!isCategoryExists(id)) throw new NotExistsException(CATEGORY_NOT_EXISTS_ERROR_WITH_ID.getMessage().formatted(id));
        return repository.getReferenceById(id);
    }

    private Boolean isCategoryExists(Long id) {
        return repository.existsById(id);
    }

    private Boolean isCategoryExists(String name) {
        return repository.existsByName(name);
    }

    private CategoryEntity getCategoryIfExists(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotExistsException(CATEGORY_NOT_EXISTS_ERROR_WITH_ID.getMessage().formatted(id)));
    }
}
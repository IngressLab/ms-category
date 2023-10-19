package az.ingress.mscategory.mapper;

import az.ingress.mscategory.model.response.CategoryResponse;
import az.ingress.mscategory.dao.entity.CategoryEntity;
import az.ingress.mscategory.model.request.CategoryRequest;

import static java.util.Optional.ofNullable;
import static az.ingress.mscategory.mapper.SubcategoryMapper.SUBCATEGORY_MAPPER;

public enum CategoryMapper {
    CATEGORY_MAPPER;
    public CategoryEntity mapRequestToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .name(request.getName())
                .isFavorite(request.getIsFavorite())
                .isVisible(true)
                .build();
    }

    public CategoryResponse mapEntityToResponse(CategoryEntity entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isFavorite(entity.getIsFavorite())
                .subcategories(entity.getSubcategories()
                        .stream().map(SUBCATEGORY_MAPPER::mapEntityToResponse).toList())
                .build();
    }

    public void partialUpdate(CategoryRequest request, CategoryEntity entity) {
        ofNullable(request.getName()).ifPresent(entity::setName);
        ofNullable(request.getIsFavorite()).ifPresent(entity::setIsFavorite);
    }
}
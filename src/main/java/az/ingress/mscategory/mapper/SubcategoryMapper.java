package az.ingress.mscategory.mapper;

import az.ingress.mscategory.dao.entity.CategoryEntity;
import az.ingress.mscategory.model.request.SaveSubcategoryRequest;
import az.ingress.mscategory.dao.entity.SubcategoryEntity;
import az.ingress.mscategory.model.request.UpdateSubcategoryRequest;
import az.ingress.mscategory.model.response.SubcategoryResponse;

import static java.util.Optional.ofNullable;

public enum SubcategoryMapper {
    SUBCATEGORY_MAPPER;
    public SubcategoryEntity mapRequestToEntity(SaveSubcategoryRequest request, CategoryEntity entity) {
        return SubcategoryEntity.builder()
                .category(entity)
                .name(request.getName())
                .isFavorite(request.getIsFavorite())
                .isVisible(true)
                .build();
    }

    public SubcategoryResponse mapEntityToResponse(SubcategoryEntity entity) {
        return SubcategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .isFavorite(entity.getIsFavorite())
                .build();
    }

    public void partialUpdate(UpdateSubcategoryRequest request, SubcategoryEntity entity) {
        ofNullable(request.getName()).ifPresent(entity::setName);
        ofNullable(request.getIsFavorite()).ifPresent(entity::setIsFavorite);
    }
}
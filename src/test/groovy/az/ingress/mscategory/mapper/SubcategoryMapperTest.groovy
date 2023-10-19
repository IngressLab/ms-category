package az.ingress.mscategory.mapper

import az.ingress.mscategory.dao.entity.CategoryEntity
import az.ingress.mscategory.dao.entity.SubcategoryEntity
import az.ingress.mscategory.model.request.SaveSubcategoryRequest
import az.ingress.mscategory.model.request.UpdateSubcategoryRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import static az.ingress.mscategory.mapper.SubcategoryMapper.SUBCATEGORY_MAPPER

class SubcategoryMapperTest extends Specification {
    def random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "testMapRequestToEntity(subcategoryRequest)"() {
        given:
        def request = random.nextObject(SaveSubcategoryRequest)
        def categoryEntity = random.nextObject(CategoryEntity)

        when:
        def entity = SUBCATEGORY_MAPPER.mapRequestToEntity(request, categoryEntity)

        then:
        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.isVisible == true
        entity.category.id == categoryEntity.id
    }

    def "testMapEntityToResponse(subcategoryEntity)"() {
        given:
        def entity = random.nextObject(SubcategoryEntity)

        when:
        def response = SUBCATEGORY_MAPPER.mapEntityToResponse(entity)

        then:
        response.id == entity.id
        response.name == entity.name
        response.isFavorite == entity.isFavorite
    }

    def "testPartialUpdate(subcategoryRequest, subcategoryEntity)"() {
        given:
        def request = random.nextObject(UpdateSubcategoryRequest)
        def entity = random.nextObject(SubcategoryEntity)

        when:
        SUBCATEGORY_MAPPER.partialUpdate(request, entity)

        then:
        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.isVisible == true
    }
}
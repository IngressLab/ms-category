package az.ingress.mscategory.mapper

import az.ingress.mscategory.dao.entity.CategoryEntity
import az.ingress.mscategory.model.request.CategoryRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import static az.ingress.mscategory.mapper.CategoryMapper.CATEGORY_MAPPER

class CategoryMapperTest extends Specification {
    private def random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "testMapRequestToEntity(categoryRequest)"() {
        given:
        def request = random.nextObject(CategoryRequest)

        when:
        def entity = CATEGORY_MAPPER.mapRequestToEntity(request)

        then:
        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.isVisible == true
    }

    def "testMapEntityToResponse(categoryEntity)"() {
        given:
        def entity = random.nextObject(CategoryEntity)

        when:
        def response = CATEGORY_MAPPER.mapEntityToResponse(entity)

        then:
        response.id == entity.id
        response.name == entity.name
        response.isFavorite == entity.isFavorite
    }

    def "testPartialUpdate(categoryRequest, categoryEntity)"() {
        given:
        def request = random.nextObject(CategoryRequest)
        def entity = random.nextObject(CategoryEntity)

        when:
        CATEGORY_MAPPER.partialUpdate(request, entity)

        then:
        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.isVisible == true
    }
}
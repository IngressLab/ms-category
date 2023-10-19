package az.ingress.mscategory.service

import az.ingress.mscategory.dao.entity.CategoryEntity
import az.ingress.mscategory.dao.entity.SubcategoryEntity
import az.ingress.mscategory.dao.repository.SubcategoryRepository
import az.ingress.mscategory.exception.AlreadyExistsException
import az.ingress.mscategory.exception.NotExistsException
import az.ingress.mscategory.model.request.SaveSubcategoryRequest
import az.ingress.mscategory.model.request.UpdateSubcategoryRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import static az.ingress.mscategory.exception.ExceptionMessages.CATEGORY_NOT_EXISTS_ERROR_WITH_ID
import static az.ingress.mscategory.mapper.SubcategoryMapper.SUBCATEGORY_MAPPER

class SubcategoryServiceTest extends Specification {
    def random = EnhancedRandomBuilder.aNewEnhancedRandom()
    SubcategoryRepository repository
    CategoryService categoryService
    SubcategoryService service

    def setup() {
        repository = Mock()
        categoryService = Mock()
        service = new SubcategoryService(repository, categoryService)
    }

    def "testGetSubcategory(id) success"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(SubcategoryEntity)

        when:
        def response = service.getSubcategory(id)

        then:
        1 * repository.findById(id) >> Optional.of(entity)

        response.id == entity.id
        response.name == entity.name
        response.isFavorite == entity.isFavorite
    }

    def "testGetSubcategory(id) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()

        when:
        service.getSubcategory(id)

        then:
        1 * repository.findById(id) >> Optional.empty()

        def e = thrown(NotExistsException)
        e.message == "Subcategory does not exists with ID: $id"
    }

    def "testSaveSubcategory(saveSubcategoryRequest) success"() {
        given:
        def request = random.nextObject(SaveSubcategoryRequest)
        def reference = random.nextObject(CategoryEntity)
        def entity = SUBCATEGORY_MAPPER.mapRequestToEntity(request, reference)

        when:
        service.saveSubcategory(request)

        then:
        1 * categoryService.getCategoryReferenceIfExists(request.categoryId) >> reference
        1 * repository.save(entity)

        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.category.id == reference.id
    }

    def "testSaveSubcategory(saveSubcategoryRequest) error(ALREADY_EXISTS)"() {
        given:
        def request = random.nextObject(SaveSubcategoryRequest)

        when:
        service.saveSubcategory(request)

        then:
        1 * repository.existsByName(request.name) >> true
        0 * categoryService.getCategoryReferenceIfExists()
        0 * repository.save()

        def e = thrown(AlreadyExistsException)
        e.message == "Subcategory already exists with name: $request.name"
    }

    def "testSaveSubcategory(saveSubcategoryRequest) error(NOT_EXISTS)"() {
        given:
        def request = random.nextObject(SaveSubcategoryRequest)

        when:
        service.saveSubcategory(request)

        then:
        1 * repository.existsByName(request.name) >> false
        1 * categoryService.getCategoryReferenceIfExists(request.categoryId) >>{
            throw new NotExistsException(CATEGORY_NOT_EXISTS_ERROR_WITH_ID.getMessage().formatted(request.categoryId))
        }
        0 * repository.save()

        def e = thrown(NotExistsException)
        e.message == "Category does not exists with ID: $request.categoryId"
    }

    def "testUpdateSubcategory(id, updateSubcategoryRequest) success"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(UpdateSubcategoryRequest)
        def entity = random.nextObject(SubcategoryEntity)

        when:
        service.updateSubcategory(id, request)

        then:
        1 * repository.findById(id) >> Optional.of(entity)
        1 * repository.save(entity)

        entity.name == request.name
        entity.isFavorite == request.isFavorite
    }

    def "testUpdateSubcategory(id, updateSubcategoryRequest) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(UpdateSubcategoryRequest)

        when:
        service.updateSubcategory(id, request)

        then:
        1 * repository.findById(id) >> Optional.empty()
        0 * repository.save()

        def e = thrown(NotExistsException)
        e.message == "Subcategory does not exists with ID: $id"
    }

    def "testDeleteSubcategory(id) success"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(SubcategoryEntity)

        when:
        service.deleteSubcategory(id)

        then:
        1 * repository.findById(id) >> Optional.of(entity)
        1 * repository.save(entity)

        entity.isVisible == false
    }

    def "testDeleteSubcategory(id) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()

        when:
        service.deleteSubcategory(id)
        repository.save()

        then:
        1 * repository.findById(id) >> Optional.empty()
        0 * repository.save()

        def e = thrown(NotExistsException)
        e.message == "Subcategory does not exists with ID: $id"
    }
}
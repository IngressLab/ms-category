package az.ingress.mscategory.service

import az.ingress.mscategory.dao.entity.CategoryEntity
import az.ingress.mscategory.dao.repository.CategoryRepository
import az.ingress.mscategory.exception.AlreadyExistsException
import az.ingress.mscategory.exception.NotExistsException
import az.ingress.mscategory.model.request.CategoryRequest
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import static az.ingress.mscategory.mapper.CategoryMapper.CATEGORY_MAPPER

class CategoryServiceTest extends Specification {
    def random = EnhancedRandomBuilder.aNewEnhancedRandom()
    CategoryRepository repository
    CategoryService service

    def setup() {
        repository = Mock()
        service = new CategoryService(repository)
    }

    def "testGetCategories()"() {
        given:
        def categories = random.nextObject(List<CategoryEntity>)

        when:
        service.getCategories()

        then:
        1 * repository.findAll() >> categories
    }

    def "testGetCategory(id) success"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(CategoryEntity)

        when:
        def response = service.getCategory(id)

        then:
        1 * repository.findById(id) >> Optional.of(entity)

        response.id == entity.id
        response.name == entity.name
        response.isFavorite == entity.isFavorite
    }

    def "testGetCategory(id) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()

        when:
        service.getCategory(id)

        then:
        1 * repository.findById(id) >> Optional.empty()

        def e = thrown(NotExistsException)
        e.message == "Category does not exists with ID: $id"
    }

    def "testSaveCategory(categoryRequest) success"() {
        given:
        def request = random.nextObject(CategoryRequest)
        def entity = CATEGORY_MAPPER.mapRequestToEntity(request)

        when:
        service.saveCategory(request)

        then:
        1 * repository.existsByName(request.name) >> false
        1 * repository.save(entity)

        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.isVisible == true
    }

    def "testSaveCategory(categoryRequest) error(ALREADY_EXISTS)"() {
        given:
        def request = random.nextObject(CategoryRequest)

        when:
        service.saveCategory(request)

        then:
        1 * repository.existsByName(request.name) >> true
        0 * repository.save()

        def e = thrown(AlreadyExistsException)
        e.message == "Category already exists with name: $request.name"
    }

    def "testUpdateCategory(id, categoryRequest) success"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(CategoryRequest)
        def entity = random.nextObject(CategoryEntity)

        when:
        service.updateCategory(id, request)

        then:
        1 * repository.findById(id) >> Optional.of(entity)
        1 * repository.save(entity)

        entity.name == request.name
        entity.isFavorite == request.isFavorite
        entity.isVisible == true
    }

    def "testUpdateCategory(id, categoryRequest) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(CategoryRequest)

        when:
        service.updateCategory(id, request)

        then:
        1 * repository.findById(id) >> Optional.empty()
        0 * repository.save()

        def e = thrown(NotExistsException)
        e.message == "Category does not exists with ID: $id"
    }

    def "testDeleteCategory(id) success"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(CategoryEntity)

        when:
        service.deleteCategory(id)

        then:
        1 * repository.findById(id) >> Optional.of(entity)
        1 * repository.save(entity)

        entity.isVisible == false
    }

    def "testDeleteCategory(id, categoryRequest) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()

        when:
        service.deleteCategory(id)

        then:
        1 * repository.findById(id) >> Optional.empty()
        0 * repository.save()

        def e = thrown(NotExistsException)
        e.message == "Category does not exists with ID: $id"
    }

    def "testGetCategoryReferenceIfExists(id) success"() {
        given:
        def id = random.nextLong()
        def entity = random.nextObject(CategoryEntity)

        when:
        def response = service.getCategoryReferenceIfExists(id)

        then:
        1 * repository.existsById(id) >> true
        1 * repository.getReferenceById(id) >> entity

        response.id == entity.id
        response.name == entity.name
        response.isFavorite == entity.isFavorite
    }

    def "testGetCategoryReferenceIfExists(id) error(NOT_EXISTS)"() {
        given:
        def id = random.nextLong()

        when:
        service.getCategoryReferenceIfExists(id)

        then:
        1 * repository.existsById(id) >> false
        0 * repository.getReferenceById()

        def e = thrown(NotExistsException)
        e.message == "Category does not exists with ID: $id"
    }
}
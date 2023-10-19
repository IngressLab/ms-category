package az.ingress.mscategory.controller

import az.ingress.mscategory.exception.ErrorHandler
import az.ingress.mscategory.model.response.CategoryResponse
import az.ingress.mscategory.model.response.SubcategoryResponse
import az.ingress.mscategory.service.CategoryService
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class CategoryControllerTest extends Specification {
    CategoryService service
    MockMvc mockMvc
    def random = aNewEnhancedRandom()

    def setup() {
        service = Mock()
        def controller = new CategoryController(service)
        mockMvc = standaloneSetup(controller).setControllerAdvice(new ErrorHandler()).build()
    }

    def "testGetCategories()"() {
        given:
        def subcategory = random.nextObject(SubcategoryResponse)
        def category = new CategoryResponse(random.nextLong(), random.nextObject(String), random.nextBoolean(), [subcategory])
        def response = [category]
        def url = "/v1/categories"

        def expectedResponse = """
          [
            {
              "id": $category.id,
              "name": "$category.name",
              "isFavorite": $category.isFavorite,
              "subcategories": [
                {
                  "id": $subcategory.id,
                  "name": "$subcategory.name",
                  "isFavorite": $subcategory.isFavorite
                }
              ]
            }
          ]
        """

        when:
        def actualResponse = mockMvc.perform(get(url)).andReturn()

        then:
        1 * service.getCategories() >> response
        actualResponse.response.status == OK.value()
        assertEquals(expectedResponse, actualResponse.response.contentAsString, true)
    }

    def "testGetCategory()"() {
        given:
        def id = random.nextLong()
        def subcategory = random.nextObject(SubcategoryResponse)
        def response = new CategoryResponse(id, random.nextObject(String), random.nextBoolean(), [subcategory])
        def url = "/v1/categories/$id"

        def expectedResponse = """
          {
            "id": $response.id,
            "name": "$response.name",
            "isFavorite": $response.isFavorite,
            "subcategories": [
              {
                "id": $subcategory.id,
                "name": "$subcategory.name",
                "isFavorite": $subcategory.isFavorite
              }
            ]
          }
        """

        when:
        def actualResponse = mockMvc.perform(get(url)).andReturn()

        then:
        1 * service.getCategory(id) >> response
        actualResponse.response.status == OK.value()
        assertEquals(expectedResponse, actualResponse.response.contentAsString, true)
    }
}
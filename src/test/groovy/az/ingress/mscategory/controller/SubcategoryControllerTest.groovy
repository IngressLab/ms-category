package az.ingress.mscategory.controller

import az.ingress.mscategory.exception.ErrorHandler
import az.ingress.mscategory.model.response.SubcategoryResponse
import az.ingress.mscategory.service.SubcategoryService
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class SubcategoryControllerTest extends Specification {
    SubcategoryService service
    MockMvc mockMvc
    def random = aNewEnhancedRandom()

    def setup() {
        service = Mock()
        def controller = new SubcategoryController(service)
        mockMvc = standaloneSetup(controller).setControllerAdvice(new ErrorHandler()).build()
    }

    def "testGetSubcategory(id)"() {
        given:
        def id = random.nextLong()
        def response = random.nextObject(SubcategoryResponse)
        def url = "/v1/subcategories/$id"

        def expectedResponse = """
          {
            "id": $response.id,
            "name": "$response.name",
            "isFavorite": $response.isFavorite
          }
        """

        when:
        def actualResponse = mockMvc.perform(get(url)).andReturn()

        then:
        1 * service.getSubcategory(id) >> response
        actualResponse.response.status == OK.value()
        assertEquals(expectedResponse, actualResponse.response.contentAsString, true)
    }
}
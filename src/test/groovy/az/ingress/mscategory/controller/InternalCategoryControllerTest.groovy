package az.ingress.mscategory.controller

import az.ingress.mscategory.exception.ErrorHandler
import az.ingress.mscategory.model.request.CategoryRequest
import az.ingress.mscategory.service.CategoryService
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class InternalCategoryControllerTest extends Specification {
    CategoryService service
    MockMvc mockMvc
    def random = aNewEnhancedRandom()

    def setup() {
        service = Mock()
        def controller = new InternalCategoryController(service)
        mockMvc = standaloneSetup(controller).setControllerAdvice(new ErrorHandler()).build()
    }

    def "testSaveCategory(categoryRequest)"() {
        given:
        def request = random.nextObject(CategoryRequest)
        def requestBody = """
          {
            "name": "$request.name",
            "isFavorite": $request.isFavorite
          }
        """

        def url = "/internal/v1/categories"

        when:
        mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(requestBody)).andExpect(status().isCreated()).andReturn()

        then:
        1 * service.saveCategory(request)
    }

    def "testUpdateCategory(id, categoryRequest)"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(CategoryRequest)
        def requestBody = """
            {
                "name": "$request.name",
                "isFavorite": $request.isFavorite
            }
        """

        def url = "/internal/v1/categories/$id"

        when:
        mockMvc.perform(put(url).contentType(APPLICATION_JSON).content(requestBody)).andExpect(status().isNoContent()).andReturn()

        then:
        1 * service.updateCategory(id, request)
    }

    def "testDeleteCategory(id)"() {
        given:
        def id = random.nextLong()
        def url = "/internal/v1/categories/$id"

        when:
        mockMvc.perform(delete(url)).andExpect(status().isNoContent()).andReturn()

        then:
        1 * service.deleteCategory(id)
    }
}
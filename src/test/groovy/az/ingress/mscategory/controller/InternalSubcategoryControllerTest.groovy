package az.ingress.mscategory.controller

import az.ingress.mscategory.exception.ErrorHandler
import az.ingress.mscategory.model.request.SaveSubcategoryRequest
import az.ingress.mscategory.model.request.UpdateSubcategoryRequest
import az.ingress.mscategory.service.SubcategoryService
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class InternalSubcategoryControllerTest extends Specification {
    SubcategoryService service
    MockMvc mockMvc
    def random = aNewEnhancedRandom()

    def setup() {
        service = Mock()
        def controller = new InternalSubcategoryController(service)
        mockMvc = standaloneSetup(controller).setControllerAdvice(new ErrorHandler()).build()
    }

    def "testSaveSubcategory(saveSubcategoryRequest)"() {
        given:
        def request = random.nextObject(SaveSubcategoryRequest)
        def requestBody = """
            {
                "categoryId": $request.categoryId,
                "name": "$request.name",
                "isFavorite": $request.isFavorite
            }
        """

        def url = "/internal/v1/subcategories"

        when:
        mockMvc.perform(post(url).contentType(APPLICATION_JSON).content(requestBody)).andExpect(status().isCreated()).andReturn()

        then:
        1 * service.saveSubcategory(request)
    }

    def "testUpdateSubcategory(id, updateSubcategoryRequest)"() {
        given:
        def id = random.nextLong()
        def request = random.nextObject(UpdateSubcategoryRequest)
        def requestBody = """
            {
                "name": "$request.name",
                "isFavorite": $request.isFavorite
            }
        """

        def url = "/internal/v1/subcategories/$id"

        when:
        mockMvc.perform(put(url).contentType(APPLICATION_JSON).content(requestBody)).andExpect(status().isNoContent()).andReturn()

        then:
        1 * service.updateSubcategory(id, request)
    }

    def "testDeleteSubcategory(id)"() {
        given:
        def id = random.nextLong()
        def url = "/internal/v1/subcategories/$id"

        when:
        mockMvc.perform(delete(url)).andExpect(status().isNoContent()).andReturn()

        then:
        1 * service.deleteSubcategory(id)
    }
}
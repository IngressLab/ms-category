package az.ingress.mscategory.service;

import az.ingress.mscategory.annotation.Log;
import az.ingress.mscategory.exception.AlreadyExistsException;
import az.ingress.mscategory.model.request.UpdateSubcategoryRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import az.ingress.mscategory.dao.entity.SubcategoryEntity;
import az.ingress.mscategory.dao.repository.SubcategoryRepository;
import az.ingress.mscategory.exception.NotExistsException;
import az.ingress.mscategory.model.response.SubcategoryResponse;
import az.ingress.mscategory.model.request.SaveSubcategoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static az.ingress.mscategory.exception.ExceptionMessages.SUBCATEGORY_ALREADY_EXISTS_ERROR_WITH_NAME;
import static az.ingress.mscategory.exception.ExceptionMessages.SUBCATEGORY_NOT_EXISTS_ERROR_WITH_ID;
import static lombok.AccessLevel.PRIVATE;
import static az.ingress.mscategory.mapper.SubcategoryMapper.SUBCATEGORY_MAPPER;

@Log
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SubcategoryService {

    SubcategoryRepository repository;
    CategoryService categoryService;

    public SubcategoryResponse getSubcategory(Long id) {
        return SUBCATEGORY_MAPPER.mapEntityToResponse(getSubcategoryIfExists(id));
    }

    public void saveSubcategory(SaveSubcategoryRequest request) {
        if (isSubcategoryExists(request.getName())) throw new AlreadyExistsException(SUBCATEGORY_ALREADY_EXISTS_ERROR_WITH_NAME.getMessage().formatted(request.getName()));
        repository.save(SUBCATEGORY_MAPPER.mapRequestToEntity(
                request,
                categoryService.getCategoryReferenceIfExists(request.getCategoryId())
        ));
    }

    public void updateSubcategory(@Log.Exclude Long id, UpdateSubcategoryRequest request) {
        var entity = getSubcategoryIfExists(id);
        SUBCATEGORY_MAPPER.partialUpdate(request, entity);
        repository.save(entity);
    }

    public void deleteSubcategory(Long id) {
        var entity = getSubcategoryIfExists(id);
        entity.setIsVisible(false);
        repository.save(entity);
    }

    private Boolean isSubcategoryExists(String name) {
        return repository.existsByName(name);
    }

    private SubcategoryEntity getSubcategoryIfExists(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotExistsException(SUBCATEGORY_NOT_EXISTS_ERROR_WITH_ID.getMessage().formatted(id)));
    }
}
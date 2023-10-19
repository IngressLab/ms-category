package az.ingress.mscategory.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum ExceptionMessages {
    UNEXPECTED_ERROR("Unexpected error occurred!"),
    CATEGORY_NOT_EXISTS_ERROR_WITH_ID("Category does not exists with ID: %d"),
    SUBCATEGORY_NOT_EXISTS_ERROR_WITH_ID("Subcategory does not exists with ID: %d"),
    CATEGORY_ALREADY_EXISTS_ERROR_WITH_NAME("Category already exists with name: %s"),
    SUBCATEGORY_ALREADY_EXISTS_ERROR_WITH_NAME("Subcategory already exists with name: %s");
    String message;
}
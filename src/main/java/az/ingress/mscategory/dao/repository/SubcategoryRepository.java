package az.ingress.mscategory.dao.repository;

import az.ingress.mscategory.dao.entity.SubcategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface SubcategoryRepository extends CrudRepository<SubcategoryEntity, Long> {
    boolean existsByName(String name);
}
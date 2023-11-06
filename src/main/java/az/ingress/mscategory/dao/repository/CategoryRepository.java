package az.ingress.mscategory.dao.repository;

import az.ingress.mscategory.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Boolean existsByName(String name);

    @EntityGraph(value = "categories")
    @Override
    List<CategoryEntity> findAll();

    @EntityGraph(value = "categories")
    @Override
    Optional<CategoryEntity> findById(Long id);
}
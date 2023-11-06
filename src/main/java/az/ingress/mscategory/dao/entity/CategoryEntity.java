package az.ingress.mscategory.dao.entity;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "categories")
@NamedEntityGraph(name = "categories", attributeNodes = {@NamedAttributeNode(value = "subcategories")})
@FieldDefaults(level = PRIVATE)
@Where(clause = "is_visible = 'true'")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    String name;

    @Builder.Default
    Boolean isVisible = true;

    @Builder.Default
    Boolean isFavorite = false;

    @CreationTimestamp
    LocalDateTime updatedAt;

    @UpdateTimestamp
    LocalDateTime createdAt;

    @OneToMany(cascade = PERSIST, mappedBy = "category")
    @ToString.Exclude
    List<SubcategoryEntity> subcategories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity categoryEntity = (CategoryEntity) o;
        return Objects.equals(id, categoryEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
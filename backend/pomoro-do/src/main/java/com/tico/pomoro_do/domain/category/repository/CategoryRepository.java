package com.tico.pomoro_do.domain.category.repository;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByOwnerAndTypeAndDeletedIsFalse(User owner, CategoryType type);

    Optional<Category> findById(Long categoryId);

    List<Category> findByDate(LocalDate date);
}

package com.tico.pomoro_do.domain.category.validator;

import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.repository.CategoryRepository;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public Category validateExists(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("카테고리를 찾을 수 없습니다. ID: {}", categoryId);
                    return new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
                });
    }
}

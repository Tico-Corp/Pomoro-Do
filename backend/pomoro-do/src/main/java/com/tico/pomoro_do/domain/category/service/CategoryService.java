package com.tico.pomoro_do.domain.category.service;

import com.tico.pomoro_do.domain.category.dto.request.CategoryDTO;

public interface CategoryService {

    void createCategory(String hostName, CategoryDTO categoryDTO);
}

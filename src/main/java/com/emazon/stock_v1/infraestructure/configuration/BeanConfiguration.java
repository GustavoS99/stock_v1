package com.emazon.stock_v1.infraestructure.configuration;

import com.emazon.stock_v1.domain.api.IFindAllCategoriesServicePort;
import com.emazon.stock_v1.domain.api.ISaveCategoryServicePort;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.domain.usecase.FindAllCategoriesUseCase;
import com.emazon.stock_v1.domain.usecase.SaveCategoryUseCase;
import com.emazon.stock_v1.infraestructure.out.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ISaveCategoryServicePort saveCategoryServicePort() {
        return new SaveCategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IFindAllCategoriesServicePort findAllCategoriesServicePort() {
        return new FindAllCategoriesUseCase(categoryPersistencePort());
    }
}

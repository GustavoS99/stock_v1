package com.emazon.stock_v1.infraestructure.configuration;

import com.emazon.stock_v1.domain.api.IFindAllBrandsServicePort;
import com.emazon.stock_v1.domain.api.IFindAllCategoriesServicePort;
import com.emazon.stock_v1.domain.api.ISaveBrandServicePort;
import com.emazon.stock_v1.domain.api.ISaveCategoryServicePort;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.domain.usecase.FindAllBrandsUseCase;
import com.emazon.stock_v1.domain.usecase.FindAllCategoriesUseCase;
import com.emazon.stock_v1.domain.usecase.SaveBrandUseCase;
import com.emazon.stock_v1.domain.usecase.SaveCategoryUseCase;
import com.emazon.stock_v1.infraestructure.out.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock_v1.infraestructure.out.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

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

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public ISaveBrandServicePort saveBrandServicePort() {
        return new SaveBrandUseCase(brandPersistencePort());
    }

    @Bean
    public IFindAllBrandsServicePort findAllBrandsServicePort() {
        return new FindAllBrandsUseCase(brandPersistencePort());
    }
}

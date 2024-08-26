package com.emazon.stock_v1.infraestructure.configuration;

import com.emazon.stock_v1.domain.api.*;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.domain.spi.ICategoryPersistencePort;
import com.emazon.stock_v1.domain.spi.IItemPersistencePort;
import com.emazon.stock_v1.domain.usecase.*;
import com.emazon.stock_v1.infraestructure.out.jpa.adapter.BrandJpaAdapter;
import com.emazon.stock_v1.infraestructure.out.jpa.adapter.CategoryJpaAdapter;
import com.emazon.stock_v1.infraestructure.out.jpa.adapter.ItemJpaAdapter;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.BrandEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.CategoryEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.mapper.ItemEntityMapper;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IBrandRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.ICategoryRepository;
import com.emazon.stock_v1.infraestructure.out.jpa.repository.IItemRepository;
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
    private final IItemRepository itemRepository;
    private final ItemEntityMapper itemEntityMapper;

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

    @Bean
    public IItemPersistencePort itemPersistencePort(){
        return new ItemJpaAdapter(
                itemRepository, brandRepository, categoryRepository,
                itemEntityMapper, brandEntityMapper, categoryEntityMapper);
    }

    @Bean
    public ISaveItemServicePort saveItemServicePort() {
        return new SaveItemUseCase(itemPersistencePort());
    }
}

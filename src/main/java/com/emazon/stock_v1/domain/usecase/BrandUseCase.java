package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.IBrandServicePort;
import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.utils.GlobalConstants;

import java.util.List;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void save(Brand brand) {
        validateNameInput(brand);

        validateDescriptionInput(brand);

        brandPersistencePort.save(brand);
    }

    private void validateDescriptionInput(Brand brand) {
        if(brand.getDescription() == null || brand.getDescription().trim().isEmpty())
            throw new EmptyBrandDescriptionException();

        brand.setDescription(brand.getDescription().trim());

        if(brand.getDescription().length() > GlobalConstants.LENGTH_BRAND_DESCRIPTION)
            throw new InvalidLengthBrandDescriptionException();
    }

    private void validateNameInput(Brand brand) {
        if(brand.getName() == null || brand.getName().trim().isEmpty())
            throw new EmptyBrandNameException();

        brand.setName(brand.getName().trim());

        if(brand.getName().length() > GlobalConstants.LENGTH_BRAND_NAME)
            throw new InvalidLengthBrandNameException();
    }

    @Override
    public PaginatedResult<Brand> findAll(PaginationRequest paginationRequest, String sortDirection) {
        if(paginationRequest.getPage() < GlobalConstants.START_PAGE
                || paginationRequest.getSize() <= GlobalConstants.START_PAGE_SIZE) {
            throw new InvalidPaginationParametersException();
        }

        List<Brand> brands = brandPersistencePort.findAll(sortDirection);

        List<Brand> paginatedBrands = getPaginated(brands, paginationRequest.getPage(), paginationRequest.getSize());

        int totalPages = (int) Math.ceil((double) brands.size() / paginationRequest.getSize());

        return new PaginatedResult<>(paginatedBrands, paginationRequest.getPage(), totalPages, brands.size());
    }

    private List<Brand> getPaginated(List<Brand> brands, int page, int size) {

        int start = page * size;
        int end = Math.min(start + size, brands.size());

        return brands.subList(start, end);
    }
}

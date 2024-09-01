package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.IBrandServicePort;
import com.emazon.stock_v1.domain.exception.*;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.model.PaginatedResult;
import com.emazon.stock_v1.domain.model.PaginationRequest;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.domain.exception.BrandAlreadyExistsException;
import com.emazon.stock_v1.domain.exception.BrandsNotFoundException;
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

        validateIfBrandExists(brand.getName());

        brandPersistencePort.save(brand);
    }

    private void validateIfBrandExists(String name) {
        if (brandPersistencePort.findByName(name).isPresent()) {
            throw new BrandAlreadyExistsException();
        }
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

        List<Brand> brands = validateIfListIsEmpty(brandPersistencePort.findAll(sortDirection));

        int totalPages = (int) Math.ceil((double) brands.size() / paginationRequest.getSize());

        List<Brand> paginatedBrands = getPaginated(
                brands, validatePage(paginationRequest.getPage(), totalPages), paginationRequest.getSize());

        return new PaginatedResult<>(paginatedBrands, paginationRequest.getPage(), totalPages, brands.size());
    }

    private List<Brand> validateIfListIsEmpty(List<Brand> all) {
        if(all.isEmpty())
            throw new BrandsNotFoundException();

        return all;
    }

    private int validatePage(int page, int totalPages) {

        if(page >= totalPages)
            throw new PageExceedTotalPagesException();

        return page;
    }

    private List<Brand> getPaginated(List<Brand> brands, int page, int size) {

        int start = page * size;
        int end = Math.min(start + size, brands.size());

        return brands.subList(start, end);
    }
}

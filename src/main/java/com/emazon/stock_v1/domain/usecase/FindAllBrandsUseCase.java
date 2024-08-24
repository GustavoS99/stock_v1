package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.api.IFindAllBrandsServicePort;
import com.emazon.stock_v1.domain.exception.InvalidPaginationParametersException;
import com.emazon.stock_v1.domain.model.Brand;
import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.helpers.GlobalConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FindAllBrandsUseCase implements IFindAllBrandsServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public FindAllBrandsUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Page<Brand> findAll(int page, int size, String sortDirection) {
        if(page < GlobalConstants.START_PAGE || size <= GlobalConstants.START_PAGE)
            throw new InvalidPaginationParametersException();

        Pageable pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.BRAND_SORT_BY).ascending());
        if(sortDirection.equalsIgnoreCase(GlobalConstants.DESCENDING_SORT))
            pageable = PageRequest.of(page, size, Sort.by(GlobalConstants.BRAND_SORT_BY).descending());

        return brandPersistencePort.findAll(pageable);
    }
}

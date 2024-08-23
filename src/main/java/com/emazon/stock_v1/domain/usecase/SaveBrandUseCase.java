package com.emazon.stock_v1.domain.usecase;

import com.emazon.stock_v1.domain.spi.IBrandPersistencePort;
import com.emazon.stock_v1.helpers.GlobalConstants;
import com.emazon.stock_v1.domain.api.ISaveBrandServicePort;
import com.emazon.stock_v1.domain.exception.EmptyBrandDescriptionException;
import com.emazon.stock_v1.domain.exception.EmptyBrandNameException;
import com.emazon.stock_v1.domain.exception.InvalidLengthBrandDescriptionException;
import com.emazon.stock_v1.domain.exception.InvalidLengthBrandNameException;
import com.emazon.stock_v1.domain.model.Brand;

public class SaveBrandUseCase implements ISaveBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public SaveBrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void save(Brand brand) {
        validateNameInput(brand);

        validateDescriptionInput(brand);

        brandPersistencePort.save(brand);
    }

    private void validateDescriptionInput(Brand brand) {
        if(brand.getDescription() == null || brand.getDescription().isEmpty())
            throw new EmptyBrandDescriptionException();

        brand.setDescription(brand.getDescription().trim());

        if(brand.getDescription().isEmpty()
                || brand.getDescription().length() > GlobalConstants.LENGTH_BRAND_DESCRIPTION)
            throw new InvalidLengthBrandDescriptionException();
    }

    private void validateNameInput(Brand brand) {
        if(brand.getName() == null || brand.getName().isEmpty())
            throw new EmptyBrandNameException();

        brand.setName(brand.getName().trim());

        if(brand.getName().isEmpty() || brand.getName().length() > GlobalConstants.LENGTH_BRAND_NAME)
            throw new InvalidLengthBrandNameException();
    }
}

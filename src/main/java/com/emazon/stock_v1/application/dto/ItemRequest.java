package com.emazon.stock_v1.application.dto;

import com.emazon.stock_v1.utils.GlobalConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ItemRequest {
    @NotEmpty(message = GlobalConstants.EMPTY_NAME_MESSAGE)
    @NotNull(message = GlobalConstants.EMPTY_NAME_MESSAGE)
    @Size(
            min = GlobalConstants.MIN_LEN_NAME,
            max = GlobalConstants.LENGTH_ITEM_NAME,
            message = GlobalConstants.BAD_NAME_LENGTH_MESSAGE
    )
    private String name;
    @NotEmpty(message = GlobalConstants.EMPTY_DESCRIPTION_MESSAGE)
    @NotNull(message = GlobalConstants.EMPTY_DESCRIPTION_MESSAGE)
    @Size(
            min = GlobalConstants.MIN_LEN_DESCRIPTION,
            max = GlobalConstants.LENGTH_ITEM_DESCRIPTION,
            message = GlobalConstants.BAD_ITEM_DESCRIPTION_LENGTH
    )
    private String description;
    private long quantity;
    private BigDecimal price;
    @NotNull(message = GlobalConstants.EMPTY_BRAND_OF_ITEM_MESSAGE)
    private BrandRequest brandRequest;
    @NotNull(message = GlobalConstants.EMPTY_CATEGORIES_OF_ITEM_MESSAGE)
    private Set<CategoryRequest> categoryRequests;
}

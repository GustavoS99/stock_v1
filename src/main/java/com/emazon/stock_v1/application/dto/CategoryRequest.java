package com.emazon.stock_v1.application.dto;

import com.emazon.stock_v1.utils.GlobalConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class CategoryRequest {
    @NotEmpty(message = GlobalConstants.EMPTY_NAME_MESSAGE)
    @NotNull(message = GlobalConstants.NULL_NAME_MESSAGE)
    @Size(
            min = GlobalConstants.MIN_LEN_NAME,
            max = GlobalConstants.LENGTH_CATEGORY_NAME,
            message = GlobalConstants.BAD_NAME_LENGTH_MESSAGE)
    private String name;
    @NotEmpty(message = GlobalConstants.EMPTY_DESCRIPTION_MESSAGE)
    @NotNull(message = GlobalConstants.NULL_DESCRIPTION_MESSAGE)
    @Size(
            min = GlobalConstants.MIN_LEN_DESCRIPTION,
            max = GlobalConstants.LENGTH_CATEGORY_DESCRIPTION,
            message = GlobalConstants.BAD_CATEGORY_DESCRIPTION_LENGTH_MESSAGE)
    private String description;
}

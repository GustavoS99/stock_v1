package com.emazon.stock_v1.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemUpdateQuantityRequest {
    private Long id;
    private Long quantity;
}

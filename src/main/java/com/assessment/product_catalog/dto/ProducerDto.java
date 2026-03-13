package com.assessment.product_catalog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProducerDto {
    private Long id;
    private String name;
}

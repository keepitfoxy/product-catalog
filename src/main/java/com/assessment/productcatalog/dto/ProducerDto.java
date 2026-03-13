package com.assessment.productcatalog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProducerDto {
    private Long id;
    private String name;
}

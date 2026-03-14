package com.assessment.product_catalog.controller;

import com.assessment.product_catalog.dto.ProducerCreateDto;
import com.assessment.product_catalog.dto.ProducerDto;
import com.assessment.product_catalog.service.ProducerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/producers")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @GetMapping
    public List<ProducerDto> getProducers() {
        return producerService.getAllProducers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProducerDto createProducer(@Valid @RequestBody ProducerCreateDto dto) {
        return producerService.createProducer(dto);
    }
}
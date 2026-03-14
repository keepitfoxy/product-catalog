package com.assessment.product_catalog.service;

import com.assessment.product_catalog.dto.ProducerCreateDto;
import com.assessment.product_catalog.dto.ProducerDto;
import com.assessment.product_catalog.model.Producer;
import com.assessment.product_catalog.repository.ProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerRepository producerRepository;

    public List<ProducerDto> getAllProducers() {
        return producerRepository.findAll().stream()
                .map(producer -> ProducerDto.builder()
                        .id(producer.getId())
                        .name(producer.getName())
                        .build())
                .collect(Collectors.toList());
    }

    public ProducerDto createProducer(ProducerCreateDto dto) {
        Producer producer = new Producer();
        producer.setName(dto.getName());

        Producer savedProducer = producerRepository.save(producer);

        return ProducerDto.builder()
                .id(savedProducer.getId())
                .name(savedProducer.getName())
                .build();
    }
}
package com.assessment.product_catalog.service;

import com.assessment.product_catalog.dto.ProducerDto;
import com.assessment.product_catalog.dto.ProductCreateDto;
import com.assessment.product_catalog.dto.ProductDto;
import com.assessment.product_catalog.model.Producer;
import com.assessment.product_catalog.model.Product;
import com.assessment.product_catalog.repository.ProducerRepository;
import com.assessment.product_catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProducerRepository producerRepository;

    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByFilters(name, minPrice, maxPrice, pageable)
                .map(this::mapToDto);
    }

    @Transactional
    public ProductDto createProduct(ProductCreateDto dto) {
        Producer producer = producerRepository.findById(dto.getProducerId())
                .orElseThrow(() -> new IllegalArgumentException("Producer with ID " + dto.getProducerId() + " not found."));

        Product product = Product.builder()
                .producer(producer)
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .attributes(dto.getAttributes())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToDto(savedProduct);
    }

    @Transactional
    public ProductDto updateProduct(Long id, ProductCreateDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found."));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setAttributes(dto.getAttributes());

        Product updatedProduct = productRepository.save(product);
        return mapToDto(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    // helper: mapping model -> DTO
    private ProductDto mapToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .attributes(product.getAttributes())
                .producer(ProducerDto.builder()
                        .id(product.getProducer().getId())
                        .name(product.getProducer().getName())
                        .build())
                .build();
    }
}
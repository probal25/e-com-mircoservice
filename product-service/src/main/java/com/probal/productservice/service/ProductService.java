package com.probal.productservice.service;

import com.probal.productservice.dto.request.ProductRequest;
import com.probal.productservice.dto.response.ProductResponse;
import com.probal.productservice.dto.response.Response;
import com.probal.productservice.model.Product;
import com.probal.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Response<ProductResponse> createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);

        log.info("A NEW PRODUCT [NAME : {}] HAS BEEN CREATED", product.getName());
        Response<ProductResponse> response = new Response<>();
        response = response.buildResponse(200, "Product Created Successfully", ProductResponse.from(savedProduct));
        return response;
    }

    public Response<List<ProductResponse>> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponseList = products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
        Response<List<ProductResponse>> response = new Response<>();
        response = response.buildResponse(200, "All Products", productResponseList);
        return response;
    }
}

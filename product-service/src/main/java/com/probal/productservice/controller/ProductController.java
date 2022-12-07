package com.probal.productservice.controller;

import com.probal.productservice.dto.request.ProductRequest;
import com.probal.productservice.dto.response.ProductResponse;
import com.probal.productservice.dto.response.Response;
import com.probal.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<ProductResponse>> getProducts() {
        return productService.getProducts();
    }

}

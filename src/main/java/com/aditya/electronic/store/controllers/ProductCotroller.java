package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.ApiResponseMessage;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.dtos.ProductDto;
import com.aditya.electronic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductCotroller {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
    {
        ProductDto createdProduct =  productService.create(productDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto productDto)
    {
        ProductDto updatedProduct = productService.update(productDto,productId);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId)
    {
         productService.delete(productId);
         ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                 .message("Product Deleted Successfully !!")
                 .success(true)
                 .status(HttpStatus.OK)
                 .build();
         return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId)
    {
        ProductDto productDto = productService.get(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", required = false,defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false,defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc" ) String sortDir
    )
    {

        PageableResponse<ProductDto> pageableResponse= productService.getAll(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", required = false,defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false,defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc" ) String sortDir
    )
    {

        PageableResponse<ProductDto> pageableResponse= productService.getAllLive(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", required = false,defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false,defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", required = false, defaultValue = "title") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "asc" ) String sortDir
    )
    {

        PageableResponse<ProductDto> pageableResponse= productService.searchByTitle(query,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

}

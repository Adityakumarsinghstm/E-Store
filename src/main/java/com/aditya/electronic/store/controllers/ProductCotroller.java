package com.aditya.electronic.store.controllers;

import com.aditya.electronic.store.dtos.*;
import com.aditya.electronic.store.services.FileService;
import com.aditya.electronic.store.services.ProductService;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductCotroller {
    @Autowired
    private ProductService productService;
    @Value("${product.image.path}")
    private String imagePath;
    @Autowired
    private FileService fileService;

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
    @PostMapping("/images/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile file
            ) throws IOException {
        String imageName = fileService.uploadFile(file,imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(imageName);
        ProductDto updatedProduct =  productService.update(productDto,productId);
        ImageResponse response = ImageResponse.builder()
                .imageName(updatedProduct.getProductImageName())
                .message("Product Image updated successfully... ")
                .success(true)
                .status(HttpStatus.CREATED)
                .build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @GetMapping("/images/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto productDto = productService.get(productId);
//        logger.info("User image name : {}",user.getImageName());
        InputStream resource = fileService.getResource(imagePath,productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}

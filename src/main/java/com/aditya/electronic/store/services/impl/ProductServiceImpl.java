package com.aditya.electronic.store.services.impl;

import com.aditya.electronic.store.dtos.CategoryDto;
import com.aditya.electronic.store.dtos.PageableResponse;
import com.aditya.electronic.store.dtos.ProductDto;
import com.aditya.electronic.store.entities.Category;
import com.aditya.electronic.store.entities.Product;
import com.aditya.electronic.store.exceptions.ResourceNotFoundException;
import com.aditya.electronic.store.helper.Helper;
import com.aditya.electronic.store.repositories.CategoryRepository;
import com.aditya.electronic.store.repositories.ProductRepository;
import com.aditya.electronic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Value("${product.image.path}")
    private String imagePath;
    private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    // Service for creating new Product
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setId(productId);
        product.setAddedDate(new Date());
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);
    }

    //Service for updating product details
    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Not Found With This Id !!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setAddedDate(productDto.getAddedDate());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());

        product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(product);
        return mapper.map(updatedProduct, ProductDto.class);
    }

    //Service for deleting the Product Details
    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Not Found With This Id !!"));
        String fullPath = imagePath+product.getProductImageName();
        try
        {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
        catch(NoSuchFileException ex)
        {
            logger.info("User Image Not Found in Folder ");
            ex.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        productRepository.delete(product);
    }

    //Service for getting a Single Product Details
    @Override
    public ProductDto get(String productId) {
        Product product =  productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Not Found With This Id !!"));
        return mapper.map(product,ProductDto.class);
    }

    //Service for getting all the Product details
    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    // Service for getting all the live product
    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    // Service for getting all the Product by their title
    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Not Found with this categoryId !!!"));
        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setId(productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);

    }

    @Override
    public ProductDto updateProduct(String productId, String categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Not Found with this Product Id"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Not Found with categoryId"));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllofCategories(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Not Found with categoryId"));
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products = productRepository.findByCategory(category,pageable);
        return Helper.getPageableResponse(products,ProductDto.class);

    }


}

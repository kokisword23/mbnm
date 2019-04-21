package org.softuni.mbnm.service;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Product;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.ProductServiceModel;
import org.softuni.mbnm.error.ProductNameAlreadyExistsException;
import org.softuni.mbnm.error.ProductNotFoundException;
import org.softuni.mbnm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final LogService logService;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(LogService logService, ProductRepository productRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductServiceModel addProduct(ProductServiceModel productServiceModel) {
        Product product = this.productRepository
                .findByName(productServiceModel.getName())
                .orElse(null);

        if (product != null) {
            throw new ProductNameAlreadyExistsException("Product already exists");
        }

        product = this.modelMapper.map(productServiceModel, Product.class);
        product = this.productRepository.save(product);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername("ADMIN");
        logServiceModel.setDescription("Product added");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return  this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findById(String id) {
        return this.productRepository.findById(id)
                    .map(p -> this.modelMapper.map(p,ProductServiceModel.class))
                    .orElseThrow(() -> new ProductNotFoundException("Nqma takuv product!"));
    }

    @Override
    public void deleteProduct(String id) {
        Product product = this.productRepository.findById(id)
                                .orElseThrow(() -> new ProductNotFoundException("Nqma takuv product!"));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername("ADMIN");
        logServiceModel.setDescription("Product deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.productRepository.delete(product);
    }

    @Scheduled(fixedRate = 5000000)
    private void discount(){
        ProductServiceModel productServiceModel =
                this.productRepository.findAll().stream()
                        .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                        .collect(Collectors.toList())
                        .get(0);

        productServiceModel.setPrice(productServiceModel.getPrice().divide(new BigDecimal("2"),2, RoundingMode.FLOOR));
        productServiceModel.setDescription(productServiceModel.getDescription() + "!!!Product is on discount!!!");

        this.productRepository.saveAndFlush(this.modelMapper.map(productServiceModel, Product.class));
    }
}

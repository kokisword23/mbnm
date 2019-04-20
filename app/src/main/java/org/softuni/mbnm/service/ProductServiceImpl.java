package org.softuni.mbnm.service;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Product;
import org.softuni.mbnm.domain.models.service.ProductServiceModel;
import org.softuni.mbnm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
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

        return this.modelMapper.map(product, ProductServiceModel.class);
    }
}

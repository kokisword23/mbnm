package org.softuni.mbnm.service;

import org.softuni.mbnm.domain.models.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel addProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findById(String id);

    void deleteProduct(String id);
}

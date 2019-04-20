package org.softuni.mbnm.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.models.binding.ProductCreateBindingModel;
import org.softuni.mbnm.domain.models.service.ProductServiceModel;
import org.softuni.mbnm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/products")
public class ProductController extends BaseController{

    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public ProductController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView create(){
        return super.view("products/create-product");
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductCreateBindingModel model){
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        this.productService.addProduct(productServiceModel);

        return super.redirect("/products/all");
    }
}

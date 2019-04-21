package org.softuni.mbnm.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.models.binding.ProductCreateBindingModel;
import org.softuni.mbnm.domain.models.service.ProductServiceModel;
import org.softuni.mbnm.domain.models.view.UserProfileViewModel;
import org.softuni.mbnm.service.ProductService;
import org.softuni.mbnm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController extends BaseController{

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public ProductController(ModelMapper modelMapper, UserService userService, ProductService productService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView create(Principal principal, ModelAndView modelAndView){
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return super.view("product/create-product");
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addProductConfirm(@ModelAttribute ProductCreateBindingModel model){
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        this.productService.addProduct(productServiceModel);

        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView all(ModelAndView modelAndView){
        List<ProductServiceModel> products = this.productService.findAllProducts();

        modelAndView.addObject("products",products);

        return super.view("product/all-products",modelAndView);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteQuote(@PathVariable String id, ModelAndView modelAndView) {
        ProductServiceModel productServiceModel = this.productService.findById(id);

        modelAndView.addObject("product", productServiceModel);
        modelAndView.addObject("productId", id);

        return super.view("product/delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView deleteQuoteConfirm(@PathVariable String id) {
        this.productService.deleteProduct(id);

        return super.redirect("/products/all");
    }
}

package org.softuni.mbnm.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.models.view.QuoteAllViewModel;
import org.softuni.mbnm.service.QuoteService;
import org.softuni.mbnm.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final QuoteService quoteService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(QuoteService quoteService, ModelMapper modelMapper) {
        this.quoteService = quoteService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(){
        return super.view("home");
    }

    @GetMapping("/all-quotes")
    @ResponseBody
    public List<QuoteAllViewModel> fetchAllQuotes() {
        return this.quoteService.findAllQuotes()
                .stream()
                .map(q -> this.modelMapper.map(q, QuoteAllViewModel.class))
                .collect(Collectors.toList());
    }
}

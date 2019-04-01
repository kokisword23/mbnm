package org.softuni.mbnm.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.models.binding.QuoteCreateBindingModel;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quotes")
public class QuoteController extends BaseController {

    private final QuoteService quoteService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteController(QuoteService quoteService, ModelMapper modelMapper) {
        this.quoteService = quoteService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create (){
        return super.view("quote-create");
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createConfirm (@ModelAttribute QuoteCreateBindingModel model){

        this.quoteService.createQuote(this.modelMapper.map(model, QuoteServiceModel.class));

        return super.redirect("/home");
    }

    @GetMapping("/all-quotes")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAll(ModelAndView modelAndView){
        List<QuoteServiceModel> users = this.quoteService.findAllQuotes()
                .stream()
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("quotes", users);
        return super.view("all-quotes", modelAndView);
    }
}

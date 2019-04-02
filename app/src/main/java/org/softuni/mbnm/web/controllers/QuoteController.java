package org.softuni.mbnm.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.models.binding.QuoteCreateBindingModel;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.domain.models.view.QuoteDetailsViewlModel;
import org.softuni.mbnm.domain.models.view.UserProfileViewModel;
import org.softuni.mbnm.service.QuoteService;
import org.softuni.mbnm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quotes")
public class QuoteController extends BaseController {

    private final QuoteService quoteService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteController(QuoteService quoteService, UserService userService, ModelMapper modelMapper) {
        this.quoteService = quoteService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create (Principal principal, ModelAndView modelAndView){
        modelAndView
                .addObject("model", this.modelMapper
                        .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return super.view("quote-create");
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createConfirm (@ModelAttribute QuoteCreateBindingModel model){

        this.quoteService.createQuote(this.modelMapper.map(model, QuoteServiceModel.class));

        return super.redirect("/home");
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showAll(ModelAndView modelAndView){
        List<QuoteServiceModel> users = this.quoteService.findAllQuotes()
                .stream()
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("quotes", users);
        return super.view("all-quotes", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsQuote(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("quote", this.modelMapper.map(this.quoteService.findQuoteById(id), QuoteDetailsViewlModel.class));

        return super.view("quote-details", modelAndView);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteQuote(@PathVariable String id, ModelAndView modelAndView) {
        QuoteServiceModel quoteServiceModel = this.quoteService.findQuoteById(id);

        modelAndView.addObject("quote", quoteServiceModel);
        modelAndView.addObject("quoteId", id);

        return super.view("quote-delete", modelAndView);
    }

@PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteQuoteConfirm(@PathVariable String id) {
        this.quoteService.deleteQuote(id);

        return super.redirect("/quotes/all");
    }
}

package org.softuni.mbnm.service;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Quote;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteServiceImpl(QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QuoteServiceModel createQuote(QuoteServiceModel quoteServiceModel) {
        Quote quote = this.modelMapper.map(quoteServiceModel, Quote.class);
        return this.modelMapper.map(this.quoteRepository.saveAndFlush(quote), QuoteServiceModel.class);
    }

    @Override
    public QuoteServiceModel findQuoteByTitle(String title) {
        return this.quoteRepository.findQuoteByTitle(title)
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .orElseThrow(() -> new UsernameNotFoundException("Title not found!"));
    }

    @Override
    public List<QuoteServiceModel> findQuotesByAuthor(String author) {
        return this.quoteRepository.findAll()
                .stream()
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .filter(q -> q.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuoteServiceModel> findAllQuotes() {
        return this.quoteRepository.findAll()
                .stream()
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public QuoteServiceModel findQuoteById(String id) {
        return this.quoteRepository.findById(id)
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void deleteQuote(String id) {
        Quote quote = this.quoteRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        this.quoteRepository.delete(quote);
    }

//    @Override
//    public void seedQuotes(HttpSession session) {
//        session.setAttribute("quotes",this.quoteRepository.findAll());
//    }


}

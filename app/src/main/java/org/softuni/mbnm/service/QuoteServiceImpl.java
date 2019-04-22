package org.softuni.mbnm.service;

import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Quote;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.error.Constants;
import org.softuni.mbnm.error.QuoteNotFoundException;
import org.softuni.mbnm.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final LogService logService;
    private final QuoteRepository quoteRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuoteServiceImpl(LogService logService, QuoteRepository quoteRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.quoteRepository = quoteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public QuoteServiceModel createQuote(QuoteServiceModel quoteServiceModel) {
        Quote quote = this.modelMapper.map(quoteServiceModel, Quote.class);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(quoteServiceModel.getAuthor().toLowerCase());
        logServiceModel.setDescription("Quote created");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        return this.modelMapper.map(this.quoteRepository.saveAndFlush(quote), QuoteServiceModel.class);
    }

    @Override
    public QuoteServiceModel findQuoteByTitle(String title) {
        return this.quoteRepository.findQuoteByTitle(title)
                .map(q -> this.modelMapper.map(q, QuoteServiceModel.class))
                .orElseThrow(() -> new QuoteNotFoundException(Constants.QUOTE_NOT_FOUND));
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
                .orElseThrow(() -> new QuoteNotFoundException(Constants.QUOTE_ID_NOT_FOUND));
    }

    @Override
    public void deleteQuote(String id) {
        Quote quote = this.quoteRepository.findById(id).orElseThrow(() -> new QuoteNotFoundException(Constants.QUOTE_ID_NOT_FOUND));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(quote.getAuthor());
        logServiceModel.setDescription("Quote deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDB(logServiceModel);

        this.quoteRepository.delete(quote);
    }

    @Override
    public QuoteServiceModel editQuote(String id, QuoteServiceModel quoteServiceModel) {
        Quote quote = this.quoteRepository.findById(id)
                .orElseThrow(() -> new QuoteNotFoundException(Constants.QUOTE_ID_NOT_FOUND));

        quote.setTitle(quoteServiceModel.getTitle());
        quote.setAuthor(quoteServiceModel.getAuthor());
        quote.setDescription(quoteServiceModel.getDescription());
        quote.setImgUrl(quoteServiceModel.getImgUrl());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(quoteServiceModel.getAuthor());
        logServiceModel.setDescription("Quote edited");
        logServiceModel.setTime(LocalDateTime.now());

        return this.modelMapper.map(this.quoteRepository.saveAndFlush(quote), QuoteServiceModel.class);
    }

}

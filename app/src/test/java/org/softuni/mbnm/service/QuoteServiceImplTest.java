package org.softuni.mbnm.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Quote;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.repository.LogRepository;
import org.softuni.mbnm.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@DataJpaTest
public class QuoteServiceImplTest {

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private LogRepository logRepository;
    private LogService logService;
    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.logService = new LogServiceImpl(logRepository,modelMapper);
    }

//    @Test
//    public void quoteService_CreateWithValidValues_ReturnCorrect() {
//        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);
//
//
//        QuoteServiceModel toBeSaved = new QuoteServiceModel();
//
//        toBeSaved.setTitle("wtf");
//        toBeSaved.setDescription("neshto");
//        toBeSaved.setAuthor("ceci");
//        toBeSaved.setImgUrl("url");
//
//        LogServiceModel logServiceModel = new LogServiceModel();
//        logServiceModel.setUsername(toBeSaved.getAuthor().toLowerCase());
//        logServiceModel.setDescription("Quote created");
//        logServiceModel.setTime(LocalDateTime.now());
//
//        logService.seedLogInDB(logServiceModel);
//
//        QuoteServiceModel expected = quoteService.createQuote(toBeSaved);
//        QuoteServiceModel actual = this.modelMapper
//                .map(this.quoteRepository.findAll().get(0), QuoteServiceModel.class);
//
//        Assert.assertEquals(expected.getId(), actual.getId());
//        Assert.assertEquals(expected.getAuthor(), actual.getAuthor());
//        Assert.assertEquals(expected.getDescription(), actual.getDescription());
//        Assert.assertEquals(expected.getImgUrl(), actual.getImgUrl());
//        Assert.assertEquals(expected.getTitle(), actual.getTitle());
//    }

    @Test(expected = Exception.class)
    public void quoteService_createWithInvalidValues_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        QuoteServiceModel toBeSaved = new QuoteServiceModel();

        toBeSaved.setImgUrl(null);

        quoteService.createQuote(toBeSaved);
    }

    @Test(expected = Exception.class)
    public void quoteService_findQuoteByTittleWithInvalidValue_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        Quote quote = new Quote();

        quote.setTitle("cec");
        quote.setAuthor("ceci");
        quote.setDescription("cecii");
        quote.setImgUrl("ceciiiii");

        quote = this.quoteRepository.saveAndFlush(quote);

        quoteService.findQuoteByTitle("invalid");
    }

    @Test(expected = Exception.class)
    public void quoteService_findQuotesByAuthorWithInvalidValue_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        Quote quote = new Quote();

        quote.setTitle("cec");
        quote.setAuthor("ceci");
        quote.setDescription("cecii");
        quote.setImgUrl("ceciiiii");

        quote = this.quoteRepository.saveAndFlush(quote);

        quoteService.findQuotesByAuthor("invalid");
    }

    @Test(expected = Exception.class)
    public void quoteService_findAllQuotesWithInvalidValue_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        Quote quote = new Quote();

        quote.setTitle(null);
        quote.setAuthor("ceci");
        quote.setDescription("cecii");
        quote.setImgUrl("ceciiiii");

        quote = this.quoteRepository.saveAndFlush(quote);

        quoteService.findAllQuotes();
    }

    @Test(expected = Exception.class)
    public void quoteService_findQuoteByIdWithInvalidValue_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        Quote quote = new Quote();

        quote.setTitle("cec");
        quote.setAuthor("ceci");
        quote.setDescription("cecii");
        quote.setImgUrl("ceciiiii");

        quote = this.quoteRepository.saveAndFlush(quote);

        quoteService.findQuoteById("invalid");
    }

    @Test(expected = Exception.class)
    public void quoteService_deleteQuoteWithInvalidValue_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        Quote quote = new Quote();

        quote.setTitle("cec");
        quote.setAuthor("ceci");
        quote.setDescription("cecii");
        quote.setImgUrl("ceciiiii");

        quote = this.quoteRepository.saveAndFlush(quote);

        quoteService.deleteQuote("invalid");
    }

    @Test(expected = Exception.class)
    public void quoteService_editQuoteWithInvalidValue_ThrowError() {
        QuoteService quoteService = new QuoteServiceImpl(this.logService, quoteRepository, modelMapper);

        Quote quote = new Quote();

        quote.setTitle("cec");
        quote.setAuthor("ceci");
        quote.setDescription("cecii");
        quote.setImgUrl("ceciiiii");

        quote = this.quoteRepository.saveAndFlush(quote);

        QuoteServiceModel toBeEdited = new QuoteServiceModel();
        toBeEdited.setImgUrl(null);
        toBeEdited.setAuthor("az");
        toBeEdited.setDescription("ceci");
        toBeEdited.setTitle("cecii");

        quoteService.editQuote("invalid",toBeEdited);
    }
}
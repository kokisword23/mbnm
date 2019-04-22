package org.softuni.mbnm.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.mbnm.domain.entities.Log;
import org.softuni.mbnm.domain.entities.Quote;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.repository.LogRepository;
import org.softuni.mbnm.repository.QuoteRepository;
import org.softuni.mbnm.service.LogService;
import org.softuni.mbnm.service.LogServiceImpl;
import org.softuni.mbnm.service.QuoteService;
import org.softuni.mbnm.service.QuoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuoteServiceTests {

    @Autowired
    private QuoteService service;

    @MockBean
    private QuoteRepository mockQuoteRepository;

//    @Test
//    public void createWithValidValues_ReturnCorrect() {
//       QuoteServiceModel quote = new QuoteServiceModel();
//
//       when(mockQuoteRepository.save(any()))
//               .thenReturn(new Quote());
//
//       service.createQuote(quote);
//       verify(mockQuoteRepository)
//               .save(any());
//    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithInvalidValues_ThrowError() {
        service.createQuote(null);
        verify(mockQuoteRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void quoteService_findQuoteByTittleWithInvalidValue_ThrowError() {
        service.findQuoteByTitle(null);
        verify(mockQuoteRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void quoteService_findQuotesByAuthorWithInvalidValue_ThrowError() {
        service.findQuotesByAuthor(null);
        verify(mockQuoteRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void quoteService_findAllQuotesWithInvalidValue_ThrowError() {
        service.findAllQuotes();
        verify(mockQuoteRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void quoteService_findQuoteByIdWithInvalidValue_ThrowError() {
        service.findQuoteById(null);
        verify(mockQuoteRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void quoteService_deleteQuoteWithInvalidValue_ThrowError() {
        service.deleteQuote(null);
        verify(mockQuoteRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void quoteService_editQuoteWithInvalidValue_ThrowError() {
        service.editQuote(null,null);
        verify(mockQuoteRepository)
                .save(any());
    }
}

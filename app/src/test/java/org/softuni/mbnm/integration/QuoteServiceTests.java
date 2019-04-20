package org.softuni.mbnm.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.softuni.mbnm.domain.entities.Log;
import org.softuni.mbnm.domain.entities.Quote;
import org.softuni.mbnm.domain.models.service.LogServiceModel;
import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.repository.LogRepository;
import org.softuni.mbnm.repository.QuoteRepository;
import org.softuni.mbnm.service.LogService;
import org.softuni.mbnm.service.QuoteService;
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
    private LogService logService;

    @MockBean
    private LogRepository mockLogRepository;

    @Autowired
    private QuoteService service;

    @MockBean
    private QuoteRepository mockQuoteRepository;

    @Test
    public void createQuote_whenValid_quoteCreated(){
        QuoteServiceModel model = new QuoteServiceModel();

        LogServiceModel log = new LogServiceModel();
        when(mockQuoteRepository.save(any())).thenReturn(new Quote());
        when(mockLogRepository.save(any())).thenReturn(new Log());

        service.createQuote(model);
        logService.seedLogInDB(log);
        verify(mockQuoteRepository).save(any());
        verify(mockLogRepository).save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createQuote_whenNull_throw() {
        service.createQuote(null);
        verify(mockQuoteRepository)
                .save(any());
    }
}

package org.softuni.mbnm.service;

import org.softuni.mbnm.domain.models.service.QuoteServiceModel;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface QuoteService {

    QuoteServiceModel createQuote(QuoteServiceModel quoteServiceModel);

    QuoteServiceModel findQuoteByTitle(String title);

    List<QuoteServiceModel> findQuotesByAuthor(String author);

    List<QuoteServiceModel> findAllQuotes();

//    void seedQuotes(HttpSession session);
}

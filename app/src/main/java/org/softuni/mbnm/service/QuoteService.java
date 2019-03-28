package org.softuni.mbnm.service;

import org.softuni.mbnm.domain.models.service.QuoteServiceModel;

import java.util.List;

public interface QuoteService {

    QuoteServiceModel createQuote(QuoteServiceModel quoteServiceModel);

    QuoteServiceModel findQuoteByTitle(String title);

    List<QuoteServiceModel> findQuotesByAuthor(String author);
}

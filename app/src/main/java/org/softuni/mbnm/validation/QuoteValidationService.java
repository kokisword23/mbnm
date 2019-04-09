package org.softuni.mbnm.validation;

import org.softuni.mbnm.domain.models.service.QuoteServiceModel;

public interface QuoteValidationService {

    boolean isQuoteValid(QuoteServiceModel quoteServiceModel);
}

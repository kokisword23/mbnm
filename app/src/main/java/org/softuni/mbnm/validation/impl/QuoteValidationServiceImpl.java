package org.softuni.mbnm.validation.impl;

import org.softuni.mbnm.domain.models.service.QuoteServiceModel;
import org.softuni.mbnm.validation.QuoteValidationService;
import org.springframework.stereotype.Component;

@Component
public class QuoteValidationServiceImpl implements QuoteValidationService {
    @Override
    public boolean isQuoteValid(QuoteServiceModel quoteServiceModel) {
        return  quoteServiceModel != null;
    }
}

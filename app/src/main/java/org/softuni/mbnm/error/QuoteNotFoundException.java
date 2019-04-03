package org.softuni.mbnm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Quote not found!")
public class QuoteNotFoundException extends RuntimeException {

    private int status;

    public QuoteNotFoundException() {
        this.status = 404;
    }

    public QuoteNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}

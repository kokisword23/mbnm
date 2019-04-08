package org.softuni.mbnm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found!")
public class UserNotFoundException extends RuntimeException {

    private int status;

    public UserNotFoundException() {
        this.status = 404;
    }

    public UserNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}

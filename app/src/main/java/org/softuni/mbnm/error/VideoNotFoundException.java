package org.softuni.mbnm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Video not found!")
public class VideoNotFoundException extends RuntimeException {

    private int status;

    public VideoNotFoundException() {
        this.status = 404;
    }

    public VideoNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}

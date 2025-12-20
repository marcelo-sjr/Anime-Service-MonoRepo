package br.dev.marcelojunior.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(HttpStatusCode httpstatus, String message) {
        super(httpstatus, message);
    }
}

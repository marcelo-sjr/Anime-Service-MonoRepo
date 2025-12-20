package br.dev.marcelojunior.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<GlobalExceptionMessage> notFoundHandler(NotFoundException e){
        log.atError().setCause(e).setMessage(e.getMessage()).log();
        return new ResponseEntity<>(new GlobalExceptionMessage(e.getStatusCode(),e.getReason()), e.getStatusCode());
    }

}

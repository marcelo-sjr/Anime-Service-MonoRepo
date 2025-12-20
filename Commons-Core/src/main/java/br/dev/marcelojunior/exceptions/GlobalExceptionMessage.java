package br.dev.marcelojunior.exceptions;

import org.springframework.http.HttpStatusCode;

public record GlobalExceptionMessage(HttpStatusCode status, String message) {
}

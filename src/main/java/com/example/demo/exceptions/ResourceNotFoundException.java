package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Táto anotácia nastavuje HTTP status na 404 (NOT FOUND) keď je výnimka vyvolaná
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Konštruktor, ktorý prijíma chybové hlásenie a odovzdáva ho super triede RuntimeException
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

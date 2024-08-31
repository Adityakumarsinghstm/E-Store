package com.aditya.electronic.store.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Resource Not Found !!");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

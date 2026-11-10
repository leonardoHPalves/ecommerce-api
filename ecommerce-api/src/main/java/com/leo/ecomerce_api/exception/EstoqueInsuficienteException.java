package com.leo.ecomerce_api.exception;

public class EstoqueInsuficienteException extends RuntimeException{
    public EstoqueInsuficienteException(String message) {
        super(message);
    }
}

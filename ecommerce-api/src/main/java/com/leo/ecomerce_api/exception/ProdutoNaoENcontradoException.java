package com.leo.ecomerce_api.exception;

public class ProdutoNaoENcontradoException extends RuntimeException{

    public ProdutoNaoENcontradoException(String message) {
        super(message);
    }
}

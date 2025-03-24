package com.majesty.gerenciador.exception;

public class ProdutoNaoPodeSerExcluidoException extends RuntimeException {
    public ProdutoNaoPodeSerExcluidoException(String message) {
        super(message);
    }
}

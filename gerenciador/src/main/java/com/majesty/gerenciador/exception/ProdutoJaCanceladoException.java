package com.majesty.gerenciador.exception;

public class ProdutoJaCanceladoException extends RuntimeException{
    public ProdutoJaCanceladoException(String mensagem) {
        super(mensagem);
    }
}

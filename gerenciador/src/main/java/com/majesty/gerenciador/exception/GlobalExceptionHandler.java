package com.majesty.gerenciador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Captura erro quando um produto já foi cancelado
    @ExceptionHandler(ProdutoJaCanceladoException.class)
    public ResponseEntity<String> handleProdutoJaCancelado(ProdutoJaCanceladoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Captura erro quando um produto não pode ser excluído (regras de negócio)
    @ExceptionHandler(ProdutoNaoPodeSerExcluidoException.class)
    public ResponseEntity<String> handleProdutoNaoPodeSerExcluido(ProdutoNaoPodeSerExcluidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Captura erro quando um recurso (ex: projeto, membro) não é encontrado
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    //Captura erro de requisição inválida (ex: dados errados na requisição)
    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<String> handleRequisicaoInvalida(RequisicaoInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    //Captura qualquer outro erro inesperado e evita expor detalhes sensíveis
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro inesperado. Contate o suporte.");
    }
}

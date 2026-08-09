package br.com.restauranthub.api.exception;

import br.com.restauranthub.api.dto.ErroResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String mensagens = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro ->
                        erro.getField() + ": " + erro.getDefaultMessage()
                )
                .collect(Collectors.joining("; "));

        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos",
                mensagens,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarJsonInvalido(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                "JSON inválido ou valor incorreto para um dos campos.",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(resposta);
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception,
            HttpServletRequest request
    ) {
        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Recurso não encontrado",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resposta);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroResponse> tratarRegraNegocio(
            RegraNegocioException exception,
            HttpServletRequest request
    ) {
        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de regra de negócio",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(resposta);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroResponse> tratarViolacaoIntegridade(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Conflito de integridade",
                "Não é possível concluir a operação porque existem registros relacionados.",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(resposta);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroInesperado(
            Exception exception,
            HttpServletRequest request
    ) {
        ErroResponse resposta = new ErroResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado.",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resposta);
    }
}
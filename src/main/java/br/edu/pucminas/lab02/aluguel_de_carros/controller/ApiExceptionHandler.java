package br.edu.pucminas.lab02.aluguel_de_carros.controller;

import br.edu.pucminas.lab02.aluguel_de_carros.dto.ErroResponse;
import br.edu.pucminas.lab02.aluguel_de_carros.service.ClienteService;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse validacao(MethodArgumentNotValidException exception) {
        Map<String, String> campos = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.putIfAbsent(erro.getField(), erro.getDefaultMessage()));
        return new ErroResponse("Dados invalidos", campos);
    }

    @ExceptionHandler({ClienteService.CpfInvalidoException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResponse requisicaoInvalida(Exception exception) {
        return new ErroResponse(exception instanceof HttpMessageNotReadableException
                ? "Corpo da requisicao invalido" : exception.getMessage());
    }

    @ExceptionHandler({ClienteService.CpfDuplicadoException.class, ClienteService.EmailDuplicadoException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResponse conflito(RuntimeException exception) {
        return new ErroResponse(exception.getMessage());
    }

    @ExceptionHandler(ClienteService.ClienteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResponse naoEncontrado(RuntimeException exception) {
        return new ErroResponse(exception.getMessage());
    }
}

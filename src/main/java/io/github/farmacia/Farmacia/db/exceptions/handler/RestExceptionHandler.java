package io.github.farmacia.Farmacia.db.exceptions.handler;

import io.github.farmacia.Farmacia.db.exceptions.OperacaoNaoPermitida;
import io.github.farmacia.Farmacia.db.exceptions.ResgistroDuplicado;
import io.github.farmacia.Farmacia.db.model.Erro.ErroMensagem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{


    @ExceptionHandler(ResgistroDuplicado.class)
    public ResponseEntity<?> RegistroDuplicadoHandler(ResgistroDuplicado e){
        ErroMensagem erro = new ErroMensagem("Já existe no banco de dados", HttpStatus.CONFLICT.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(OperacaoNaoPermitida.class)
    public ResponseEntity<?> OperacaoNaoPermitidaHandler(OperacaoNaoPermitida e){
        ErroMensagem erro = new ErroMensagem("Não Autorizado", HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }


}

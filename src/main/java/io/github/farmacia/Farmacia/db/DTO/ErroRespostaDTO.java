package io.github.farmacia.Farmacia.db.DTO;

import org.springframework.http.HttpStatus;

public record ErroRespostaDTO(int status, String mensagem) 
{
    
    public static ErroRespostaDTO resposta400(String mensagem){
        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), mensagem);
    }

    public static ErroRespostaDTO respostaConflito(String mensagem){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), mensagem);
    }
}

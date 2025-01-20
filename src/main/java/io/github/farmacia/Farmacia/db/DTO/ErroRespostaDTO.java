package io.github.farmacia.Farmacia.db.DTO;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErroRespostaDTO(int status, String mensagem, List<ErrodeCampoDTO> erros) 
{
    
    public static ErroRespostaDTO resposta400(String mensagem){
        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroRespostaDTO respostaConflito(String mensagem){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }
}

package io.github.farmacia.Farmacia.db.exceptions;

public class OperacaoNaoPermitida extends RuntimeException {
    public OperacaoNaoPermitida(String message){
        super(message);
    }
    
}

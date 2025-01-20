package io.github.farmacia.Farmacia.db.exceptions;

public class ResgistroDuplicado extends RuntimeException {
    public ResgistroDuplicado(String mensagem){
        super(mensagem);
    }
}

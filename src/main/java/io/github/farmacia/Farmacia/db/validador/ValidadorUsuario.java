package io.github.farmacia.Farmacia.db.validador;

import io.github.farmacia.Farmacia.db.exceptions.ResgistroDuplicado;
import io.github.farmacia.Farmacia.db.model.Usuario;
import io.github.farmacia.Farmacia.db.repository.UsuarioRepository;


import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuario {

    private UsuarioRepository repository;

    public ValidadorUsuario(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void validar(Usuario usuario) {
        if (existsLogin(usuario)) {
            throw new ResgistroDuplicado("Usuario já cadastrado");
        }
    }

    private boolean existsLogin(Usuario usuario) {
        return repository.existsByLogin(usuario.getLogin());
    }

}

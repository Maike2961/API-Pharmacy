package io.github.farmacia.Farmacia.db.service;

import java.util.List;

import io.github.farmacia.Farmacia.db.validador.ValidadorUsuario;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.farmacia.Farmacia.db.model.Usuario;
import io.github.farmacia.Farmacia.db.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidadorUsuario validadorUsuario;


    public void salvar(Usuario usuario){
        validadorUsuario.validar(usuario);
        String senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public Usuario obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }

    public List<Usuario> obterTodos(){
        return usuarioRepository.findAll();
    }

}

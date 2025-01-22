package io.github.farmacia.Farmacia.db.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import io.github.farmacia.Farmacia.db.model.Usuario;
import io.github.farmacia.Farmacia.db.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario obterPorLogin = service.obterPorLogin(login);

        if(obterPorLogin == null){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return User
                .builder()
                .username(obterPorLogin.getLogin())
                .password(obterPorLogin.getSenha())
                .roles(obterPorLogin.getRoles())
                .build();
    }
    
}

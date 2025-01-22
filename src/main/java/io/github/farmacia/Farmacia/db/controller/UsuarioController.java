package io.github.farmacia.Farmacia.db.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.farmacia.Farmacia.db.DTO.UsuarioDTO;
import io.github.farmacia.Farmacia.db.model.Usuario;
import io.github.farmacia.Farmacia.db.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody UsuarioDTO usuarioDTO){
        Usuario mapear = usuarioDTO.mapear();
        service.salvar(mapear);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(mapear.getId())
        .toUri();
        return ResponseEntity.created(uri).body(mapear);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> pesquisar() {
        List<Usuario> obterTodos = service.obterTodos();

        List<UsuarioDTO> usuarios = obterTodos.stream().map(i ->
            new UsuarioDTO(
                i.getLogin(), 
                i.getSenha(), 
                i.getRoles())).collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }
    
}
